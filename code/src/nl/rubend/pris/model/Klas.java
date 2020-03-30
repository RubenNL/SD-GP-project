package nl.rubend.pris.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Klas implements Serializable {
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
	public ArrayList<Student> getStudenten() { return studenten; }

	public void addStudent(Student student) {
		this.studenten.add(student);
		student.addKlas(this);
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

	public String toString() {
		return "klas: " + this.klasNaam;
	}
}
