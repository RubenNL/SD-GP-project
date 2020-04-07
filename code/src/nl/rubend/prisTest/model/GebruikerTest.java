package nl.rubend.prisTest.model;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import nl.rubend.pris.model.Gebruiker;

import static org.junit.jupiter.api.Assertions.*;

class GebruikerTest {




    // setNaam() testen
    @Test
    public void test_NaamAlsLegeString() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setNaam("");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }

    @Test
    public void test_NaamGelijkAanNull() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setNaam(null);
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }


    @Test
    public void test_NaamBevatNietAlphabetischeCharacters() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setNaam("Marks123");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }

    @Test
    public void test_NaamUitNietAlphabetischeCharacters() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setNaam("2342$#@");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }



    // setMail() testen
    @Test
    public void test_EmailNotMatchesPattern1() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setEmail("sdf@eee.eee");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Emailadres niet correct", iae.getMessage());
        }
    }


    @Test
    public void test_EmailNotMatchesPattern2() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setEmail("sdf @hu.nl");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Emailadres niet correct", iae.getMessage());
        }
    }


    @Test
    public void test_EmailGelijkAanNull() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setEmail(null);
        }
        catch (IllegalArgumentException iae){
            assertEquals("Emailadres niet correct", iae.getMessage());
        }
    }


    @Test
    public void test_EmailNotMatchesPattern3() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setEmail("sdfhu.nl");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Emailadres niet correct", iae.getMessage());
        }
    }

    @Test
    public void EmailAlsEenLegeString() {
        Gebruiker gebruiker = new Gebruiker("qwe@hu.nl", "sssadfsdfsdf", "qwe");
        try {
            gebruiker.setEmail("");
        }
        catch (IllegalArgumentException iae){
            assertEquals("Emailadres niet correct", iae.getMessage());
        }
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