package nl.rubend.pris.model;

import java.util.ArrayList;
import java.util.Objects;

public class Docent extends Gebruiker {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Docent docent = (Docent) o;
		return docentNummer == docent.docentNummer;
	}


}
