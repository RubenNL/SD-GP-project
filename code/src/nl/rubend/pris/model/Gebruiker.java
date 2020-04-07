package nl.rubend.pris.model;
import nl.rubend.pris.Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class Gebruiker implements Serializable {
	private String email;
	private String naam;
	private String saltString;
	private String hashedPassword;
	private static SecureRandom random = new SecureRandom();
	public final static ArrayList<Class> gebruikerTypes=new ArrayList<>() {{
		add(Student.class);
		add(Docent.class);
		add(Systeembeheerder.class);
	}};

	public Gebruiker(String email, String wachtwoord, String naam) throws IllegalArgumentException {
		setEmail(email);
		setWachtwoord(wachtwoord);
		this.naam=naam;
	}

	// Getters
	public String getNaam() {return this.naam;}

	public String getEmail() {return this.email;}

	public static String hash(String password, String saltString) {
		byte[] salt = Base64.getUrlDecoder().decode(saltString);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		try {
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			Base64.Encoder enc = Base64.getEncoder();
			return enc.encodeToString(f.generateSecret(spec).getEncoded());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			System.out.println("Systeem ondersteund delen van hashing niet,kan niet worden uitgevoerd op dit apparaat.");
		}
		return null;
	}

	public boolean checkWachtwoord(String wachtwoord) {
		return hash(wachtwoord,this.saltString).equals(hashedPassword);
	}


	// Setters
	public void setNaam(String naam) {
		if (naam != null && (!naam.equals("")) && Utils.isAlpha(naam)) {
			this.naam=naam;
		} else {
			throw new IllegalArgumentException("Ongeldige waarde");
		}
	}
	public void setEmail(String email) throws UnsupportedOperationException {
		String pattern="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		if(email!=null && email.matches(pattern)) this.email = email;
		else throw new IllegalArgumentException("Emailadres niet correct");
	}
//
//	public void setWachtwoord(String password)  {
//		if (password != null) {
//			byte[] salt = new byte[16];
//			random.nextBytes(salt);
//			Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
//			this.saltString = enc.encodeToString(salt);
//			this.hashedPassword = hash(password, this.saltString);
//		}
//	}

	public void setWachtwoord(String password)  {
		if (password != null) {
			if (password.length() >= 8) {
				byte[] salt = new byte[16];
				random.nextBytes(salt);
				Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
				this.saltString = enc.encodeToString(salt);
				this.hashedPassword = hash(password, this.saltString);
			} else {
				throw new IllegalArgumentException("Wachtwoord is te kort!");
			}
		} else throw new IllegalArgumentException("Ongeldige waarde");
	}


	// Equals
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		Gebruiker gebruiker = (Gebruiker) o;
		return gebruiker.getEmail().equals(this.email);
	}
}
