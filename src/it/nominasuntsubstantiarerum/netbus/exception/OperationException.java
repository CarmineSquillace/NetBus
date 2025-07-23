package it.nominasuntsubstantiarerum.netbus.exception;

public class OperationException extends Exception {
	public OperationException() {
		super("Errore generico di operazione");
	}
	
	public OperationException(String message) {
		super(message);
	}

	// gestione eccezioni concatenate
	public OperationException(String message, Throwable t) {
		super(message, t);
	}

	public OperationException(Throwable t) {
		super(t);
	}
}
