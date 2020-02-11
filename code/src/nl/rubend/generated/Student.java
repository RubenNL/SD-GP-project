package nl.rubend.generated;

public class Student extends Persoon {

	private Integer studentNummer;

	public Integer getStudentNummer() {
		return studentNummer;
	}

	/**
	 * 
	 * @param studentNummer
	 */
	public void setStudentNummer(Integer studentNummer) {
		this.studentNummer=studentNummer;
	}

	/**
	 * 
	 * @param studentNummer
	 */
	public Student(Integer studentNummer) {
		this.studentNummer=studentNummer;
	}

}