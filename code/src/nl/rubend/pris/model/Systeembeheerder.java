package nl.rubend.pris.model;

import java.io.Serializable;

public class Systeembeheerder extends Gebruiker implements Serializable {
	public Systeembeheerder (String email, String wachtwoord, String naam) {
		super(email, wachtwoord, naam);
	}
}