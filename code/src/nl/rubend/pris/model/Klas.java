package nl.rubend.pris.model;

import java.util.ArrayList;

public class Klas {
	private String klasNaam;
	private ArrayList<Cursus> cursusen;
	private ArrayList<Student> studenten;
	private ArrayList<RoosterItem> roosterItems;

	public Klas(String nm) {
		this.klasNaam = nm;
		cursusen = new ArrayList<>();
		studenten = new ArrayList<>();
		roosterItems = new ArrayList<>();
	}

	public String getKlasNaam() {
		return klasNaam;
	}

	public void setKlasNaam(String klasNaam) {
		this.klasNaam = klasNaam;
	}
}
