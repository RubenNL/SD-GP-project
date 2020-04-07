package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import nl.rubend.pris.model.Gebruiker;

import static org.junit.jupiter.api.Assertions.*;

class GebruikerTest {


	private Gebruiker gebruiker;
	@BeforeEach
	public void init() {
		gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
	}
	// setNaam() testen
	@Test
	public void test_NaamAlsLegeString() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setNaam(""),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}

	@Test
	public void test_NaamGelijkAanNull() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setNaam(null),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}


	@Test
	public void test_NaamBevatNietAlphabetischeCharacters() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setNaam("Marks123"),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}

	@Test
	public void test_NaamUitNietAlphabetischeCharacters() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setNaam("2342$#@"),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}



	// setMail() testen
	@Test
	public void test_EmailNotMatchesPattern1() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setEmail("test@iana.org-"),"expected exception");
		assertEquals("Emailadres niet correct", e.getMessage());
	}


	@Test
	public void test_EmailNotMatchesPattern2() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setEmail("sdf @hu.nl"),"expected exception");
		assertEquals("Emailadres niet correct", e.getMessage());
	}


	@Test
	public void test_EmailGelijkAanNull() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setEmail(null),"expected exception");
		assertEquals("Emailadres niet correct", e.getMessage());
	}


	@Test
	public void test_EmailNotMatchesPattern3() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setEmail("sdfhu.nl"),"expected exception");
		assertEquals("Emailadres niet correct", e.getMessage());
	}

	@Test
	public void EmailAlsEenLegeString() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setEmail(""),"expected exception");
		assertEquals("Emailadres niet correct", e.getMessage());
	}

	@Test
	public void test_EmailExceptionBijInstantieren() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->new Gebruiker("sdfsdfsdf", "sdfsdf", "qwe"),"expected exception");
		assertEquals("Emailadres niet correct", e.getMessage());
	}


	@Test
	public void test_WachtwoordTekortIs() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->new Gebruiker("qwe@hu.nl", "sdfsdf", "qwe"),"expected exception");
		assertEquals("Wachtwoord is te kort!", e.getMessage());
	}

	@Test
	public void test_WachtwoordGelijkAanNull() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->new Gebruiker("qwe@hu.nl", null, "qwe"),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}

}