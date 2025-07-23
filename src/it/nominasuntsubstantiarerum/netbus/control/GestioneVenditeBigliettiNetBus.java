package it.nominasuntsubstantiarerum.netbus.control;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;

import it.nominasuntsubstantiarerum.netbus.database.AutobusDAO;
import it.nominasuntsubstantiarerum.netbus.database.BigliettoDAO;
import it.nominasuntsubstantiarerum.netbus.database.CittaDAO;
import it.nominasuntsubstantiarerum.netbus.database.CorsaDAO;
import it.nominasuntsubstantiarerum.netbus.database.ImpiegatoDAO;
import it.nominasuntsubstantiarerum.netbus.database.TrattaDAO;
import it.nominasuntsubstantiarerum.netbus.entity.EntityAutobus;
import it.nominasuntsubstantiarerum.netbus.entity.EntityBiglietto;
import it.nominasuntsubstantiarerum.netbus.entity.EntityCitta;
import it.nominasuntsubstantiarerum.netbus.entity.EntityCorsa;
import it.nominasuntsubstantiarerum.netbus.entity.EntityImpiegato;
import it.nominasuntsubstantiarerum.netbus.exception.CredentialException;
import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;
import it.nominasuntsubstantiarerum.netbus.exception.OperationException;
import it.nominasuntsubstantiarerum.netbus.util.Time;

public class GestioneVenditeBigliettiNetBus {
	
	private static GestioneVenditeBigliettiNetBus instance = null;
	
	protected GestioneVenditeBigliettiNetBus() {}
	
	public static GestioneVenditeBigliettiNetBus getInstance() {
		if (instance == null) {
			instance = new GestioneVenditeBigliettiNetBus();
		}
		return instance;
	}
	
	
	/**
	 * Metodo per emettere biglietti da parte di un impiegato
	 * @param biglietto Biglietto da emettere
	 * @param numBiglietti Numero di biglietti da emettere
	 * @return ID dell'ultimo biglietto emesso
	 * @throws OperationException Se si verifica un errore durante l'emissione dei biglietti
	 */
	public int emettiBiglietti(EntityBiglietto biglietto, int numBiglietti, OutputStream output) throws OperationException {
		try {
			if (numBiglietti <= 0) {
				throw new IllegalArgumentException("Il numero di biglietti deve essere maggiore di zero.");
			}
			// fase di validazione
			EntityAutobus autobusAssociato = AutobusDAO.readAutobusPerCorsa(biglietto.getIdCorsa());
			if (autobusAssociato == null) {
				throw new OperationException("Autobus associato alla corsa non trovato.");
			}
			int numBigliettiTotaliEmessi = BigliettoDAO.bigliettiPerCorsa(biglietto.getIdCorsa());
			if (numBigliettiTotaliEmessi + numBiglietti > autobusAssociato.getCapienza()) {
				throw new OperationException("Impossibile emettere " + numBiglietti + " biglietti: supererebbe la capienza dell'autobus (" + autobusAssociato.getCapienza() + ").");
			}
			// fase di emissione
			int ultimoId = BigliettoDAO.createBiglietti(biglietto, numBiglietti); // Ritorna l'ID dell'ultimo biglietto emesso
			if (output != null) {
				stampaBiglietti(ultimoId, numBiglietti, output); // fa anche la stampa dei biglietti
			}
			return ultimoId;
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante l'emissione dei biglietti: " + e.getMessage());
		}
	}
	
	/** Metodo per autenticare un impiegato
	* @param username Nome utente dell'impiegato
	* @param password Password dell'impiegato
	* @return Impiegato autenticato
	* @throws CredentialException Se le credenziali non sono valide
	* @throws OperationException Se si verifica un errore durante l'operazione
	*/
	public EntityImpiegato autenticazione(String username, String password) throws CredentialException, OperationException {
		try {
			EntityImpiegato impiegato = ImpiegatoDAO.readImpiegato(username, password);
			if (impiegato == null) {
				throw new CredentialException("Autenticazione fallita: credenziali non valide.");
			} else {
				return impiegato;
			}
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante l'autenticazione: " + e.getMessage());
		}
	}
	
	public void inviaReportMensile() {
	}

	
	/** Metodo per inserire una tratta tra due città
	 * @param cittaPartenza ID della città di partenza
	 * @param cittaDestinazione ID della città di destinazione
	 * @throws OperationException Se si verifica un errore durante l'inserimento della tratta
	 */
	public void inserisciTratta(String cittaPartenza, String cittaDestinazione) throws OperationException {
		try {
			int partenzaID = CittaDAO.search(cittaPartenza);
			int destinazioneID = CittaDAO.search(cittaDestinazione);
			TrattaDAO.createTratta(partenzaID, destinazioneID);
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante l'inserimento della tratta: " + e.getMessage());
		}
	}
	
	/** Metodo per inserire una città nel database
	 * @param nome Nome della città da inserire
	 * @throws OperationException Se si verifica un errore durante l'inserimento della città
	 */
	public void inserisciCitta(String nome) throws OperationException {
		EntityCitta citta = new EntityCitta(nome);
		try {
			CittaDAO.createCitta(citta);
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante l'inserimento della città: " + nome + " - " + e.getMessage());
		}
	}
	
	public void inserisciCorsa(String partenza, String destinazione, LocalDate data, Time orarioPartenza, Time orarioArrivo, float costo, int autobus) throws OperationException, IllegalArgumentException {
		try {
			if (data == null || orarioPartenza == null || orarioArrivo == null) {
				throw new IllegalArgumentException("Data, orario di partenza e orario di arrivo non possono essere null.");
			}
			if (orarioPartenza.isAfter(orarioArrivo)) {
				throw new IllegalArgumentException("L'orario di partenza non può essere dopo l'orario di arrivo.");
			}
			if (costo < 0) {
				throw new IllegalArgumentException("Il costo del biglietto non può essere negativo.");
			}
			if (data.isBefore(LocalDate.now())) {
				throw new IllegalArgumentException("La data della corsa non può essere nel passato.");
			}
			int idTratta = TrattaDAO.search(partenza, destinazione);
			
			EntityAutobus autobusAssociato = AutobusDAO.readAutobus(autobus);
			if(autobusAssociato == null)
				throw new OperationException("Errore durante l'inserimento della corsa, ID Autobus non valido: " + autobus);

			EntityCorsa corsa = new EntityCorsa(-1, idTratta, data, orarioPartenza, orarioArrivo, costo, autobus);
			CorsaDAO.createCorsa(corsa);
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante l'inserimento della corsa: " + e.getMessage());
		}
	}
	
	/** Metodo per cercare corse tra due città
	 * @param cittaPartenza Nome della città di partenza
	 * @param cittaDestinazione Nome della città di destinazione
	 * @param data Data della corsa
	 * @return Lista di corse trovate tra le due città
	 * @throws OperationException Se si verifica un errore durante la ricerca delle corse
	 */
	public List<EntityCorsa> ricercaCorse(String cittaPartenza, String cittaDestinazione, LocalDate data) throws OperationException {
		try {
			return CorsaDAO.readCorse(cittaPartenza, cittaDestinazione, data);
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante la ricerca delle corse tra " + cittaPartenza + " e " + cittaDestinazione + " il " + data + ": " + e.getMessage());
		}
	}
	
	private void stampaBiglietti(int idBiglietto, int numBiglietti, OutputStream output) throws OperationException {
		try {
			if (numBiglietti <= 0) {
				throw new IllegalArgumentException("Il numero di biglietti da stampare deve essere maggiore di zero.");
			}
			EntityBiglietto biglietto = BigliettoDAO.readBiglietto(idBiglietto);
			if (biglietto == null) {
				throw new OperationException("Biglietto con ID " + idBiglietto + " non trovato.");
			}
			boolean emessoDaImpiegato = biglietto.getIdImpiegato() != null && !biglietto.getIdImpiegato().isEmpty();
			for (int i = 0; i < numBiglietti; i++) {
				int id = idBiglietto - i;
				output.write(("== BIGLIETTO NETBUS ==\n"
						+ "Biglietto ID: " + id + "\n"
								+ "Corsa ID: " + biglietto.getIdCorsa() + "\n"
								+ "Data Emissione: " + DateFormat.getDateInstance().format(biglietto.getDataEmissione()) + "\n"
								+ (emessoDaImpiegato ? "Emesso da Impiegato: " + biglietto.getIdImpiegato() + "\n" : "Emesso da Cliente Registrato: " + biglietto.getIdClienteRegistrato() + "\n")
										+ "Prezzo: " + biglietto.getPrezzoVendita() + "\n\n").getBytes());
			}
		} catch (IllegalArgumentException | DAOException | DBConnectionException e) {
			throw new OperationException("Errore durante la stampa del biglietto con ID " + idBiglietto + ": " + e.getMessage());
		} catch (IOException e) {
			throw new OperationException("Errore durante la scrittura del biglietto.");
		}
	}
	
	private void generaReport() {
		
	}
}
