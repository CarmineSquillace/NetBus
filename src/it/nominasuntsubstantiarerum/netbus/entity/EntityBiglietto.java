package it.nominasuntsubstantiarerum.netbus.entity;

import java.util.Date;

public class EntityBiglietto {
	private int idBiglietto;
	private int idCorsa;
	private float prezzoVendita;
	private Date dataEmissione;
	private String idImpiegato;
	private String idClienteRegistrato;
	
	public EntityBiglietto(int idBiglietto, int idCorsa, float prezzoVendita, Date dataEmissione, String idImpiegato, String idClienteRegistrato) {
		this.idBiglietto = idBiglietto;
		this.idCorsa = idCorsa;
		this.prezzoVendita = prezzoVendita;
		this.dataEmissione = dataEmissione;
		this.idImpiegato = idImpiegato;
		this.idClienteRegistrato = idClienteRegistrato;
	}
	
	public int getIdBiglietto() {
		return idBiglietto;
	}
	
	public void setIdBiglietto(int idBiglietto) {
		this.idBiglietto = idBiglietto;
	}
	
	public int getIdCorsa() {
		return idCorsa;
	}
	
	public void setIdCorsa(int idCorsa) {
		this.idCorsa = idCorsa;
	}
	
	public float getPrezzoVendita() {
		return prezzoVendita;
	}
	
	public void setPrezzoVendita(float prezzoVendita) {
		this.prezzoVendita = prezzoVendita;
	}
	
	public Date getDataEmissione() {
		return dataEmissione;
	}
	
	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	
	public String getIdImpiegato() {
		return idImpiegato;
	}
	
	public void setIdImpiegato(String idImpiegato) {
		this.idImpiegato = idImpiegato;
	}
	
	public String getIdClienteRegistrato() {
		return idClienteRegistrato;
	}
	
	public void setIdClienteRegistrato(String idClienteRegistrato) {
		this.idClienteRegistrato = idClienteRegistrato;
	}
}
