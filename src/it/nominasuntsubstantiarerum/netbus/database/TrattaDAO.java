package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;

public class TrattaDAO {
	/**
	 * Crea una nuova tratta tra due città specificate dai loro ID.
	 * @param partenza ID della città di partenza
	 * @param destinazione ID della città di destinazione
	 * @throws DAOException Se si verifica un errore durante l'inserimento della tratta.
	 * @throws DBConnectionException Se si verifica un errore di connessione al database.
	 */
	public static void createTratta(int partenza, int destinazione) throws DAOException, DBConnectionException {
		if (partenza <= 0 || destinazione <= 0)
			  throw new IllegalArgumentException("partenza o destinazione non valide");
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "INSERT INTO tratte (partenza, destinazione) VALUES (?, ?)";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, partenza);
				preparedStatement.setInt(2, destinazione);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException("Errore durante l'inserimento della tratta.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}

	/**
	 * Cerca una tratta tra due città specificate dai loro nomi.
	 * @param partenza nome della città di partenza
	 * @param destinazione nome della città di destinazione
	 * @return ID della tratta trovata
	 * @throws DBConnectionException Se si verifica un errore di connessione al database.
	 * @throws DAOException Se si verifica un errore durante la ricerca della tratta o se la tratta non viene trovata.
	 */
	public static int search(String partenza, String destinazione) throws DBConnectionException, DAOException {
		if(partenza == null || destinazione == null)
			throw new IllegalArgumentException("Partenza o destinazione non valide");

		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT t.id AS id FROM tratte t INNER JOIN citta cp ON t.partenza = cp.id INNER JOIN citta cd ON t.destinazione = cd.id WHERE cp.nome = ? AND cd.nome = ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, partenza);
				preparedStatement.setString(2, destinazione);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return resultSet.getInt("id");
				} else {
					throw new DAOException("Tratta non trovata tra " + partenza + " e " + destinazione);
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante la ricerca della tratta tra " + partenza + " e " + destinazione);
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
}
