package it.nominasuntsubstantiarerum.netbus.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe per la gestione della connessione al database.
 */
public class DBManager {
	private static Connection connection;
	private DBManager() {}
	
	/**
	 * Ottiene una connessione al database MySQL.
	 * @return Connection oggetto di connessione al database
	 * @throws SQLException se si verifica un errore durante la connessione
	 */
	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbus", "root", "");
		}
		return connection;
	}
	
	/**
	 * Chiude la connessione al database se è aperta.
	 * @throws SQLException se si verifica un errore durante la chiusura della connessione
	 */
	public static void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
}
