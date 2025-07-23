package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.nominasuntsubstantiarerum.netbus.entity.EntityCitta;
import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;

public class CittaDAO {
	/**
	 * Crea una nuova città nel database.
	 * @param citta L'oggetto EntityCitta da inserire.
	 * @throws DAOException Se si verifica un errore durante l'inserimento della città.
	 * @throws DBConnectionException Se si verifica un errore di connessione al database.
	 */
	public static void createCitta(EntityCitta citta) throws DAOException, DBConnectionException {
		if(citta == null)
			throw new IllegalArgumentException("città non valida");
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "INSERT INTO citta (nome) VALUES (?)";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, citta.getNome());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException("Errore durante l'inserimento della città: " + citta.getNome());
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
	
	/**
	 * Cerca una città nel database in base al nome.
	 * @param nome Il nome della città da cercare.
	 * @return L'ID della città trovata.
	 * @throws DAOException Se si verifica un errore durante la ricerca della città o se la città non viene trovata.
	 * @throws DBConnectionException Se si verifica un errore di connessione al database.
	 */
	public static int search(String nome) throws DAOException, DBConnectionException {
		if(nome == null)
			throw new IllegalArgumentException("nome città non valido");
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT id FROM citta WHERE nome = ?";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, nome);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return resultSet.getInt("id");
				} else {
					throw new DAOException("Città non trovata: " + nome);
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante la ricerca della città: " + nome);
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
}
