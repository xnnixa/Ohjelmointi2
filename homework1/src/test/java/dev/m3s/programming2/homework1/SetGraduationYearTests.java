package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.m3s.maeaettae.jreq.Range;

/**
 * Tests for the method setGraduationYear.
 * The test cases are as follows:
 * - Default return value (all conditions unmet)
 * - All preconditions have been set correctly
 * - The other title has not been set
 * - The other credits have not been set
 * - Invalid graduation years
 * - Valid graduation years
 */
@TestCategories({ @TestCategory("setGraduationYear") })
class SetGraduationYearTests {

	Object student1;
	Object student2;
	int sYear;
	int gYear;

	@BeforeEach
	public void setUp() {
		student1 = parameterless.instantiate();
		student2 = parameterless.instantiate();
		sYear = rand.nextInt(minYear, yearNow + 1);
		gYear = rand.nextInt(sYear, yearNow + 1);
	}

	@Test
	@Points(category = "setGraduationYear", failDeduct = 1)
	@DisplayName("setGraduationYear: all conditions unmet")
	void conditionsUnmetTest() {
		// Test the default return value
		assertMatches(
				setGraduationYear.call(student1, gYear), CHECK_REQUIRED_STUDIES_PATTERN,
				"A new student should not be ready to graduate."
		);
		verifyYearNotSet();
	}

	@Test
	@Points(category = "setGraduationYear", failDeduct = 1)
	@DisplayName("graduationYear setter: all conditions met")
	void conditionsMetTest() {
		setEnoughCredits(student1, bachelorCredits);
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);
		setStartYear.call(student1, sYear);

		assertMatches(
				setGraduationYear.call(student1, gYear), OK_PATTERN,
				"Unable to set graduation year despite all required "
						+ "preconditions being met."
		);

		assertEquals(graduationYear.callDefaultGetter(student1), gYear,
				explain(yearNotSetMessage(gYear))
		);
	}

	@Test
	@Points(category = "setGraduationYear", failDeduct = 1)
	@DisplayName("setGraduationYear: thesis title missing")
	void missingThesisTitleTests() {
		setEnoughCredits(student1, bachelorCredits);
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfMasterThesis);
		setStartYear.call(student1, sYear);

		assertMatches(
				setGraduationYear.call(student1, gYear), CHECK_REQUIRED_STUDIES_PATTERN,
				"Incorrect return value (the title of bachelor's thesis "
						+ "was not set)"
		);
		verifyYearNotSet();

		setEnoughCredits(student2, bachelorCredits);
		setEnoughCredits(student2, masterCredits);
		setTitle(student2, titleOfBachelorThesis);
		setStartYear.call(student2, sYear);

		assertMatches(
				setGraduationYear.call(student2, gYear), CHECK_REQUIRED_STUDIES_PATTERN,
				"Incorrect return value (the title of master's thesis "
						+ "was not set)"
		);
		verifyYearNotSet();
	}

	@Test
	@Points(category = "setGraduationYear", failDeduct = 1)
	@DisplayName("setGraduationYear: insufficient credits")
	void missingCreditsTests() {
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);
		setStartYear.call(student1, sYear);

		assertMatches(
				setGraduationYear.call(student1, gYear), CHECK_REQUIRED_STUDIES_PATTERN,
				"Incorrect return value (the bachelor's credits "
						+ "were not set)"
		);

		verifyYearNotSet();

		setEnoughCredits(student2, bachelorCredits);
		setTitle(student2, titleOfBachelorThesis);
		setTitle(student2, titleOfMasterThesis);
		setStartYear.call(student2, sYear);

		assertMatches(
				setGraduationYear.call(student2, gYear), CHECK_REQUIRED_STUDIES_PATTERN,
				"Incorrect return value (the master's credits "
						+ "were not set)"
		);
		verifyYearNotSet();
	}

	@Test
	@Points(category = "setGraduationYear", failDeduct = 1)
	@DisplayName("setGraduationYear: valid year")
	void validYearTests() {
		setEnoughCredits(student1, bachelorCredits);
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);
		setStartYear.call(student1, sYear);

		assertMatches(
				setGraduationYear.call(student1, sYear), OK_PATTERN,
				String.format("Unable to set graduation year to the start year (%d)%n"
						+ "despite all required preconditions being met.", sYear)
		);

		assertEquals(graduationYear.callDefaultGetter(student1), sYear,
				explain(yearNotSetMessage(sYear))
		);

		setEnoughCredits(student2, bachelorCredits);
		setEnoughCredits(student2, masterCredits);
		setTitle(student2, titleOfBachelorThesis);
		setTitle(student2, titleOfMasterThesis);
		setStartYear.call(student1, yearNow);

		assertMatches(
				setGraduationYear.call(student2, yearNow), OK_PATTERN,
				String.format("Unable to set graduation year to the current year (%d)%n"
						+ "despite all required preconditions being met.", yearNow)
		);

		assertEquals(graduationYear.callDefaultGetter(student2), yearNow,
				explain(yearNotSetMessage(yearNow))
		);
	}

	@Test
	@Points(category = "setGraduationYear", failDeduct = 1)
	@DisplayName("setGraduationYear: invalid year")
	void invalidYearTests() {
		setEnoughCredits(student1, bachelorCredits);
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);
		setStartYear.call(student1, minYear);

		int invalidYear = minYear - 1;
		assertMatches(
				setGraduationYear.call(student1, invalidYear), CHECK_GRADUATION_YEAR_PATTERN,
				"The given year (" + invalidYear + ") is invalid, "
						+ "but the method did not return \"" + CHECK_GRADUATION_YEAR + "\"."
		);

		verifyYearNotSet();

		invalidYear = yearNow + 1;
		assertMatches(
				setGraduationYear.call(student1, invalidYear), CHECK_GRADUATION_YEAR_PATTERN,
				"The given year (" + invalidYear + ") is invalid, "
						+ "but the method did not return \"" + CHECK_GRADUATION_YEAR + "\"."
		);

		verifyYearNotSet();
	}

	private String yearNotSetMessage(int year) {
		return String.format(
				"The return value of getGraduationYear should be the%n"
						+ "same year which was set with setGraduationYear "
						+ "(%s).",
				year
		);
	}

	private void verifyYearNotSet() {
		assertWithout(graduationYear.callDefaultGetter(student1),
				Range.closed(minYear, yearNow), "return value",
				explain("The return value of getGraduationYear should not be a "
						+ "valid year.")
		);
	}
}
