package it.nominasuntsubstantiarerum.netbus.entity;

public class EntityImpiegato {
	private String codiceIdentificativo;
	private String password;
	
	public EntityImpiegato(String codiceIdentificativo, String password) {
		this.codiceIdentificativo = codiceIdentificativo;
		this.password = password;
	}
	
	public String getCodiceIdentificativo() {
		return codiceIdentificativo;
	}
	
	public void setCodiceIdentificativo(String codiceIdentificativo) {
		this.codiceIdentificativo = codiceIdentificativo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
