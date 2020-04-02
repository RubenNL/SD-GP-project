package nl.rubend.pris.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Cursus implements Serializable {
	private String cursusCode;
	private String cursusNaam;
	private ArrayList<Klas> klassen=new ArrayList<Klas>();

	public Cursus(String cursusCode, String cursusNaam) {
		this.cursusCode = cursusCode;
		this.cursusNaam = cursusNaam;
	}

	public String getCursusNaam() {
		return cursusNaam;
	}
	public String getCursusCode() {
		return cursusCode;
	}

	protected void addKlas(Klas klas) {this.klassen.add(klas);}
}
