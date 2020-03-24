package nl.rubend.pris.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RoosterItem {
	private LocalTime beginTijd;
	private LocalTime eindTijd;
	private LocalDate datum;
	private String lokaal;
	private ArrayList<Klas> klassen;
	private ArrayList<Docent> docenten;

	public RoosterItem(LocalTime bTijd, LocalTime eTijd, LocalDate dtm, String lokaalk) {
		this.beginTijd = bTijd;
		this.eindTijd = eTijd;
		this.datum = dtm;
		this.lokaal = lokaalk;

		klassen = new ArrayList<>();
		klassen = new ArrayList<>();
	}

	public LocalDate getDatum() {
		return datum;
	}

	public LocalTime getBeginTijd() {
		return beginTijd;
	}

	public LocalTime getEindTijd() {
		return eindTijd;
	}

	public String getLokaal() {
		return lokaal;
	}

	public void setBeginTijd(LocalTime beginTijd) {
		this.beginTijd = beginTijd;
	}

	public void setEindTijd(LocalTime eindTijd) {
		this.eindTijd = eindTijd;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public void setLokaal(String lokaal) {
		this.lokaal = lokaal;
	}
}
