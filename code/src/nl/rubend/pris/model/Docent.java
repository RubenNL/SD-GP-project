package nl.rubend.pris.model;

import java.util.ArrayList;

public class Docent extends Gebruiker {
	private String email;
	private String wachtwoord;
	private String naam;
	private int docentNummer;
	private ArrayList<RoosterItem> roosterItems;

	public Docent (String email, String wachtwoord, String naam, int dN) {
		super(email, wachtwoord, naam);
		this.docentNummer = dN;
		roosterItems = new ArrayList<>();
	}

	public int getDocentNummer() {
		return docentNummer;
	}

	public void setDocentNummer(int docentNummer) {
		this.docentNummer = docentNummer;
	}

}