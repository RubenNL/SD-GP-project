package nl.rubend.pris.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Les {
	private LocalTime beginTijd;
	private LocalTime eindTijd;
	private LocalDate datum;
	private String lokaal;
	private ArrayList<Klas> klassen = new ArrayList<Klas>();
	private ArrayList<Docent> docenten = new ArrayList<Docent>();

	public Les(LocalTime bTijd, LocalTime eTijd, LocalDate dtm, String lokaalk) {
		this.beginTijd = bTijd;
		this.eindTijd = eTijd;
		this.datum = dtm;
		this.lokaal = lokaalk;
	}
	public void addKlas(Klas klas) {
		klassen.add(klas);
		klas.addLes(this);
	}
	public void addDocent(Docent docent) {
		docenten.add(docent);
		docent.addLes(this);
	}
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

	public void setLokaal(String lokaal) {
		this.lokaal = lokaal;
	}


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