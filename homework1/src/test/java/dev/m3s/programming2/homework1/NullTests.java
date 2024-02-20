package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@TestCategories({ @TestCategory("NullChecks") })
class NullTests {

	Object student;

	@BeforeEach
	public void setUp() {
		student = parameterless.instantiate();
	}

	@Test
	@Points(category = "NullChecks", failDeduct = 1)
	@DisplayName("firstName: null")
	void firstNameNullTests() {
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"firstName should not be null.");

		firstName.callDefaultSetter(student, null);
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"firstName should not be null.");
	}

	@Test
	@Points(category = "NullChecks", failDeduct = 1)
	@DisplayName("lastName: null")
	void lastNameNullTests() {
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"lastName should not be null.");

		lastName.callDefaultSetter(student, null);
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"Setting lastName to null shouldn't be possible.");
	}

	@Test
	@Points(category = "NullChecks", failDeduct = 1)
	@DisplayName("titleOfBachelorThesis: null")
	void titleOfBachelorThesisNullTests() {
		assertMatches(titleOfBachelorThesis.read(student), NO_TITLE_PATTERN,
				"titleOfBachelorThesis should not be null.");

		titleOfBachelorThesis.callDefaultSetter(student, null);
		assertMatches(titleOfBachelorThesis.read(student), NO_TITLE_PATTERN,
				"Setting titleOfBachelorThesis to null shouldn't be possible.");
	}

	@Test
	@Points(category = "NullChecks", failDeduct = 1)
	@DisplayName("titleOfMasterThesis: null")
	void titleOfMasterThesisNullTest() {

		assertMatches(titleOfMasterThesis.read(student), NO_TITLE_PATTERN,
					"titleOfMasterThesis should not be null.");

		titleOfMasterThesis.callDefaultSetter(student, null);
		assertMatches(titleOfMasterThesis.read(student), NO_TITLE_PATTERN,
		"Setting titleOfMasterThesis to null shouldn't be "
				+ "possible.");
		}

	@Test
	@Points(category = "NullChecks", failDeduct = 1)
	@DisplayName("Constructor: nulls")
	void parameterizedConstructorNullTests() {
		// Test both first and last name with default values
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"firstName should not be null."); 
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"lastName should not be null");
		
		// Test setting both first and last name to null
		Object student = parameterized.create(null, null);
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"The constructor should not set 'firstName' to null.");
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"The constructor should not set 'lastName' to null.");

		// Test setting only the first name to null
		String name = randomString(8);
		student = parameterized.create(null, name);
		assertEquals(firstName.read(student), name,
				explain("The constructor should not set 'firstName' to null."));
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"The constructor should not set 'lastName' to null.");

		// Test setting only the last name to null
		name = randomString(8);
		student = parameterized.create(name, null);
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"The constructor should not set 'firstName' to null.");
		assertEquals(lastName.read(student), name,
				explain("The constructor should not set 'lastName' to null."));
	}

	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	@Test
	@Points(category = "NullChecks", failDeduct = 1)
	@DisplayName("setPersonId: null")
	void setPersonIdNullTest() {
		// Test that setPersonId returns "Invalid birthday!" if the birthday is
		// null, and that the birthDate is not changed.
		String bd = birthDate.read(student);
		assertMatches(setPersonID.call(student, null), INVALID_BIRTHDAY_PATTERN,
				"setPersonId should return \"Invalid birthday!\" if the "
				+ "birthday is null."
		);
		assertEquals(bd, birthDate.read(student),
				explain("The birthDate should not be changed if the argument "
						+ "to setPersonId is null.")
		);
	}
}
