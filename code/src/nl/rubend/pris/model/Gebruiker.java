package nl.rubend.pris.model;
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
	public final static ArrayList<Class> gebruikerTypes=new ArrayList<Class>() {{
		add(Student.class);
		add(Docent.class);
		add(Systeembeheerder.class);
	}};
	public Gebruiker(String email, String wachtwoord, String naam) {
		setEmail(email);
		setWachtwoord(wachtwoord);
		this.naam=naam;
	}
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
	public void setWachtwoord(String password)  {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
		this.saltString=enc.encodeToString(salt);
		this.hashedPassword=hash(password,this.saltString);
	}
	public String getNaam() {return this.naam;}
	public boolean checkWachtwoord(String wachtwoord) {
		return hash(wachtwoord,this.saltString).equals(hashedPassword);
	}
	public String getEmail() {return this.email;}
	public void setNaam(String naam) {this.naam=naam;}
	public void setEmail(String email) {this.email=email;}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Gebruiker gebruiker = (Gebruiker) o;
		return Objects.equals(email, gebruiker.email) ||
				Objects.equals(naam, gebruiker.naam);
	}
}
