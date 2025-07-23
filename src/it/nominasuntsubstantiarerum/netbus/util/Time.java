package it.nominasuntsubstantiarerum.netbus.util;

import java.time.LocalTime;

public class Time {
	private int ore;
	private int minuti;
	private int secondi;
	
	/**
	 * Inizializza un oggetto Time con un oggetto java.sql.Time.
	 * @param time oggetto java.sql.Time.
	 */
	public Time(java.sql.Time time) {
		if (time == null)
			throw new IllegalArgumentException("Il tempo non può essere null");

		LocalTime t = time.toLocalTime();
		this.ore = t.getHour();
		this.minuti = t.getMinute();
		this.secondi = t.getSecond();
	}
	
	/**
	 * Inizializza un oggetto Time con il tempo in millisecondi.
	 * @param time tempo in millisecondi.
	 */
	public Time(long time) {
		if (time < 0)
			throw new IllegalArgumentException("Il tempo non può essere negativo");

		long totalSeconds = time / 1000;
		this.ore = (int) (totalSeconds / 3600);
		totalSeconds %= 3600;
		this.minuti = (int) (totalSeconds / 60);
		this.secondi = (int) (totalSeconds % 60);

		// Validazione dei valori. Si tratta di situazioni anomale, ma è meglio essere sicuri.
		if (ore < 0 || ore >= 24)
			throw new IllegalArgumentException("l'ora dovrebbe essere tra 0-23");

		if (minuti < 0 || minuti >= 60)
			throw new IllegalArgumentException("i minuti dovrebbero essere tra 0-59");

		if (secondi < 0 || secondi >= 60)
			throw new IllegalArgumentException("i secondi dovrebbero essere tra 0-59");
	}

	public Time() {
		this(0, 0, 0);
	}
	
	/**
	 * Converte l'oggetto Time in un oggetto java.sql.Time.
	 * @return un oggetto java.sql.Time rappresentante l'ora, i minuti e i secondi.
	 */
	public java.sql.Time toSqlTime() {
		return new java.sql.Time((ore * 3600 + minuti * 60 + secondi) * 1000L);
	}

	public Time(int ore) {
		this(ore, 0, 0);
	}

	public Time(int ore, int minuti) {
		this(ore, minuti, 0);
	}

	public Time(int ore, int minuti, int secondi) {
		if (ore < 0 || ore >= 24)
			throw new IllegalArgumentException("l'ora dovrebbe essere tra 0-23");

		if (minuti < 0 || minuti >= 60)
			throw new IllegalArgumentException("i minuti dovrebbero essere tra 0-59");

		if (secondi < 0 || secondi >= 60)
			throw new IllegalArgumentException("i secondi dovrebbero essere tra 0-59");

		this.ore = ore;
		this.minuti = minuti;
		this.secondi = secondi;
	}

	public Time(Time t) {
		this(t.ore, t.minuti, t.secondi);
	}

	public int getOre() {
		return ore;
	}

	public int getMinuti() {
		return minuti;
	}

	public int getSecondi() {
		return secondi;
	}

	// setter

	public void setOre(int ore) {
		if (ore < 0 || ore >= 24)
			throw new IllegalArgumentException("l'ora dovrebbe essere tra 0-23");

		this.ore = ore;
	}

	public void setMinuti(int minuti) {
		if (minuti < 0 || minuti >= 60)
			throw new IllegalArgumentException("i minuti dovrebbero essere tra 0-59");

		this.minuti = minuti;
	}

	public void setSecondi(int secondi) {
		if (secondi < 0 || secondi >= 60)
			throw new IllegalArgumentException("i secondi dovrebbero essere tra 0-59");

		this.secondi = secondi;
	}

	public String toString() {
		return String.format("%02d:%02d:%02d", ore, minuti, secondi);
	}

	/**
	 * Confronta se questo oggetto Time rappresenta un tempo successivo rispetto al parametro.
	 * @param altro l'oggetto Time da confrontare.
	 * @return true se questo oggetto Time è successivo a quello passato come parametro, false altrimenti.
	 */
	public boolean isAfter(Time altro) {
		if (altro == null) {
			throw new IllegalArgumentException("Il tempo da confrontare non può essere null");
		}
		if (this.ore > altro.ore) {
			return true;
		} else if (this.ore == altro.ore) {
			if (this.minuti > altro.minuti) {
				return true;
			} else if (this.minuti == altro.minuti) {
				return this.secondi > altro.secondi;
			}
		}
		return false;
	}
}