package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Docent extends Gebruiker implements Serializable,RemovableAccount {
	private int docentNummer;
	private Cursus cursus;
	private ArrayList<Les> lessen = new ArrayList<>();
	private ArrayList<Aanwezigheid> aanwezigheidsmeldingen = new ArrayList<>();
	private ArrayList<Student> slbStudenten=new ArrayList<>();

	public Docent (String email, String wachtwoord, String naam, int dN) throws IllegalArgumentException {
		super(email, wachtwoord, naam);
		this.docentNummer = dN;
	}

	// Getters
	public int getDocentNummer() {
		return docentNummer;
	}

	public Cursus getCursus() { return this.cursus; }

	public ArrayList<Les> getLessen() {
		return lessen;
	}

	public ArrayList<Les> getLessenByDag(LocalDate date) {
		ArrayList<Les> response=new ArrayList<Les>();
		for(Les les:getLessen()) {
			if(les.getDatum().equals(date)) response.add(les);
		}
		return response;
	}


	// Adders en Setters
	public void addAanwezigheid(Aanwezigheid aanwezigheid) {
		if (aanwezigheid != null) {
			this.aanwezigheidsmeldingen.add(aanwezigheid);
		}
	}
	public void removeAanwezigheid(Aanwezigheid aanwezigheid) {
		this.aanwezigheidsmeldingen.remove(aanwezigheid);
	}
	public void addLes(Les les) {
		if (les != null) {
			this.lessen.add(les);
		}
	}
	public void setCursus(Cursus cursus) {
		if (cursus != null)
		this.cursus = cursus;
	}

	public void removeLes(Les les) {
		this.lessen.remove(les);
	}
	public void removeSlbStudent(Student student) {
		this.slbStudenten.remove(student);
	}
	public void addSlbStudent(Student student) {
		if (student != null) {
			this.slbStudenten.add(student);
		}
	}

	@Override
	public void removeAccount() {
		for(Les les:this.lessen) les.removeDocent(this);
		for(Aanwezigheid aanwezigheid:this.aanwezigheidsmeldingen) aanwezigheid.removeGebruiker(false);
		for(Student student:slbStudenten) student.removeSlber();
		aanwezigheidsmeldingen.removeAll(aanwezigheidsmeldingen);
		slbStudenten.removeAll(slbStudenten);
		lessen.removeAll(lessen);
	}
}

