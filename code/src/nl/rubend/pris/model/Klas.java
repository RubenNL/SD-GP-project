package nl.rubend.pris.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Klas implements Serializable {
	private String klasNaam;
	private ArrayList<Cursus> cursusen = new ArrayList<>();
	private ArrayList<Student> studenten = new ArrayList<>();
	private ArrayList<Les> lessen = new ArrayList<>();

	public Klas(String nm) {
		this.klasNaam = nm;
	}



	// Getters
	public String getKlasNaam() {
		return klasNaam;
	}
	public ArrayList<Student> getStudenten() { return studenten; }
	public ArrayList<Cursus> getCursusen() {
		return this.cursusen;
	}

	public ArrayList<Les> getLessenOpDag(LocalDate date) {
		ArrayList<Les> response=new ArrayList<Les>();
		for(Les les:lessen) {
			if(les.getDatum().equals(date)) response.add(les);
		}
		return response;
	}

	public Map<Student,Aanwezigheid> getAanwezigheidBijLes(Les les) {
		Map<Student,Aanwezigheid> response=new HashMap<Student,Aanwezigheid>();
		for(Student student:getStudenten()) {
			response.put(student,student.getAanwezigheidBijLes(les));
		}
		return response;
	}



	// Adders en Setters
	protected void addLes(Les les) {
		if (les != null && (!lessen.contains(les))) {
			this.lessen.add(les);
		}else throw new IllegalArgumentException("Onjuiste waarde");

	}
	public void addStudent(Student student) {
		if (student != null && (!studenten.contains(student))) {
			this.studenten.add(student);
			student.addKlas(this);
		} else throw new IllegalArgumentException("Onjuiste waarde");
	}
	public void addCursus(Cursus cursus) {
		if (cursus != null && !cursusen.contains(cursus)) {
			this.cursusen.add(cursus);
			cursus.addKlas(this);
		} else throw new IllegalArgumentException("Onjuiste waarde");
	}
	public void removeStudent(Student student) {
		this.studenten.remove(student);
	}
	public void removeLes(Les les) {this.lessen.remove(les);}

	public void removeKlas() {
		for (Cursus cursus: cursusen) cursus.removeKlas(this);
		for (Student student:studenten) student.removeKlas(this);
		for(Les les:lessen) les.removeKlas(this);
		studenten.removeAll(studenten);
		cursusen.removeAll(cursusen);
		lessen.removeAll(lessen);
		School.getSchool().removeKlas(this);
	}
	public void removeCursus(Cursus cursus) {
		this.cursusen.remove(cursus);
	}


	//Equals
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Klas klas = (Klas) o;
		return klasNaam.equals(klas.klasNaam);
	}

	@Override
	public int hashCode() {
		return Objects.hash(klasNaam);
	}

	// To String
	public String toString() {
//		het was eerder zo:
//		return "klas: " + this.klasNaam;
		return this.klasNaam;
	}




}
