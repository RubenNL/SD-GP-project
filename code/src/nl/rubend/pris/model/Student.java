package nl.rubend.pris.model;

import java.util.Objects;

public class Student extends Gebruiker {
	private int studentNummer;

	public Student(String email, String wachtwoord, String naam, int sN) {
		super(email, wachtwoord, naam);
		this.studentNummer = sN;
	}

	public int getStudentNummer() {
		return studentNummer;
	}

	public void setStudentNummer(int studentNummer) {
		this.studentNummer = studentNummer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return studentNummer == student.studentNummer;
	}

}
