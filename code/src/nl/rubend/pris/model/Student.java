package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Gebruiker implements Serializable,RemovableAccount {
	private int studentNummer;
	private boolean langdurigAfwezig;
	private ArrayList<Klas> klassen=new ArrayList<>();
	private ArrayList<Aanwezigheid> aanwezigheid=new ArrayList<>();
	private Docent slber;

	public Student(String email, String wachtwoord, String naam, int sN,Docent slber) {
		super(email, wachtwoord, naam);
		this.studentNummer = sN;
		setSlber(slber);
	}

	// Getters
	public int getStudentNummer() {
		return studentNummer;
	}

	public ArrayList<Aanwezigheid> getAanwezigheidList() {
		return aanwezigheid;
	}

	public ArrayList<Klas> getKlassen() {
		return this.klassen;
	}
	public ArrayList<Les> getLessenOpDag(LocalDate dag) {
		ArrayList<Les> response=new ArrayList<Les>();
		for(Klas klas:klassen) response.addAll(klas.getLessenOpDag(dag));
		return response;
	}

	public Aanwezigheid getAanwezigheidBijLes(Les les) {
		for(Aanwezigheid aanwezigheid:this.aanwezigheid) {
			if(aanwezigheid.getLes().equals(les)) return aanwezigheid;
		}
		Aanwezigheid aanwezigheid=new Aanwezigheid(les);
		addAanwezigheid(aanwezigheid);
		return aanwezigheid;
	}

	public boolean isLangdurigAfwezig() {
		return langdurigAfwezig;
	}

	public Docent getSlber() {
		return this.slber;
	}

	// Setters en Adders
	public void setLangdurigAfwezig(boolean langdurigAfwezig) {
		this.langdurigAfwezig = langdurigAfwezig;
	}

	public void addKlas(Klas klas) {
		if (klas != null && (!klassen.contains(klas))) {
			this.klassen.add(klas);
		} else {
			throw new IllegalArgumentException("Ongeldige waarde");
		}
	}

	public void addAanwezigheid(Aanwezigheid aanwezigheid) {
		if (aanwezigheid != null) {
			this.aanwezigheid.add(aanwezigheid);
		}
	}
	public void setSlber(Docent slber) {
		if (slber != null) {
			if(slber instanceof Docent) {
				if (this.slber instanceof Docent) {
					this.slber.removeSlbStudent(this);
				}
				this.slber = slber;
				this.slber.addSlbStudent(this);
			} else throw new IllegalArgumentException("SLBer is geen Docent");
		} else throw new IllegalArgumentException("Geen slber gegeven");
	}
	public void removeSlber() {
		this.slber=null;
	}
	@Override
	public void removeAccount() {
		for(Klas klas:getKlassen()) {
			klas.removeStudent(this);
		}
		klassen.removeAll(klassen);
		for(Aanwezigheid melding:aanwezigheid) {
			melding.removeGebruiker(true);
			melding.removeLes();
		}
		slber.removeSlbStudent(this);
	}
	public void removeKlas(Klas klas) {
		this.klassen.remove(klas);
	}

	public String toString() {
		return this.getNaam();
	}
	//Equals
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Student student = (Student) o;

		return studentNummer == student.studentNummer;
	}

	@Override
	public int hashCode() {
		return studentNummer;
	}
}
