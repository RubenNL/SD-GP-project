package nl.rubend.pris;

public class Gebruiker {
	private String email;
	private String wachtwoord;
	private String naam;
	public Gebruiker(String email, String wachtwoord, String naam) {
		this.email=email;
		this.wachtwoord=wachtwoord;
		this.naam=naam;
	}
	public String getNaam() {return this.naam;}
	public boolean checkWachtwoord(String wachtwoord) {return this.wachtwoord.equals(wachtwoord);}
	public String getEmail() {return this.email;}
	public void setNaam(String naam) {this.naam=naam;}
	public void setWachtwoord(String wachtwoord) {this.wachtwoord=wachtwoord;}
	public void setEmail(String email) {this.email=email;}
}
