package it.nominasuntsubstantiarerum.netbus.entity;

import java.time.LocalDate;

import it.nominasuntsubstantiarerum.netbus.util.Time;

public class EntityCorsa {
	private int idCorsa;
	private int idTratta;
	private LocalDate data;
	private Time orarioDiPartenza;
	private Time orarioDiArrivo;
	private float costo;
	private int idAutobus;
	
	public EntityCorsa(int idCorsa, int idTratta, LocalDate data, Time orarioDiPartenza, Time orarioDiArrivo, float costo, int idAutobus) {
		this.idCorsa = idCorsa;
		this.idTratta = idTratta;
		this.data = data;
		this.orarioDiPartenza = orarioDiPartenza;
		this.orarioDiArrivo = orarioDiArrivo;
		this.costo = costo;
		this.idAutobus = idAutobus;
	}
	
	public int getIdCorsa() {
		return idCorsa;
	}
	
	public void setIdCorsa(int idCorsa) {
		this.idCorsa = idCorsa;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getTratta() {
		return idTratta;
	}

	public void setTratta(int idTratta) {
		this.idTratta = idTratta;
	}

	public Time getOrarioDiPartenza() {
		return orarioDiPartenza;
	}

	public void setOrarioDiPartenza(Time orarioDiPartenza) {
		this.orarioDiPartenza = orarioDiPartenza;
	}

	public Time getOrarioDiArrivo() {
		return orarioDiArrivo;
	}

	public void setOrarioDiArrivo(Time orarioDiArrivo) {
		this.orarioDiArrivo = orarioDiArrivo;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	public int getIdAutobus() {
		return idAutobus;
	}
	
	public void setIdAutobus(int idAutobus) {
		this.idAutobus = idAutobus;
	}
}
