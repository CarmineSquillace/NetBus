package it.nominasuntsubstantiarerum.netbus.entity;

public class EntityAutobus {
	private int idAutobus;
	private int capienza;
	
	public EntityAutobus(int idAutobus, int capienza) {
		this.idAutobus = idAutobus;
		this.capienza = capienza;
	}
	
	public int getIdAutobus() {
		return idAutobus;
	}
	
	public void setIdAutobus(int idAutobus) {
		this.idAutobus = idAutobus;
	}
	
	public int getCapienza() {
		return capienza;
	}
	
	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}
}
