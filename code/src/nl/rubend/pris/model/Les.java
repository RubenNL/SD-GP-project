package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Les implements Serializable {
	private LocalTime beginTijd;
	private LocalTime eindTijd;
	private LocalDate datum;
	private String lokaal;
	private Cursus cursus;
	private ArrayList<Klas> klassen = new ArrayList<>();
	private ArrayList<Docent> docenten = new ArrayList<>();

	public Les(LocalTime bTijd, LocalTime eTijd, LocalDate dtm, String lokaalk, Cursus cursus) {
		this.beginTijd = bTijd;
		this.eindTijd = eTijd;
		this.datum = dtm;
		this.lokaal = lokaalk;
		this.cursus=cursus;
		cursus.addLes(this);
	}

	// Getters
	public LocalTime getBeginTijd() {
		return beginTijd;
	}
	public LocalTime getEindTijd() {
		return eindTijd;
	}
	public LocalDate getDatum() { return datum; }
	public Cursus getCursus() {
		return this.cursus;
	}
	public String getLokaal() { return lokaal; }

	public ArrayList<Student> getStudenten() {
		ArrayList<Student> response=new ArrayList<Student>();
		for(Klas klas:klassen) {
			response.addAll(klas.getStudenten());
		}
		return response;
	}

	public Map<Student,Aanwezigheid> getAanwezigheid() {
		Map<Student,Aanwezigheid> response=new HashMap<Student,Aanwezigheid>();
		for(Klas klas:klassen) {
			response.putAll(klas.getAanwezigheidBijLes(this));
		}
		return response;
	}


	// Adders en Setters
	public void setLokaal(String lokaal) {
		if (lokaal != null) {
			this.lokaal = lokaal;
		}
	}
	public void addDocent(Docent docent) {
		if (docent != null) {
			docenten.add(docent);
			docent.addLes(this);
		}
	}
	public void addKlas(Klas klas) {
		if (klas != null) {
			klassen.add(klas);
			klas.addLes(this);
		}
	}
	public void removeDocent(Docent docent) {
		docenten.remove(docent);
	}
	public void removeKlas(Klas klas) {klassen.remove(klas);}
	public void unsetCursus() {
		try {
			this.cursus=School.getSchool().getCursusByCode("deleted");
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	// Equals
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Les that = (Les) o;
		return Objects.equals(beginTijd, that.beginTijd) &&
				Objects.equals(eindTijd, that.eindTijd) &&
				Objects.equals(datum, that.datum) &&
				Objects.equals(lokaal, that.lokaal);
	}

}
