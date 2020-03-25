package nl.rubend.pris.model;

import java.util.ArrayList;

public class School {
	private ArrayList<Gebruiker> gebruikers=new ArrayList<Gebruiker>();
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
	public static School getSchool() {
		return school;
	}
}
