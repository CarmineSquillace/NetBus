package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.nominasuntsubstantiarerum.netbus.entity.EntityCorsa;
import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;

public class CorsaDAO {
	/**
	 * Recupera tutte le corse tra due città in una data specifica.
	 * @param cittaPartenza nome della città di partenza
	 * @param cittaDestinazione nome della città di destinazione
	 * @param date data della corsa
	 * @return Lista di corse trovate tra le due città nella data specificata.
	 * @throws DAOException Se si verifica un errore durante la ricerca delle corse
	 * @throws DBConnectionException Se si verifica un errore di connessione al database.
	 */
	public static List<EntityCorsa> readCorse(String cittaPartenza, String cittaDestinazione, LocalDate date) throws DAOException, DBConnectionException {
		if(cittaPartenza==null || cittaDestinazione==null || date==null)
			throw new IllegalArgumentException("Parametri di ricerca non validi");
		
		List<EntityCorsa> corse = new ArrayList<>();
		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT c.* FROM corse c INNER JOIN tratte t ON t.id = c.tratta INNER JOIN citta cp ON cp.id = t.partenza INNER JOIN citta cd ON cd.id = t.destinazione WHERE cp.nome = ? AND cd.nome = ? AND c.data = ?;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, cittaPartenza);
				preparedStatement.setString(2, cittaDestinazione);
				preparedStatement.setDate(3, java.sql.Date.valueOf(date));
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					int tratta = resultSet.getInt("tratta");
					LocalDate data = resultSet.getDate("data").toLocalDate();
					java.sql.Time orarioPartenza = resultSet.getTime("orario_partenza");
					java.sql.Time orarioArrivo = resultSet.getTime("orario_arrivo");
					float costo = resultSet.getFloat("costo");
					int autobus = resultSet.getInt("autobus");					
					corse.add(new EntityCorsa(id, tratta, data,
							new it.nominasuntsubstantiarerum.netbus.util.Time(orarioPartenza), 
							new it.nominasuntsubstantiarerum.netbus.util.Time(orarioArrivo), 
							costo, autobus));
				}
				return corse; // ha senso anche se la lista è vuota
			} catch (SQLException e) {
				throw new DAOException("Errore durante la ricerca della corsa tra " + cittaPartenza + " e " + cittaDestinazione);
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
	
	/**
	 * Crea una nuova corsa nel database.
	 * @param corsa L'oggetto EntityCorsa da inserire.
	 * @throws DAOException Se si verifica un errore durante l'inserimento della corsa.
	 * @throws DBConnectionException Se si verifica un errore di connessione al database.
	 */
	public static void createCorsa(EntityCorsa corsa) throws DAOException, DBConnectionException {
		if(corsa == null)
			throw new IllegalArgumentException("corsa non valida");
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "INSERT INTO corse (tratta, orario_partenza, orario_arrivo, costo, autobus, data) VALUES (?, ?, ?, ?, ?, ?)";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, corsa.getTratta());
				preparedStatement.setTime(2, corsa.getOrarioDiPartenza().toSqlTime());
				preparedStatement.setTime(3, corsa.getOrarioDiArrivo().toSqlTime());
				preparedStatement.setFloat(4, corsa.getCosto());
				preparedStatement.setInt(5, corsa.getIdAutobus());
				preparedStatement.setDate(6, java.sql.Date.valueOf(corsa.getData()));
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException("Errore durante l'inserimento della corsa.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
}
