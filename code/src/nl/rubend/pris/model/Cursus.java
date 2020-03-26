package nl.rubend.pris.model;

import java.util.ArrayList;

public class Cursus {
	private String cursusCode;
	private String cursusNaam;
	private ArrayList<Klas> klassen=new ArrayList<Klas>();
	public Cursus(String cursusCode, String cursusNaam) {
		this.cursusCode = cursusCode;
		this.cursusNaam = cursusNaam;
	}
	protected void addKlas(Klas klas) {this.klassen.add(klas);}
	public ArrayList<Klas> getKlassen() {return this.klassen;}
	public String getCursusNaam() {
		return cursusNaam;
	}
	public String getCursusCode() {
		return cursusCode;
	}

}
