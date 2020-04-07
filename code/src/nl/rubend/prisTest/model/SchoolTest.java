package model;

import nl.rubend.pris.Main;
import nl.rubend.pris.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static nl.rubend.pris.Main.serializeDemoData;
import static nl.rubend.pris.model.School.deserialize;
import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

    static Student st1, st2;

    @BeforeEach
    public void initialize() {
        try {
            st1 = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);
            st2 = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);

        } catch (Exception e) {
            String errorMessage = "Exception: " + e.getMessage();
            System.out.println(errorMessage);
        }
    }




    @Test
    public void test_KlasGelijkAanNullToevoegen() {
        School school=School.getSchool();
        try {
            school.addKlas(null);
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }


    @Test
    public void test_GebruikerGelijkAanNullToevoegen() {
        School school=School.getSchool();
        try {
            school.addGebruiker(null);
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }



    @Test
    public void test_CursusGelijkAanNullToevoegen() {
        School school=School.getSchool();
        try {
            school.addCursus(null);
        }
        catch (IllegalArgumentException iae){
            assertEquals("Ongeldige waarde", iae.getMessage());
        }
    }


    @Test
    void test_generiek_BestaandeGebruikerToevoegen() {
        School school=School.getSchool();
        Gebruiker gebruiker1 = new Gebruiker("jan@hu.nl", "sssadfsdfsdf", "jan");
        school.addGebruiker(gebruiker1);
        Gebruiker gebruiker2 = new Gebruiker("jan@hu.nl", "sssadfsdfsdf", "jan");
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> school.addGebruiker(gebruiker2),
                "Gebruiker (" + gebruiker2 + ") bestaat al"
        );

        assertTrue(thrown.getMessage().contains("bestaat al"));
    }


    @Test
    void test_dezelfdeSubklasse_BestaandeGebruikerToevoegen() {
        School school=School.getSchool();
        Student student1 = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);
        school.addGebruiker(student1);
        Student student2 = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () ->             school.addGebruiker(student2),
                "Gebruiker (" + student2 + ") bestaat al"
        );

        assertTrue(thrown.getMessage().contains("bestaat al"));
    }


    @Test
    void test_verschillendeSubklasse_BestaandeGebruikerToevoegen() {
        School school=School.getSchool();
        Student student = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);
        school.addGebruiker(student);
        Docent docent = new Docent("jan@hu.nl", "345efgdgfdgf", "jan", 1234);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () ->            school.addGebruiker(docent),
                "Gebruiker (" + docent + ") bestaat al"
        );

        assertTrue(thrown.getMessage().contains("bestaat al"));
    }


    @Test
    public void test_BestaandeKlasToevoegen() {
        School school=School.getSchool();
        Klas klas1 = new Klas("TICT-SD-V1E");
        school.addKlas(klas1);
        Klas klas2 = new Klas("TICT-SD-V1E");

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> school.addKlas(klas2),
                "Klas (" + klas2 + ") bestaat al"
        );

        assertTrue(thrown.getMessage().contains("bestaat al"));
    }


    @Test
    public void test_BestaandeCursusToevoegen() {
        School school=School.getSchool();
        Cursus cursus1 =  new Cursus("TCIF-V1GP-19_2019","SD-GroupProject");
        school.addCursus(cursus1);
        Cursus cursus2 =  new Cursus("TCIF-V1GP-19_2019","SD-GroupProject");

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> school.addCursus(cursus2),
                "Cursus (" + cursus2 + ") bestaat al"
        );

        assertTrue(thrown.getMessage().contains("bestaat al"));

    }


    @Test
    public void test_GebruikerGetByEmail() throws NotFoundException {
        School school=School.getSchool();
        Student student = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);
        school.addGebruiker(student);
        assertEquals(school.getGebruikerByEmail("jan@hu.nl"), student);
    }


    @Test
    public void test_GebruikerGetByEmailNotFound() throws NotFoundException {
        School school=School.getSchool();
        Student student = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);
        school.addGebruiker(student);

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> school.getGebruikerByEmail("test@email.com"),
                "Gebruiker niet gevonden"
        );

        assertTrue(thrown.getMessage().contains("niet gevonden"));
    }






    @Test
    public void test_KlasGetByName() throws NotFoundException {
        School school=School.getSchool();
        Klas klas = new Klas("TICT-SD-V1E");
        school.addKlas(klas);
        assertEquals(school.getKlasByName("TICT-SD-V1E"), klas);
    }


    @Test
    public void test_KlasGetByNameNotFound() throws NotFoundException {
        School school=School.getSchool();
        Klas klas = new Klas("TICT-SD-V1E");
        school.addKlas(klas);
        assertEquals(school.getKlasByName("TICT-SD-V1E"), klas);

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> school.getKlasByName("test"),
                "Klas niet gevonden"
        );

        assertTrue(thrown.getMessage().contains("niet gevonden"));
    }







    @Test
    public void test_CursusGetByCode() throws NotFoundException {
        School school=School.getSchool();
        Cursus cursus =  new Cursus("TCIF-V1GP-19_2019","SD-GroupProject");
        school.addCursus(cursus);
        assertEquals(school.getCursusByCode("TCIF-V1GP-19_2019"), cursus);
    }


    @Test
    public void test_CursusGetByCodeNotFound() throws NotFoundException {
        School school=School.getSchool();
        Cursus cursus =  new Cursus("TCIF-V1GP-19_2019","SD-GroupProject");
        school.addCursus(cursus);

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> school.getCursusByCode("test"),
                "Cursus niet gevonden"
        );

        assertTrue(thrown.getMessage().contains("niet gevonden"));
    }

    @Test
    public void test_serialize_deserialize() {
        assertDoesNotThrow(()->serializeDemoData(),"Serialize zou geen foutmelding moeten geven");
        //Gebruik van serializeDemoData omdat hier alle types al in staan, en dus ook getest worden.
        assertDoesNotThrow(()->deserialize(),"Deserialize zou geen foutmelding moeten geven");
    }
}