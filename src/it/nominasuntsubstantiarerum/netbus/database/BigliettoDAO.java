package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.nominasuntsubstantiarerum.netbus.entity.EntityBiglietto;
import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;

public class BigliettoDAO {
	public static void createBiglietto(EntityBiglietto biglietto) throws DAOException, DBConnectionException {
		if(biglietto == null)
			throw new IllegalArgumentException("biglietto non valido");
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "INSERT INTO biglietti (corsa, prezzo, data, impiegato) VALUES (?, ?, ?, ?)";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, biglietto.getIdCorsa());
				preparedStatement.setFloat(2, biglietto.getPrezzoVendita());
				preparedStatement.setDate(3, new java.sql.Date(biglietto.getDataEmissione().getTime()));
				preparedStatement.setString(4, biglietto.getIdImpiegato());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException("Errore durante l'inserimento del biglietto.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}

	public static EntityBiglietto readBiglietto(int id) throws DAOException, DBConnectionException {
		if(id <= 0)
			  throw new IllegalArgumentException("ID biglietto non valido: " + id);
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT * FROM biglietti WHERE id = ?";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					int bId = resultSet.getInt("id");
					int corsa = resultSet.getInt("corsa");
					float prezzo = resultSet.getFloat("prezzo");
					java.sql.Date data = resultSet.getDate("data");
					String impiegato = resultSet.getString("impiegato");
					String idCliente = resultSet.getString("cliente");
					return new EntityBiglietto(bId, corsa, prezzo, new java.util.Date(data.getTime()), impiegato, idCliente);
				} else {
					throw new DAOException("Biglietto non trovato.");
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante la lettura del biglietto.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
	
	/**
	 * Metodo per creare più biglietti in un'unica operazione.
	 * @param biglietto Biglietto da emettere
	 * @param ripetizioni Numero di biglietti da emettere
	 * @throws DAOException Se si verifica un errore durante l'inserimento dei biglietti
	 * @throws DBConnectionException Se si verifica un errore di connessione al database
	 * @return L'ID dell'ultimo biglietto inserito
	 */
	public static int createBiglietti(EntityBiglietto biglietto, int ripetizioni) throws DAOException, DBConnectionException {
		try {
			Connection connection = DBManager.getConnection();
			String query = "INSERT INTO biglietti (corsa, prezzo, data, impiegato) VALUES (?, ?, ?, ?)";
			String returnQuery = "SELECT LAST_INSERT_ID()"; // Per ottenere l'ID dell'ultimo biglietto inserito
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, biglietto.getIdCorsa());
				preparedStatement.setFloat(2, biglietto.getPrezzoVendita());
				preparedStatement.setDate(3, new java.sql.Date(biglietto.getDataEmissione().getTime()));
				preparedStatement.setString(4, biglietto.getIdImpiegato());
				
				for (int i = 0; i < ripetizioni; i++) 
					preparedStatement.executeUpdate();
				
				PreparedStatement returnStatement = connection.prepareStatement(returnQuery);
				ResultSet resultSet = returnStatement.executeQuery();
				if (resultSet.next()) {
					return resultSet.getInt(1);  // Restituisce l'ID dell'ultimo biglietto inserito
				} else {
					throw new DAOException("Nessun biglietto inserito.");
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante l'inserimento dei biglietti.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
	
	public static int bigliettiPerCorsa(int idCorsa) throws DAOException, DBConnectionException {
		if(idCorsa <= 0)
			throw new IllegalArgumentException("ID corsa non valido: " + idCorsa);
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT COUNT(1) AS numero FROM biglietti WHERE corsa = ?";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, idCorsa);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					return resultSet.getInt("numero");
				} else {
					throw new DAOException("Nessun biglietto trovato per la corsa con ID " + idCorsa);
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante il conteggio dei biglietti per la corsa.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
}
