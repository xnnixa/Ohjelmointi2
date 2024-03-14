package dev.m3s.programming2.homework2;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

@TestCategories({ @TestCategory("Student") })
public class GraduationTests {


	private final String canGraduateString = "canGraduate()";
	private final String setGraduationYearString = "setGraduationYear()";
	// For canGraduate:
	private final String newStudentCanGraduate
			= "%s should not allow new student to immediately graduate.";
	private final String insufficientBachelorCredits
			= "%s should not allow student with insufficient amount of bachelor's credits to graduate";
	private final String insufficientMasterCredits
			= "%s should not allow student with insufficient amount of master's credits to graduate";
	private final String insufficientBachelorAndMasterCredits
			= "%s should not allow student with insufficient amount of bachelor's and master's credits to graduate";
	private final String bachelorThesisTitleNotSet
			= "%s should not allow student without a bachelor's thesis title to graduate.";
	private final String masterThesisTitleNotSet
			= "%s should not allow student without a master's thesis title to graduate.";
	private final String canGraduateConditionsMet =
			"%s should allow student to graduate after student has been given "
					+ "valid credits and thesis titles.";
	/*
	private final String setGraduationYearFalsePositive2A =
			"The return value of 'setGraduationYear' is invalid.%nExpected \"%s\""
					+ " (or a string matching the regular expression \"%s\")"
					+ "%nbecause setting the year with 'setGraduationYear' wasn't successful.%n";
	private final String setGraduationYearFalseNegative2A =
			"The return value of 'setGraduationYear' is invalid.%nExpected \"%s\""
					+ " (or a string matching the regular expression \"%s\")"
					+ " because setting the year with 'setGraduationYear' was successful.%n";
	private final String getGraduationYearError1A =
			"The return value of 'getGraduationYear' should be the same year"
					+ " which was set with 'setGraduationYear' (%s) because all of the required preconditions were met.";
	*/
		// For setGraduationYear:
	private final String theGivenYearIsInvalid1A
			= "%s should not allow to set a graduation year with an invalid year: '%s'.";

	private final String getGraduationYearError1A =
			"The return value of 'getGraduationYear' should be the same year"
					+ " which was set with 'setGraduationYear' (%s) because all of the required preconditions were met.";
	private final String setGraduationYearAllConditionsMet1A
			= "setGraduationYear() should allow student to graduate after a valid graduation year (%s) and other required preconditions were set.";

	// For hasGraduated:
	private final String newStudentHasGraduatedError
			= " A new student should not be already graduated.";
	private final String hasGraduatedConditionsMet1A
			= " Student should have graduated, as a valid graduation year (%s) and other required preconditions were set.";

	// Exception messages expecting arguments (1A = one argument)

	//private final String reason = " Reason: ";
	private Object student;
	private int currentStartYear;
	private int newGraduationYear;

	@BeforeEach
	public void setUp() {
		student = paramlessStudent.create();
		currentStartYear = rand.nextInt(minYear, yearNow + 1);
		newGraduationYear = rand.nextInt(currentStartYear, yearNow + 1);
	}

	@Test
	@DisplayName("canGraduate: all conditions unmet")
	void canGraduateConditionsUnmetTest() {
		// Test the default return value
		assertEquals(canGraduate.call(student), false,
				explain(String.format(newStudentCanGraduate, canGraduateString))
		);
	}

	@Test
	@DisplayName(("canGraduate: all conditions met"))
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void canGraduateConditionsMetTest() {
		setAllConditions(student, currentStartYear);
		assertEquals(canGraduate.call(student), true,
				explain(String.format(canGraduateConditionsMet, canGraduateString))
		);
	}

	@Test
	@DisplayName(("canGraduate: all conditions met v2"))
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void canGraduateConditionsMetTest_V2() {
		setAllConditionsV2(student, currentStartYear);
		assertEquals(canGraduate.call(student), true,
				explain(String.format(canGraduateConditionsMet, canGraduateString)
		));
	}

	@Test
	@DisplayName("canGraduate: no credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void canGraduateWithoutCreditsTest() {
		setStartYear.call(student, currentStartYear);
		setBachelorsThesis(student);

		assertEquals(canGraduate.call(student), false,
				explain(String.format(insufficientBachelorCredits, canGraduateString))
		);
	}

	@Test
	@DisplayName("canGraduate: no bachelor's credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void canGraduateWithoutBachelorsCreditsTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 1, 120);
		setThesis(student, 0);
		setThesis(student, 1);
		assertEquals(canGraduate.call(student), false,
				explain(String.format(insufficientBachelorCredits, canGraduateString))
		);
	}

	@Test
	@DisplayName("canGraduate: no master's credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void canGraduateWithoutMastersCreditsTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 0, 180);
		setThesis(student, 1);
		setThesis(student, 0);
		assertEquals(canGraduate.call(student), false,
				explain(String.format(insufficientMasterCredits, canGraduateString)
		));
	}

	@Test
	@DisplayName("canGraduate: no thesis title")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void canGraduateWithoutThesisTitleTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 180);

		assertEquals(canGraduate.call(student), false,
				explain(bachelorThesisTitleNotSet, canGraduateString)
		);
	}

	@Test
	@DisplayName("canGraduate: no bachelor's thesis title")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void canGraduateWithoutBachelorsThesisTitleTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);
		setThesis(student, 1);
		assertEquals(canGraduate.call(student), false,
				explain(String.format(bachelorThesisTitleNotSet, canGraduateString))
		);
	}

	@Test
	@DisplayName("canGraduate: no master's thesis title")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void canGraduateWithoutMastersThesisTitleTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);
		setThesis(student, 0);

		assertEquals(canGraduate.call(student), false,
				explain(String.format(masterThesisTitleNotSet, canGraduateString))
		);
	}

	@Test
	@DisplayName("canGraduate: almost enough credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void canGraduateConditionsAlmostMetTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 179);
		setBachelorsThesis(student);

		assertEquals(canGraduate.call(student), false,
				explain(String.format(insufficientBachelorAndMasterCredits, canGraduateString))
		);
	}

	@Test
	@DisplayName("canGraduate: almost enough credits v2")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void canGraduateConditionsAlmostMetTest_V2() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 0, 179);
		setCredits(student, 1, 119);
		setThesis(student, 0);
		setThesis(student, 1);

		assertEquals(canGraduate.call(student), false,
				explain(String.format(insufficientBachelorAndMasterCredits, canGraduateString))
		);
	}

	// ****************************** */

	private void verifyYearNotSet() {
		String getGraduationYearError
				= "The return value of 'getGraduationYear' should not be a valid year.";

		assertEquals(isValidGraduationYear(
						graduationYear.callDefaultGetter(student)), false,
				explain(getGraduationYearError)
		);
	}

	@Test
	@DisplayName("setGraduationYear: invalid year")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void setGraduationYearInvalidYearTests() {
		setAllConditions(student, currentStartYear);

		assertMatches(setGraduationYear.call(student, minYear - 1),
				CHECK_GRADUATION_YEAR_PATTERN,
				String.format(theGivenYearIsInvalid1A, setGraduationYearString, minYear-1)
		);

		verifyYearNotSet();

		assertMatches(setGraduationYear.call(student, yearNow + 1),
				CHECK_GRADUATION_YEAR_PATTERN,
				String.format(theGivenYearIsInvalid1A, setGraduationYearString, yearNow+1)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: invalid year v2")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearInvalidYearTests_V2() {
		setAllConditionsV2(student, currentStartYear);

		assertMatches(setGraduationYear.call(student, minYear - 1),
				CHECK_GRADUATION_YEAR_PATTERN,
				String.format(theGivenYearIsInvalid1A, setGraduationYearString, minYear-1)
						
		);

		verifyYearNotSet();

		assertMatches(setGraduationYear.call(student, yearNow + 1),
				CHECK_GRADUATION_YEAR_PATTERN,
				String.format(String.format(theGivenYearIsInvalid1A, setGraduationYearString, yearNow+1))
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: all conditions unmet")
	void setGraduationYearConditionsUnmetTest() {
		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(newStudentCanGraduate, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: all conditions met")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void setGraduationYearConditionsMetTest() {
		setAllConditions(student, currentStartYear);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				OK_PATTERN,
				String.format(canGraduateConditionsMet, setGraduationYearString)
		);

		assertEquals(graduationYear.callDefaultGetter(student),
				newGraduationYear,
				explain(String.format(getGraduationYearError1A,
						newGraduationYear
				))
		);
	}

	@Test
	@DisplayName("setGraduationYear: all conditions met v2")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearConditionsMetTest_V2() {
		setAllConditionsV2(student, currentStartYear);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				OK_PATTERN,
				String.format(canGraduateConditionsMet, setGraduationYearString)
		);

		assertEquals(newGraduationYear,
				graduationYear.callDefaultGetter(student),
				explain(String.format(getGraduationYearError1A,
						newGraduationYear
				))
		);
		assertEquals(graduationYear.callDefaultGetter(student),
				newGraduationYear,
				explain(String.format(getGraduationYearError1A,
						newGraduationYear
				))
		);
	}

	@Test
	@DisplayName("setGraduationYear: year bounds")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void setGraduationYearStartYearTest() {
		setAllConditions(student, currentStartYear);

		assertMatches(setGraduationYear.call(student, currentStartYear),
				OK_PATTERN,
				String.format(setGraduationYearAllConditionsMet1A, currentStartYear)
		);
		assertEquals(graduationYear.callDefaultGetter(student),
				currentStartYear,
				explain(String.format(getGraduationYearError1A,
						currentStartYear
				))
		);

		assertMatches(setGraduationYear.call(student, yearNow), OK_PATTERN,
				String.format(setGraduationYearAllConditionsMet1A, currentStartYear)
		);

		assertEquals(graduationYear.callDefaultGetter(student), yearNow,
				explain(String.format(getGraduationYearError1A, yearNow))
		);
	}

	@Test
	@DisplayName("setGraduationYear: year bounds v2")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearStartYearTest_V2() {
		setAllConditionsV2(student, currentStartYear);


		assertMatches(setGraduationYear.call(student, currentStartYear),
				OK_PATTERN,
				String.format(setGraduationYearAllConditionsMet1A, currentStartYear)
		);

		assertEquals(graduationYear.callDefaultGetter(student),
				currentStartYear,
				explain(String.format(getGraduationYearError1A,
						currentStartYear
				))
		);

		assertMatches(setGraduationYear.call(student, yearNow), OK_PATTERN,
				String.format(setGraduationYearAllConditionsMet1A, currentStartYear)
		);
		assertEquals(graduationYear.callDefaultGetter(student), yearNow,
				explain(String.format(getGraduationYearError1A, yearNow))
		);
	}

	@Test
	@DisplayName("setGraduationYear: no credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void setGraduationYearWithoutCreditsTest() {
		setStartYear.call(student, currentStartYear);
		setBachelorsThesis(student);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(insufficientBachelorCredits, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: no bachelor's credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearWithoutBachelorsCreditsTest() {
		setStartYear.call(student, currentStartYear);
		setThesis(student, 0);
		setThesis(student, 1);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(insufficientBachelorCredits, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: no master's credits")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearWithoutMastersCreditsTest() {
		setStartYear.call(student, currentStartYear);
		setThesis(student, 0);
		setThesis(student, 1);
		setCredits(student, 0, 180);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(insufficientMasterCredits, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: no thesis title")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void setGraduationYearWithoutThesisTitleTest() {
		setStartYear.call(student, currentStartYear);
		setCredits(student, 180);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(bachelorThesisTitleNotSet, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: no bachelor's thesis title")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearWithoutBachelorsThesisTitleTest() {
		setStartYear.call(student, currentStartYear);
		setThesis(student, 1);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(bachelorThesisTitleNotSet, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("setGraduationYear: no masters's thesis title")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void setGraduationYearWithoutMastersThesisTitleTest() {
		setStartYear.call(student, currentStartYear);
		setThesis(student, 0);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);

		assertMatches(setGraduationYear.call(student, newGraduationYear),
				CHECK_REQUIRED_CREDITS_PATTERN,
				String.format(masterThesisTitleNotSet, setGraduationYearString)
		);

		verifyYearNotSet();
	}

	@Test
	@DisplayName("hasGraduated")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V1)
	void hasGraduatedTests() {
		// Test the default return value

		assertEquals(hasGraduated.call(student), false,
				explain(newStudentHasGraduatedError)
		);
		setAllConditions(student, currentStartYear);

		setGraduationYear.call(student, newGraduationYear);
		assertEquals(graduationYear.read(student), newGraduationYear,
				explain("The graduation year was not set correctly")
		);

		assertEquals(hasGraduated.call(student), true,
				explain(String.format(hasGraduatedConditionsMet1A,
						newGraduationYear)
				)
		);
	}

	@Test
	@DisplayName("hasGraduated v2")
	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	void hasGraduatedTests_V2() {
		// Test the default return value

		assertEquals(hasGraduated.call(student), false,
				explain(newStudentHasGraduatedError)
		);
		setAllConditionsV2(student, currentStartYear);

		setGraduationYear.call(student, newGraduationYear);
		assertEquals(graduationYear.read(student), newGraduationYear,
				explain("The graduation year was not set correctly")
		);
		assertEquals(hasGraduated.call(student), true,
				explain(String.format(hasGraduatedConditionsMet1A,
						newGraduationYear)
				)
		);
	}
}
