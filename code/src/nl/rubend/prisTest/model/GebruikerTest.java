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
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->gebruiker.setEmail("sdf@eee.eee"),"expected exception");
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
		try {
			Gebruiker gebruiker = new Gebruiker("sdfsdfsdf", "sdfsdf", "qwe");
		}
		catch (IllegalArgumentException iae){
			assertEquals("Emailadres niet correct", iae.getMessage());
		}
	}


	@Test
	public void test_WachtwoordTekortIs() {
		// korter dan 8 characters
		try {
			Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sdfsdf", "qwe");
		}
		catch (IllegalArgumentException iae){
			assertEquals("Wachtwoord is te kort!", iae.getMessage());
		}
	}

	@Test
	public void test_WachtwoordGelijkAanNull() {
		try {
			Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", null, "qwe");
		}
		catch (IllegalArgumentException iae){
			assertEquals("Ongeldige waarde", iae.getMessage());
		}
	}

}