package model;

import nl.rubend.pris.Main;
import nl.rubend.pris.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

	private School school;
	private Docent slber;

	@BeforeEach
	public void initialize() {
		School.resetSchool();
		school = School.getSchool();
		slber = new Docent("test123@abdsoifj.nl", "asodfijasdf", "osjdfasdf", 212123);
		school.addGebruiker(slber);
	}


	@Test
	public void test_KlasGelijkAanNullToevoegen() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> school.addKlas(null), "expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}


	@Test
	public void test_GebruikerGelijkAanNullToevoegen() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> school.addGebruiker(null), "expected exception");
		assertEquals("Gebruiker is geen Gebruiker?", e.getMessage());
	}


	@Test
	public void test_CursusGelijkAanNullToevoegen() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> school.addCursus(null), "expected exception");
		assertEquals("Ongeldige waarde", e.getMessage());
	}


	@Test
	void test_generiek_BestaandeGebruikerToevoegen() {
		Gebruiker gebruiker1 = new Gebruiker("jan@hu.nl", "sssadfsdfsdf", "jan");
		school.addGebruiker(gebruiker1);
		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
				() -> new Gebruiker("jan@hu.nl", "sssadfsdfsdf", "jan"),
				"Zou een foutmelding moeten geven"
		);

		assertTrue(thrown.getMessage().contains("bestaat al"));
	}


	@Test
	void test_dezelfdeSubklasse_BestaandeGebruikerToevoegen() {
		Student student1 = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, slber);
		school.addGebruiker(student1);

		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
				() -> new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, slber),
				"fout bij controle op dubbele account bestaat al"
		);

		assertTrue(thrown.getMessage().contains("bestaat al"));
	}

	@Test
	void test_slberNietInSchool() {
		Docent slber2 = new Docent("123@456.789", "1233456434235", "SlbTest", 6432);
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 1234, slber2), "Geen foutmelding bij selecteren van SLBer die niet in school zit");
		assertEquals(thrown.getMessage(), "SLBer niet in School");
	}

	@Test
	void test_verschillendeSubklasse_BestaandeGebruikerEmailToevoegen() {
		Student student = new Student("jan@hu.nl", "sssadfsdfsdf", "jaasdfaisdfn", 123412, slber);
		school.addGebruiker(student);

		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
				() -> new Docent("jan@hu.nl", "345efgdgfdgf", "jan", 1234),
				"Gebruiker zou al gevonden moeten zijn."
		);

		assertTrue(thrown.getMessage().contains("bestaat al"));
	}

	@Test
	void test_verschillendeSubklasse_BestaandeGebruikerNaamToevoegen() {
		Student student = new Student("jan@hu.nl", "sssadfsdfsdf", "jan", 123412, slber);
		school.addGebruiker(student);
		Docent docent = new Docent("jan2@hu.nl", "345efgdgfdgf", "jan", 1234);

		assertDoesNotThrow(() -> school.addGebruiker(docent), "ander email adres met zelfde naam zou geen probleem moeten zijn.");
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
		Cursus cursus1 = new Cursus("TCIF-V1GP-19_2019", "SD-GroupProject");
		school.addCursus(cursus1);
		Cursus cursus2 = new Cursus("TCIF-V1GP-19_2019", "SD-GroupProject");

		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
				() -> school.addCursus(cursus2),
				"Cursus (" + cursus2 + ") bestaat al"
		);

		assertTrue(thrown.getMessage().contains("bestaat al"));

	}


	@Test
	public void test_GebruikerGetByEmail() throws NotFoundException {
		School school = School.getSchool();
		Systeembeheerder systeembeheerder = new Systeembeheerder("jan@hu.nl", "sssadfsdfsdf", "Jan de Man");
		school.addGebruiker(systeembeheerder);
		assertEquals(school.getGebruikerByEmail("jan@hu.nl"), systeembeheerder);
	}


	@Test
	public void test_GebruikerGetByEmailNotFound() {
		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() -> school.getGebruikerByEmail("test@email.com"),
				"Gebruiker niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));
	}

	@Test
	public void test_KlasGetByName() throws NotFoundException {
		School school = School.getSchool();
		Klas klas = new Klas("TICT-SD-V1E");
		school.addKlas(klas);
		assertEquals(school.getKlasByName("TICT-SD-V1E"), klas);
	}


	@Test
	public void test_KlasGetByNameNotFound() {
		School school = School.getSchool();
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
		School school = School.getSchool();
		Cursus cursus = new Cursus("TCIF-V1GP-19_2019", "SD-GroupProject");
		school.addCursus(cursus);
		assertEquals(school.getCursusByCode("TCIF-V1GP-19_2019"), cursus);
	}


	@Test
	public void test_CursusGetByCodeNotFound() {
		School school = School.getSchool();
		Cursus cursus = new Cursus("TCIF-V1GP-19_2019", "SD-GroupProject");
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
		assertDoesNotThrow(Main::initData, "Serialize zou geen foutmelding moeten geven");
		School school1 = School.getSchool();
		School.resetSchool();
		//Gebruik van serializeDemoData omdat hier alle types al in staan, en dus ook getest worden.
		assertDoesNotThrow(School::deserialize, "Deserialize zou geen foutmelding moeten geven");
		assertEquals(School.getSchool(), school1, "School objecten zouden gelijk moeten zijn.");
	}

	@Test
	public void test_removeBestaandeKlas() throws NotFoundException {
		School school = School.getSchool();
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
		School school = School.getSchool();
		Klas klas = new Klas("TICT-SD-V1E");
		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() -> school.removeKlas(klas),
				"Klas niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));

	}


	@Test
	public void test_removeBestaandeCursus() throws NotFoundException {
		School school = School.getSchool();
		Cursus cursus = new Cursus("TCIF-V1GP-19_2019", "SD-GroupProject");
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
		School school = School.getSchool();
		Cursus cursus = new Cursus("TCIF-V1GP-19_2019", "SD-GroupProject");
		NotFoundException thrown = assertThrows(
				NotFoundException.class,
				() -> school.removeCursus(cursus),
				"Klas niet gevonden"
		);

		assertTrue(thrown.getMessage().contains("niet gevonden"));

	}

}