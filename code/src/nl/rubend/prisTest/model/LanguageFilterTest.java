package model;

import nl.rubend.pris.model.LanguageFilter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Dictionary;
import nl.rubend.pris.model.LanguageFilter;
import static org.junit.jupiter.api.Assertions.*;

class LanguageFilterTest {

	String zin1 = "Hallo bla bla bla bla bla bla schelden bla bla bla";
	String zin2 = "Hallo bla bla bla bla bla bla bla bla bla bla bla bla";
	String zin3 = "Hallo bla bla bla bla bla bla scheldenbla bla bla";

	@Test
	void test_zinMetEenScheldwoord() throws IOException {

		assertFalse(LanguageFilter.isGeschikt(zin1));

	}

	@Test
	void test_zinZonderScheldwoord() throws IOException {

		assertTrue(LanguageFilter.isGeschikt(zin2));

	}

	@Test
	void test_zinZonderEenEchtScheldwoord() throws IOException {

		assertTrue(LanguageFilter.isGeschikt(zin3));

	}




}