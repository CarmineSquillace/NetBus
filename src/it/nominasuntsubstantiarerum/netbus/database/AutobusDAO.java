package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.nominasuntsubstantiarerum.netbus.entity.EntityAutobus;
import it.nominasuntsubstantiarerum.netbus.exception.DAOException;
import it.nominasuntsubstantiarerum.netbus.exception.DBConnectionException;

public class AutobusDAO {
   public static EntityAutobus readAutobus(int id) throws DAOException, DBConnectionException {
		if(id <= 0)
			throw new IllegalArgumentException("ID autobus non valido: " + id);
	
		try {
			Connection connection = DBManager.getConnection();
			String query = "SELECT * FROM autobus WHERE id = ?";
	
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
	
				if (resultSet.next()) {
					int idAutobus = resultSet.getInt("id");
					int capienza = resultSet.getInt("capienza");
					return new EntityAutobus(idAutobus, capienza);
				} else {
					return null; //autobus non trovato
				}
			} catch (SQLException e) {
				throw new DAOException("Errore durante la lettura dell'autobus.");
			}
			finally {
				DBManager.closeConnection();
			}
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database.");
		}
	}
  
  /**
   * Recupera l'autobus associato a una corsa specificata.
   * @param idCorsa
   * @return EntityAutobus
   * @throws DAOException
   * @throws DBConnectionException
   */
  public static EntityAutobus readAutobusPerCorsa(int idCorsa) throws DAOException, DBConnectionException {
	  if (idCorsa <= 0)
		  throw new IllegalArgumentException("ID Corsa non valido: " + idCorsa);
	  try {
		  Connection connection = DBManager.getConnection();
		  String query = "SELECT a.id, a.capienza FROM autobus a INNER JOIN corse c ON c.autobus = a.id WHERE c.id = ?";
		  try {
			  PreparedStatement preparedStatement = connection.prepareStatement(query);
			  preparedStatement.setInt(1, idCorsa);
			  
			  ResultSet resultSet = preparedStatement.executeQuery();
			  if (resultSet.next()) {
				  int idAutobus = resultSet.getInt("id");
				  int capienza = resultSet.getInt("capienza");
				  return new EntityAutobus(idAutobus, capienza);
			  } else {
				  throw new DAOException("Nessun autobus trovato per la corsa con ID " + idCorsa);
			  }
		  } catch (SQLException e) {
			  throw new DAOException("Errore durante il recupero dell'autobus per la corsa.");
		  } finally {
			  DBManager.closeConnection();
		  }
	  } catch (SQLException e) {
		  throw new DBConnectionException("Errore di connessione al database.");
	  }
  }
}
