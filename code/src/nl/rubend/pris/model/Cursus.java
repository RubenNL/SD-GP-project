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

	protected void addKlas(Klas klas) {
		if (klas != null) {
			this.klassen.add(klas);
		}
	}

	public void removeKlas(Klas klas) { this.klassen.remove(klas); }
//
//	@Override
//	public void removeGroup() {
//		// Moet nog ontzettend veel gewijzigd aan Domein Klassen wordt om
//		// volledig Klassen te kunnen verwijderen geloof ik....
//
//
//
//
//	}

	public String toString() {
		return this.cursusCode;
	}


}
