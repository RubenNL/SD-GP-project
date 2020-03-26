package nl.rubend.pris.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Klas {
	private String klasNaam;
	private ArrayList<Cursus> cursusen = new ArrayList<Cursus>();
	private ArrayList<Student> studenten = new ArrayList<Student>();
	private ArrayList<Les> lessen = new ArrayList<Les>();

	public Klas(String nm) {
		this.klasNaam = nm;
	}

	public String getKlasNaam() {
		return klasNaam;
	}

	public void addStudent(Student student) {
		this.studenten.add(student);
		student.addKlas(this);
	}
	public ArrayList<Student> getStudenten() {
		return this.studenten;
	}
	public Map<Student,Aanwezigheid> getAanwezigheidBijLes(Les les) {
		Map<Student,Aanwezigheid> response=new HashMap<Student,Aanwezigheid>();
		for(Student student:getStudenten()) {
			response.put(student,student.getAanwezigheidBijLes(les));
		}
		return response;
	}
	protected void addLes(Les les) {
		this.lessen.add(les);
	}
	public void addCursus(Cursus cursus) {
		this.cursusen.add(cursus);
		cursus.addKlas(this);
	}
	public ArrayList<Cursus> getCursusen() {
		return this.cursusen;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Klas klas = (Klas) o;
		return Objects.equals(klasNaam, klas.klasNaam);
	}

}
