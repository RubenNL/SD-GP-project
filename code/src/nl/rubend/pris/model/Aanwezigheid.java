package nl.rubend.pris.model;

import java.io.Serializable;

public class Aanwezigheid implements Serializable {
	private boolean status;
	private Les les;
	private Gebruiker gebruiker;
	public Aanwezigheid(Les les) {
		this.les=les;
	}
	public Aanwezigheid(Gebruiker gebruiker,boolean status,Les les) {
		this.status=status;
		this.les=les;
		this.gebruiker=gebruiker;
	}
	public void setStatus(Gebruiker gebruiker,boolean status) {
		this.status=status;
		this.gebruiker=gebruiker;
	}
	public boolean getStatus() {
		return this.status;
	}
	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker=gebruiker;
	}
	public Gebruiker getGebruiker() {
		return this.gebruiker;
	}
	public Les getLes() {return les;}
}
