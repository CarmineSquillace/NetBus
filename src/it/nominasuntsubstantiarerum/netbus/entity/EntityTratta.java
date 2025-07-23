package it.nominasuntsubstantiarerum.netbus.entity;

public class EntityTratta {
	private int idTratta;
	private int idPartenza;
	private int idDestinazione;
	
	public EntityTratta(int id, int partenza, int destinazione) {
		this.idTratta = id;
		this.idPartenza = partenza;
		this.idDestinazione = destinazione;
	}
	
	public int getIdTratta() {
		return idTratta;
	}
	
	public void setIdTratta(int idTratta) {
		this.idTratta = idTratta;
	}

	public int getIdPartenza() {
		return idPartenza;
	}

	public void setIdPartenza(int idPartenza) {
		this.idPartenza = idPartenza;
	}

	public int getIdDestinazione() {
		return idDestinazione;
	}

	public void setIdDestinazione(int idDestinazione) {
		this.idDestinazione = idDestinazione;
	}
}
