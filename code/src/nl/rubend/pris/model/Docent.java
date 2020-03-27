package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Docent extends Gebruiker implements Serializable {
	private int docentNummer;
	private Cursus cursus;
	private ArrayList<Les> lessen = new ArrayList<>();


	public Docent (String email, String wachtwoord, String naam, int dN) {
		super(email, wachtwoord, naam);
		this.docentNummer = dN;
	}

	public int getDocentNummer() {
		return docentNummer;
	}

	public void addLes(Les les) { this.lessen.add(les); }
	public ArrayList<Les> getLessen() {
		return lessen;
	}

	public void setCursus(Cursus cursus) {this.cursus = cursus;}

	public Cursus getCursus() {return this.cursus;}
	public ArrayList<Les> getLessenByDag(LocalDate date) {
		ArrayList<Les> response=new ArrayList<Les>();
		for(Les les:getLessen()) {
			if(les.getDatum().equals(date)) response.add(les);
		}
		return response;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Docent docent = (Docent) o;

		return docentNummer == docent.docentNummer;
	}

	@Override
	public int hashCode() {
		return docentNummer;
	}


}

