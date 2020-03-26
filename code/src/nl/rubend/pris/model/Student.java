package nl.rubend.pris.model;

import java.util.ArrayList;
import java.util.Objects;

public class Student extends Gebruiker {
	private int studentNummer;
	private ArrayList<Klas> klassen=new ArrayList<Klas>();
	private ArrayList<Aanwezigheid> aanwezigheid=new ArrayList<Aanwezigheid>();
	public Student(String email, String wachtwoord, String naam, int sN) {
		super(email, wachtwoord, naam);
		this.studentNummer = sN;
	}

	public int getStudentNummer() {
		return studentNummer;
	}
	protected void addKlas(Klas klas) {
		this.klassen.add(klas);
	}
	public ArrayList<Klas> getKlassen() {
		return this.klassen;
	}
	public void addAanwezigheid(Aanwezigheid aanwezigheid) {
		this.aanwezigheid.add(aanwezigheid);
	}
	public ArrayList<Aanwezigheid> getAanwezigheidList() {
		return aanwezigheid;
	}
	public Aanwezigheid getAanwezigheidBijLes(Les les) {
		for(Aanwezigheid aanwezigheid:this.aanwezigheid) {
			if(aanwezigheid.getLes().equals(les)) return aanwezigheid;
		}
		return null;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return studentNummer == student.studentNummer;
	}

}
