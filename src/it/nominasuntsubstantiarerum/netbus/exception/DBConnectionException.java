package it.nominasuntsubstantiarerum.netbus.exception;

public class DBConnectionException extends Exception{
	public DBConnectionException() {
		super("Errore di connessione al database");
	}
	
	public DBConnectionException(String message) {
		super(message);
	}

	// gestione eccezioni concatenate
	public DBConnectionException(String message, Throwable t) {
		super(message, t);
	}

	public DBConnectionException(Throwable t) {
		super(t);
	}
}
