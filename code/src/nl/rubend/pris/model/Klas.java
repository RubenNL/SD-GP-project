package nl.rubend.pris.model;

import java.util.ArrayList;

public class Klas {
	private String klasNaam;
	private ArrayList<Cursus> cursusen;

	public Klas(String nm) {
		this.klasNaam = nm;
		cursusen = new ArrayList<>();
	}

	public String getKlasNaam() {
		return klasNaam;
	}

	public void setKlasNaam(String klasNaam) {
		this.klasNaam = klasNaam;
	}
}
