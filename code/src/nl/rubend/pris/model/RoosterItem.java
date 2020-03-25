package nl.rubend.pris.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoosterItem that = (RoosterItem) o;
		return Objects.equals(beginTijd, that.beginTijd) &&
				Objects.equals(eindTijd, that.eindTijd) &&
				Objects.equals(datum, that.datum) &&
				Objects.equals(lokaal, that.lokaal);
	}





}
