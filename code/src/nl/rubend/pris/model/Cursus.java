package nl.rubend.pris.model;

public class Cursus {
	private String cursusNummer;
	private String cursusNaam;

	public Cursus(String cNu, String cNa) {
		this.cursusNummer = cNu;
		this.cursusNaam = cNa;
	}

	public String getCursusNaam() {
		return cursusNaam;
	}

	public String getCursusNummer() {
		return cursusNummer;
	}

	public void setCursusNaam(String cursusNaam) {
		this.cursusNaam = cursusNaam;
	}

}
