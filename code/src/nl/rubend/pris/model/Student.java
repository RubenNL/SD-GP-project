package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Gebruiker implements Serializable,RemovableAccount {
	private int studentNummer;
	private boolean langdurigAfwezig;
	private ArrayList<Klas> klassen=new ArrayList<Klas>();
	private ArrayList<Aanwezigheid> aanwezigheid=new ArrayList<Aanwezigheid>();
	public Student(String email, String wachtwoord, String naam, int sN) {
		super(email, wachtwoord, naam);
		this.studentNummer = sN;
	}

	public int getStudentNummer() {
		return studentNummer;
	}
	public void addKlas(Klas klas) {
		this.klassen.add(klas);
	}
	public ArrayList<Klas> getKlassen() {
		return this.klassen;
	}
	public ArrayList<Les> getLessenOpDag(LocalDate dag) {
		ArrayList<Les> response=new ArrayList<Les>();
		for(Klas klas:klassen) response.addAll(klas.getLessenOpDag(dag));
		return response;
	}
	public void addAanwezigheid(Aanwezigheid aanwezigheid) {
		this.aanwezigheid.add(aanwezigheid);
	}
	public ArrayList<Aanwezigheid> getAanwezigheidList() {
		return aanwezigheid;
	}

	public boolean isLangdurigAfwezig() {
		return langdurigAfwezig;
	}

	public void setLangdurigAfwezig(boolean langdurigAfwezig) {
		this.langdurigAfwezig = langdurigAfwezig;
	}

	public Aanwezigheid getAanwezigheidBijLes(Les les) {
		for(Aanwezigheid aanwezigheid:this.aanwezigheid) {
			if(aanwezigheid.getLes().equals(les)) return aanwezigheid;
		}
		Aanwezigheid aanwezigheid=new Aanwezigheid(les);
		addAanwezigheid(aanwezigheid);
		return aanwezigheid;
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
	}
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
