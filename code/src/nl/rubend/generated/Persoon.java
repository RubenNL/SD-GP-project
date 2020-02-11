package nl.rubend.generated;

public class Persoon {

	private String email;
	private String wachtwoord;
	private String naam;

	/**
	 * 
	 * @param email
	 * @param wachtwoord
	 * @param naam
	 */
	public Persoon(String email, String wachtwoord, String naam) {
		this.email=email;
		this.wachtwoord=wachtwoord;
		this.naam=naam;
	}

	public String getEmail() {
		return this.email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getWachtwoord() {
		return this.wachtwoord;
	}

	/**
	 * 
	 * @param wachtwoord
	 */
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getNaam() {
		return this.naam;
	}

	/**
	 * 
	 * @param naam
	 */
	public void setNaam(String naam) {
		this.naam = naam;
	}

}