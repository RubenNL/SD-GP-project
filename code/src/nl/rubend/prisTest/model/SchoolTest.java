package model;

import nl.rubend.pris.Main;
import nl.rubend.pris.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static nl.rubend.pris.Main.serializeDemoData;
import static nl.rubend.pris.model.School.deserialize;
import static nl.rubend.pris.model.School.getSchool;
import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

	private School school;
	@BeforeEach
	public void initialize() {
		School.resetSchool();
		school=School.getSchool();
	}




	@Test
	public void test_KlasGelijkAanNullToevoegen() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->school.addKlas(null),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}


	@Test
	public void test_GebruikerGelijkAanNullToevoegen() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->school.addGebruiker(null),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}



	@Test
	public void test_CursusGelijkAanNullToevoegen() {
		IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->school.addCursus(null),"expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}


	@Test
	void test_generiek_BestaandeGebruikerToevoegen() {
		Gebruiker gebruiker1 = new Gebruiker("jan@hu.nl", "sssadfsdfsdf", "jan");
		school.addGebruiker(gebruiker1);
		Gebruiker gebruiker2 = new Gebruiker("jan@hu.nl", "sssadfsdfsdf", "jan");
		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
				() -> school.addGebruiker(gebruiker2),
				"Zou een foutmelding moeten geven"
		);

		assertTrue(thrown.getMessage().contains("bestaat al"));
	}


	@Test
	void test_dezelfdeSubklasse_BestaandeGebruikerToevoegen() {
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
		Student student = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, null);
		school.addGebruiker(student);
		Docent docent = new Docent("jan@hu.nl", "345efgdgfdgf", "jan", 1234);

		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
				() ->  school.addGebruiker(docent),
				"Gebruiker (" + docent + ") bestaat al"
		);

		assertTrue(thrown.getMessage().contains("bestaat al"));
	}


	@Test
	public void test_BestaandeKlasToevoegen() {
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
		School.resetSchool();
		assertDoesNotThrow(()->serializeDemoData(),"Serialize zou geen foutmelding moeten geven");
		School school1=School.getSchool();
		School.resetSchool();
		//Gebruik van serializeDemoData omdat hier alle types al in staan, en dus ook getest worden.
		assertDoesNotThrow(()->deserialize(),"Deserialize zou geen foutmelding moeten geven");
		assertEquals(School.getSchool(),school1,"School objecten zouden gelijk moeten zijn.");
	}

	@Test
	public void test_removeBestaandeKlas() throws NotFoundException {
		School school=School.getSchool();
		Klas klas = new Klas("TICT-SD-V1E");
		school.addKlas(klas);
		school.removeKlas(klas);
		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() -> school.getKlasByName("TICT-SD-V1E"),
				"Klas niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));
	}

	@Test
	public void test_removeNietBestaandeKlas() {
		School school=School.getSchool();
		Klas klas = new Klas("TICT-SD-V1E");
		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() ->  school.removeKlas(klas),
				"Klas niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));

	}


	@Test
	public void test_removeBestaandeCursus() throws NotFoundException {
		School school=School.getSchool();
		Cursus cursus =  new Cursus("TCIF-V1GP-19_2019","SD-GroupProject");
		school.addCursus(cursus);
		school.removeCursus(cursus);


		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() -> school.getKlasByName("TICT-SD-V1E"),
				"Klas niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));
	}

	@Test
	public void test_removeNietBestaandeCursus() {
		School school=School.getSchool();
		Cursus cursus =  new Cursus("TCIF-V1GP-19_2019","SD-GroupProject");
		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() ->  school.removeCursus(cursus),
				"Klas niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));

	}

}