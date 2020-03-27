package nl.rubend.pris.model;

import java.util.ArrayList;
import java.io.*;

public class School implements Serializable {
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
	public static void deserialize() throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream("out.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		school = (School) in.readObject();
		in.close();
		fileIn.close();
	}
}
