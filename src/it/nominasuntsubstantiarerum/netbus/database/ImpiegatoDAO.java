package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.nominasuntsubstantiarerum.netbus.entity.EntityImpiegato;
import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;

public class ImpiegatoDAO {
	public static EntityImpiegato readImpiegato(String username, String password) throws DAOException, DBConnectionException {
		if (username == null || password == null)
			  throw new IllegalArgumentException("username o password non valide");
		
		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT id, password FROM impiegati WHERE id = ? AND password = ?;";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					String id = resultSet.getString("id");
					String pswd = resultSet.getString("password");
					return new EntityImpiegato(id, pswd);
				} else {
					return null; // Accesso fallito, nessun impiegato trovato con queste credenziali
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante il login dell'impiegato.");
			} finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
}
