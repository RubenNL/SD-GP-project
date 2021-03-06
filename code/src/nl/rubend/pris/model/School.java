package nl.rubend.pris.model;

import java.util.ArrayList;
import java.io.*;
import java.util.Objects;

public class School implements Serializable {

	private ArrayList<Gebruiker> gebruikers=new ArrayList<>();
	private ArrayList<Klas> klassen=new ArrayList<>();
	private ArrayList<Cursus> cursussen=new ArrayList<>();

	private static School school=new School();
	public static void resetSchool() {school=new School();}
	// Getters (returnen een object)
	public ArrayList<Klas> getKlassen() { return this.klassen; }
	public ArrayList<Gebruiker> getGebruikers() {
		return gebruikers;
  }

	public ArrayList<Cursus> getCursussen() { return this.cursussen; }

	public static School getSchool() {
		return school;
	}

	public Gebruiker getGebruikerByEmail(String email) throws NotFoundException {
		for(Gebruiker gebruiker:gebruikers) {
			if(email.toLowerCase().equals(gebruiker.getEmail())) return gebruiker;
		}
		throw new NotFoundException("Gebruiker niet gevonden");
	}

	public Klas getKlasByName(String klasNaam) throws NotFoundException {
		for(Klas klas:klassen) {
			if(klas.getKlasNaam().equals(klasNaam)) {
				return klas;
			}
		}
		throw new NotFoundException("Klas niet gevonden");
	}

	public Cursus getCursusByCode(String naam) throws NotFoundException {
		for(Cursus cursus:cursussen) {
			if(cursus.getCursusCode().equals(naam)) return cursus;
		}
		throw new NotFoundException("Cursus niet gevonden");
	}


	// Adders (returnen void)
	public void addGebruiker(Gebruiker gebruiker)  {
		if (gebruiker != null && gebruiker instanceof Gebruiker) {
			if (!gebruikers.contains(gebruiker)) {
				gebruikers.add(gebruiker);
			} else throw new IllegalArgumentException("Gebruiker (" + gebruiker + ") bestaat al" );
		} else throw new IllegalArgumentException("Gebruiker is geen Gebruiker?");
	}

	public void addCursus(Cursus cursus) {
		if (cursus != null) {
			if (!cursussen.contains(cursus)) {
				this.cursussen.add(cursus);
			} else throw new IllegalArgumentException("Cursus (" + cursus + ") bestaat al");
		} else throw new IllegalArgumentException("Ongeldige waarde");
	}

	public void addKlas(Klas klas) {
		if (klas != null) {
			if (!klassen.contains(klas)) this.klassen.add(klas);
			else throw new IllegalArgumentException("Klas (" + klas + ") bestaat al");
		} else throw new IllegalArgumentException("Ongeldige waarde");
	}

	public void removeKlas(Klas klas) throws NotFoundException {
		if (this.klassen.contains(klas)) this.klassen.remove(klas);
		else throw new NotFoundException("Klas niet gevonden");
	}

	public void removeCursus(Cursus cursus) throws NotFoundException {
		if (this.cursussen.contains(cursus)) this.cursussen.remove(cursus);
		else throw new NotFoundException("Cursus niet gevonden");
	}

	public static void serialize() {
		try {
			FileOutputStream fileout= new FileOutputStream("out.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(school);
			out.close();
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void deserialize() throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream("out.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		school = (School) in.readObject();
		in.close();
		fileIn.close();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		School school = (School) o;
		return Objects.equals(gebruikers, school.gebruikers) &&
				Objects.equals(klassen, school.klassen) &&
				Objects.equals(cursussen, school.cursussen);
	}
}
