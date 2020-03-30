package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Docent extends Gebruiker implements Serializable,RemovableAccount {
	private int docentNummer;
	private Cursus cursus;
	private ArrayList<Les> lessen = new ArrayList<>();
	private ArrayList<Aanwezigheid> aanwezigheidsmeldingen = new ArrayList<Aanwezigheid>();

	public Docent (String email, String wachtwoord, String naam, int dN) {
		super(email, wachtwoord, naam);
		this.docentNummer = dN;
	}

	public int getDocentNummer() {
		return docentNummer;
	}
	public void addAanwezigheid(Aanwezigheid aanwezigheid) {
		this.aanwezigheidsmeldingen.add(aanwezigheid);
	}
	public void removeAanwezigheid(Aanwezigheid aanwezigheid) {
		this.aanwezigheidsmeldingen.remove(aanwezigheid);
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


	@Override
	public void removeAccount() {
		for(Les les:this.lessen) {
			les.removeDocent(this);
		}
		for(Aanwezigheid aanwezigheid:this.aanwezigheidsmeldingen) {
			aanwezigheid.removeGebruiker(false);
		}
		aanwezigheidsmeldingen.removeAll(aanwezigheidsmeldingen);
		lessen.removeAll(lessen);
	}
	public void removeLes(Les les) {
		this.lessen.remove(les);
	}
}

