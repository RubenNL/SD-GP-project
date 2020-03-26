package nl.rubend.pris.model;

import java.util.ArrayList;

public class School {
	private ArrayList<Gebruiker> gebruikers=new ArrayList<Gebruiker>();
	private ArrayList<Klas> klassen=new ArrayList<Klas>();
	private ArrayList<Cursus> cursussen=new ArrayList<Cursus>();
	private static School school=new School();
	public void addGebruiker(Gebruiker gebruiker) {
		gebruikers.add(gebruiker);
	}
	public Gebruiker getGebruikerByEmail(String email) throws Exception {
		for(Gebruiker gebruiker:gebruikers) {
			if(email.toLowerCase().equals(gebruiker.getEmail())) return gebruiker;
		}
		throw new Exception("Gebruiker niet gevonden");
	}
	public void addCursus(Cursus cursus) { this.cursussen.add(cursus); }
	public ArrayList<Cursus> getCursussen() { return this.cursussen; }
	public Cursus getCursusByCode(String naam) {
		for(Cursus cursus:cursussen) {
			if(cursus.getCursusCode().equals(naam)) return cursus;
		}
		return null;
	}
	public void addKlas(Klas klas) {this.klassen.add(klas);}
	public Klas getKlasByName(String klasNaam) {
		for(Klas klas:klassen) {
			if(klas.getKlasNaam().equals(klasNaam)) {
				return klas;
			}
		}
		return null;
	}
	public static School getSchool() {
		return school;
	}
}
