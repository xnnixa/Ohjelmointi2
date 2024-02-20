package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the default setters of the following fields:
 * firstName, lastName, titleOfBachelorThesis, titleOfMasterThesis,
 * startYear, id, bachelorCredits, masterCredits
 */
@TestCategories(@TestCategory("Setters"))
class SetterTests {

	Object student;

	@BeforeEach
	public void setUp() {
		student = parameterless.instantiate();
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setFirstName")
	void setFirstNameTest() {
		String fName = randomString(10);
		firstName.callDefaultSetter(student, fName);
		assertEquals(firstName.read(student), fName,
				explain("Setting firstName to \"" + fName + "\" failed.")
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setLastName")
	void setLastNameTest() {
		String lName = randomString(10);
		lastName.callDefaultSetter(student, lName);
		assertEquals(lastName.read(student), lName,
				explain("Setting lastName to \"" + lName + "\" failed.")
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setId")
	void setIdTest() {
		// Setting a value in invalid range should result in either
		// the value not getting set, or an exception.
		assertEquals(id.matchesWithin(id.defaultSetter(), student,
				Range.closed(1, 100)), true,
				explain().expected("Success in setting id to a value in range 1-100")
						.but("id was " + id.get(student))
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setBachelorCredits")
	void setBachelorCreditsTest() {
		assertEquals(bachelorCredits.matchesWithin(bachelorCredits.defaultSetter(),
				student, Range.closed(0.0, 300.0)), true,
				explain().expected("Success in setting bachelorCredits to a value in range 0.0-300.0")
						.but("bachelorCredits was " + bachelorCredits.get(student))
		);
		// Set back to 0.0 after the initial value (0.0) has been changed:
		assertEquals(bachelorCredits.matchesAfter(bachelorCredits.defaultSetter(),
				student, 0.0), true,
				explain().expected("Success in setting bachelorCredits back to 0.0")
						.but("bachelorCredits was " + bachelorCredits.get(student))
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setMasterCredits")
	void setMasterCreditsTest() {
		assertEquals(masterCredits.matchesWithin(masterCredits.defaultSetter(),
				student, Range.closed(0.0, 300.0)), true,
				explain().expected("Success in setting masterCredits to a value in range 0.0-300.0")
						.but("masterCredits was " + masterCredits.get(student))
		);
		// Set back to 0.0 after the initial value (0.0) has been changed:
		assertEquals(masterCredits.matchesAfter(masterCredits.defaultSetter(),
				student, .0), true,
				explain().expected("Success in setting masterCredits back to 0.0")
						.but("masterCredits was " + masterCredits.get(student))
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setTitleOfBachelorThesis")
	void setTitleOfBachelorThesisTest() {
		String title = greatTitle();
		titleOfBachelorThesis.callDefaultSetter(student, title);
		assertEquals(titleOfBachelorThesis.read(student), title,
				explain("Setting titleOfBachelorThesis to \"" + title + "\" failed.")
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setTitleOfMasterThesis")
	void setTitleOfMasterThesisTest() {
		String title = greatTitle();
		titleOfMasterThesis.callDefaultSetter(student, title);
		assertEquals(titleOfMasterThesis.read(student), title,
				explain("Setting titleOfMasterThesis to \"" + title + "\" failed.")
		);
	}

	@Test
	@Points(category = "Setters", failDeduct = 1)
	@DisplayName("setStartYear")
	void setStartYearTest() {
		assertEquals(startYear.matchesWithin(setStartYear,
				student, Range.closed(minYear, yearNow)), true,
				explain().expected(
						"Success in setting startYear to a value in range " + minYear + "-" + yearNow)
						.but("startYear was " + startYear.get(student))
		);
	}
}
