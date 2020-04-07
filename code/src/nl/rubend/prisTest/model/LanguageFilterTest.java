package model;

import nl.rubend.pris.model.LanguageFilter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class LanguageFilterTest {

	@Test
	void test_zinMetEenScheldwoord() throws IOException {
		assertFalse(LanguageFilter.isGeschikt("Hallo bla bla bla bla bla bla schelden bla bla bla"));
	}

	@Test
	void test_zinZonderScheldwoord() throws IOException {
		assertTrue(LanguageFilter.isGeschikt("Hallo bla bla bla bla bla bla bla bla bla bla bla bla"));
	}

	@Test
	void test_zinZonderEenEchtScheldwoord() throws IOException {
		assertTrue(LanguageFilter.isGeschikt("Hallo bla bla bla bla bla bla scheldenbla bla bla"));
	}
}