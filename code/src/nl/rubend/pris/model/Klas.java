package nl.rubend.pris.model;

import java.util.ArrayList;
import java.util.Objects;

public class Klas {
	private String klasNaam;
	private ArrayList<Cursus> cursusen;
	private ArrayList<Student> studenten;
	//private ArrayList<RoosterItem> roosterItems;

	public Klas(String nm) {
		this.klasNaam = nm;
		cursusen = new ArrayList<>();
		studenten = new ArrayList<>();
		//roosterItems = new ArrayList<>();
	}

	public String getKlasNaam() {
		return klasNaam;
	}
	public ArrayList<Student> getStudenten() { return studenten; }
	public void setStudenten(Student stud) { studenten.add(stud); }

	public void setKlasNaam(String klasNaam) {
		this.klasNaam = klasNaam;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Klas klas = (Klas) o;
		return Objects.equals(klasNaam, klas.klasNaam);
	}

}
