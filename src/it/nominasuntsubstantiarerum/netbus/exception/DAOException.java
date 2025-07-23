package it.nominasuntsubstantiarerum.netbus.exception;

public class DAOException extends Exception {
	public DAOException() {
		super("Errore generico di accesso ai dati");
	}
	
	public DAOException(String message) {
		super(message);
	}

	// gestione eccezioni concatenate
	public DAOException(String message, Throwable t) {
		super(message, t);
	}

	public DAOException(Throwable t) {
		super(t);
	}
}
