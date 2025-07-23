package it.nominasuntsubstantiarerum.netbus.entity;

public class EntityCitta {
	private int idCitta;
	private String nome;
	
	public EntityCitta(String nome) {
		this.nome = nome;
	}
	
	public EntityCitta(int idCitta, String nome) {
		this.idCitta = idCitta;
		this.nome = nome;
	}
	
	public int getIdCitta() {
		return idCitta;
	}
	
	public void setIdCitta(int idCitta) {
		this.idCitta = idCitta;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
