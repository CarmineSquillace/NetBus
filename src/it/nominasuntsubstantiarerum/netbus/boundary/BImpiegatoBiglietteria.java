package it.nominasuntsubstantiarerum.netbus.boundary;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import it.nominasuntsubstantiarerum.netbus.boundary.gui.EmissioneBiglietti;
import it.nominasuntsubstantiarerum.netbus.boundary.gui.LoginDialog;
import it.nominasuntsubstantiarerum.netbus.boundary.gui.MenuImpiegatoBiglietteria;
import it.nominasuntsubstantiarerum.netbus.boundary.gui.RicercaCorse;
import it.nominasuntsubstantiarerum.netbus.control.GestioneVenditeBigliettiNetBus;
import it.nominasuntsubstantiarerum.netbus.entity.EntityCorsa;
import it.nominasuntsubstantiarerum.netbus.entity.EntityImpiegato;
import it.nominasuntsubstantiarerum.netbus.exception.CredentialException;
import it.nominasuntsubstantiarerum.netbus.exception.OperationException;

public class BImpiegatoBiglietteria {
	EntityImpiegato sessione = null;
	
	/**
	 * Avvia l'applicazione di gestione biglietteria per impiegati.
	 */
	public static void main(String[] args) {
		BImpiegatoBiglietteria boundary = new BImpiegatoBiglietteria();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuImpiegatoBiglietteria frame = new MenuImpiegatoBiglietteria(boundary);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static EntityCorsa ricercaCorse() {
		// Apre la finestra di ricerca corse
		EntityCorsa corsa = null; // Valore di default per indicare nessuna selezione
		RicercaCorse ricercaCorse = new RicercaCorse();
		ricercaCorse.setVisible(true);
		corsa = ricercaCorse.getCorsa();
		return corsa;
	}
	
	/**
	 * Metodo per emettere biglietti.
	 * 
	 * Questo metodo apre la finestra di emissione biglietti.
	 */
	public void emettiBiglietti() {
		EmissioneBiglietti emissioneBiglietti = new EmissioneBiglietti(sessione.getCodiceIdentificativo());
		emissioneBiglietti.setVisible(true);
	}
	
	/**
	 * Metodo per autenticare un impiegato.
	 * 
	 * @return L'oggetto EntityImpiegato se l'autenticazione ha successo, altrimenti null.
	 */
	public EntityImpiegato autenticazione() {
		while (sessione == null) {
			LoginDialog loginDialog = new LoginDialog();
			loginDialog.setVisible(true);
			String id = loginDialog.getId();
			String password = loginDialog.getPassword();
			if (id == null && password == null) {
				return null;
			}
			try {
				sessione = GestioneVenditeBigliettiNetBus.getInstance().autenticazione(id, password);
			} catch (CredentialException e) {
				JOptionPane.showMessageDialog(null, "Credenziali non corrette", "Errore", JOptionPane.ERROR_MESSAGE);
			} catch (OperationException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Errore sconosciuto", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		return sessione;
	}
}
