package it.nominasuntsubstantiarerum.netbus.exception;

public class CredentialException extends Exception {
	public CredentialException() {
		super("Credenziali non valide");
	}
	
	public CredentialException(String message) {
		super(message);
	}

	// gestione eccezioni concatenate
	public CredentialException(String message, Throwable t) {
		super(message, t);
	}

	public CredentialException(Throwable t) {
		super(t);
	}
}
