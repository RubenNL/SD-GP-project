package nl.rubend.pris.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Cursus implements Serializable {
	private String cursusCode;
	private String cursusNaam;
	private ArrayList<Klas> klassen=new ArrayList<Klas>();
	private ArrayList<Les> lessen=new ArrayList<>();
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
	public void addLes(Les les) {this.lessen.add(les);}
	public void removeKlas(Klas klas) { this.klassen.remove(klas); }
	public void removeCursus() {
		for(Klas klas:klassen) klas.removeCursus(this);
		for(Les les:lessen) les.unsetCursus();
		klassen.removeAll(klassen);
		lessen.removeAll(lessen);
		School.getSchool().removeCursus(this);
	}
	public String toString() {
		return this.cursusCode;
	}


}
