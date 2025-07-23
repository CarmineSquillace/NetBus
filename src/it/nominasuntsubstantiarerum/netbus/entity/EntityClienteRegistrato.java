package it.nominasuntsubstantiarerum.netbus.entity;

public class EntityClienteRegistrato {
	private String nomeUtente;
	private String password;
	private String email;
	private String cartaDiCredito;
	
	public EntityClienteRegistrato(String nomeUtente, String password, String email, String cartaDiCredito) {
		this.nomeUtente = nomeUtente;
		this.password = password;
		this.email = email;
		this.cartaDiCredito = cartaDiCredito;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}
	
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCartaDiCredito() {
		return cartaDiCredito;
	}
	
	public void setCartaDiCredito(String cartaDiCredito) {
		this.cartaDiCredito = cartaDiCredito;
	}
	
}
