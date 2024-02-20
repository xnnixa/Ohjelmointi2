package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/**
 * These tests look for the required constructors (declared in H1)
 * and test that their names and parameters match the requirements.
 */
@TestCategories({ @TestCategory("Constructors") })
class ConstructorTests {

	@Test
	@Points(failDeduct = 1, category = "Constructors")
	@DisplayName("Student: parameterized")
	void parameterizedConstructorTests() {
		String fName = randomString(6);
		String lName = randomString(8);
		Object student = parameterized.create(lName, fName);
		// Names
		assertEquals(firstName.read(student), fName,
				explain("firstName was not set correctly"));
		assertEquals(lastName.read(student), lName,
				explain("lastName was not set correctly"));
		// ID
		assertWithin(id.read(student), Range.closed(1, 100),
				"id", explain("id should be set in the constructor to a value within [1,100]")
		);
		// Credits
		assertEquals(bachelorCredits.read(student), 0.0,
				explain("Incorrect number of bachelor's credits")
		);
		assertEquals(masterCredits.read(student), 0.0,
				explain("Incorrect number of master's credits")
		);
		// Titles
		assertMatches(titleOfBachelorThesis.read(student), NO_TITLE_PATTERN,
				"Incorrect default title of bachelor's thesis"
		);
		assertMatches(titleOfMasterThesis.read(student), NO_TITLE_PATTERN,
				"Incorrect default title of master's thesis"
		);
		// Years
		assertEquals(startYear.read(student), yearNow,
				explain("Default start year is not the current year")
		);
		assertWithout(graduationYear.read(student),
				Range.closed(minYear, yearNow), "graduationYear",
				explain("Graduation year should not be set by default")
		);

		// Setting only the first name
		fName = randomString(8);
		student = parameterized.create(null, fName);
		assertEquals(firstName.read(student), fName,
				explain("firstName was not set correctly")
		);

		// Setting only the last name
		lName = randomString(8);
		student = parameterized.create(lName, null);
		assertEquals(lastName.read(student), lName,
				explain("lastName was not set correctly")
		);
	}

	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	@Test
	@Points(failDeduct = 1, category = "Constructors")
	@DisplayName("Student: parameterized v2")
	void defaultBirthdateTest() {
		String fName = randomString(6);
		String lName = randomString(8);
		Object student = parameterized.create(lName, fName);
		assertMatches(birthDate.read(student), NOT_AVAILABLE_PATTERN,
				"Date of birth has invalid default value"
		);
	}

	@Test
	@Points(category = "Constructors", failDeduct = 1)
	@DisplayName("getRandomId")
	void getRandomIdUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework1/";
		File file = new File(path + "Student.java");
		
		files.add(file);
		
		Object[] result = runCheck("dev.m3s.checkstyle.programming2.RandomIdCheck", files);

		assertTrue((boolean) result[0], explain(result[1].toString()));
	}
}
