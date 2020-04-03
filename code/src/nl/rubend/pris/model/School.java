package nl.rubend.pris.model;

import java.util.ArrayList;
import java.io.*;

public class School implements Serializable {

	private ArrayList<Gebruiker> gebruikers=new ArrayList<>();
	private ArrayList<Klas> klassen=new ArrayList<>();
	private ArrayList<Cursus> cursussen=new ArrayList<>();

	private static School school=new School();

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
	public void addGebruiker(Gebruiker gebruiker) throws NotFoundException {
		if (!gebruikers.contains(gebruiker)) {
			gebruikers.add(gebruiker);
		}
		else {
			throw new NotFoundException("\"" + gebruiker.getNaam() + "\" object bestaat al!");
		}
	}

	public void addCursus(Cursus cursus) {
		if (cursus != null) {
			this.cursussen.add(cursus);
		}
	}

	public void addKlas(Klas klas) {
		if (klas != null) {
			this.klassen.add(klas);
		}
	}

	public static void serialize() {
		try {
			FileOutputStream fileout= new FileOutputStream("out.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(school);
			out.close();
			fileout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void deserialize() throws IOException, ClassNotFoundException,FileNotFoundException {
		FileInputStream fileIn = new FileInputStream("out.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		school = (School) in.readObject();
		in.close();
		fileIn.close();
	}
}
