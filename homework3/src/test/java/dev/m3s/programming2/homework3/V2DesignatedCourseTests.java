package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import dev.m3s.maeaettae.jreq.Callable;
import dev.m3s.maeaettae.jreq.IntMethods;
import dev.m3s.maeaettae.jreq.Quantifier;
import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V2)
@TestCategories({ @TestCategory("DesignatedCourse") })
class V2DesignatedCourseTests {

	private Object designatedCourse;

	@BeforeEach
	void setUp() {
		classDesignatedCourse.require();
		designatedCourse = paramlessDesignatedCourse.instantiate();
	}

	@Test
	@DisplayName("Required attributes")
	void requiredAttributes_V2() {
		courseDesignatedCourse.require();
		responsible.require();
		year.require();
	}

	@Test
	@DisplayName("All attributes should be non-static")
	void requireNonStaticAttributes_V2() {

		classDesignatedCourse.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("All methods should be non-static")
	void requireNonStaticMethods_V2() {
		classDesignatedCourse.hasMethod().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test parameterless constructor")
	void parameterlessConstructorTest_V2() {
		paramlessDesignatedCourse.instantiate();
	}

	@Test
	@DisplayName("Test parameterized constructor")
	void parameterizedConstructorTest_V2() {
		Object course1 = sevenParamsCourse.instantiate("More basic studies", 223344, 'A', MANDATORY, 1, 50.50, true);
		System.out.println(String.format("Calling new DesignatedCourse( %s, %b, %d )", course1, true, 2010));

 		designatedCourse = allParamDesignatedCourse.instantiate(course1, true, 2010);

		// Check constructor sets course
		System.out.println("Check if course is set in constructor");
		assertNotNull(getCourseDesignatedCourse.call(designatedCourse), "Expected course to not be null");
		assertCoursesEqual(course1, getCourseDesignatedCourse.call(designatedCourse));
		
		
		System.out.println("Check if responsible is set in constructor");
		//Check constructor sets responsible
		assertTrue(isResponsible.call(designatedCourse), explain("Incorrect return value of isResponsible"));

		System.out.println("Check if year is set in constructor");
		
		//Check constructor sets year
		assertEquals(getYearDesignatedCourse.call(designatedCourse), 2010, "Year should be set in constructor");

	}

	@Test
	@DisplayName("Test course/getCourse/setCourse")
	void courseTests_V2() {
		Object newCourse = randomCourse();
		// setCourse -> getCourse & course
		setCourseDesignatedCourse.call(designatedCourse, newCourse);
		assertCoursesEqual(newCourse, getCourseDesignatedCourse.call(designatedCourse));
		assertCoursesEqual(newCourse, courseDesignatedCourse.read(designatedCourse));
	}

	@Test
	@DisplayName("Test getYear, setYear")
	void yearTests_V2() {
		Callable<?> setter = year.defaultSetter();
		IntMethods getter = year.defaultGetter();

		// setStartYear -> getStartYear
		// Check setStartYear sets the startYear attribute
		int randYear = rand.nextInt(2000, yearNow) + 1;
		assertGetterReturnsSetValue(designatedCourse, setter, getter, randYear, "year");

		// Check the startYear is within the specified range 2000 < startYear <
		// currentYear
		System.out.println("Checking the start year default value is the current year");
		assertTrue(year.matchesWithin(setYearDesignatedCourse, designatedCourse,
				Range.closed(2000, yearNow + 1)), explain("Invalid year value")
						.expected(String.format("a value between 2000 and %d", yearNow+1)).but("was: " + year.read(designatedCourse)));

		// startYear -> getStartYear
		assertGetterReturnsNewValue(designatedCourse, year, getter, rand.nextInt(2000, yearNow + 1), "year");
	}

	@Test
	@DisplayName("Test toString")
	void toStringTests_V2() {

		// Test case 1, example output from the task description
		// [course=[223344A (50.50 cr), "More basic studies". Mandatory, period: 1.],
		// year=2023]

		Object course1 = sevenParamsCourse.instantiate("More basic studies", 223344, 'A', MANDATORY, 1, 50.50, false);

		designatedCourse = allParamDesignatedCourse.instantiate(course1, false, 2023);
		String desCourseCodeStr = getCourseCode.invoke(getCourseDesignatedCourse.invoke(designatedCourse));
		String desCourseCredits = String.format(Locale.US, "%.2f",
				getCourseCredits.invoke(getCourseDesignatedCourse.invoke(designatedCourse)));
		String desCourseName = getCourseName.invoke(getCourseDesignatedCourse.invoke(designatedCourse));
		String desCourseTypeStr = (getCourseType.invoke(getCourseDesignatedCourse.invoke(designatedCourse)) == 0)
				? "Optional"
				: "Mandatory";
		int desCoursePeriod = getPeriod.invoke(getCourseDesignatedCourse.invoke(designatedCourse));
		int desCourseYear = getYearDesignatedCourse.invoke(designatedCourse);

		String output1 = toStringDesignatedCourse.call(designatedCourse);
		// TODO: How to make the test pass with both ',' and '.' as the decimal
		// separator.
		System.out.println("Checking the output of the toString method is in the specified format for sample output 1");
		System.out.println(output1);
		String designatedCoursePatternStr1 = "^\\[course=\\["
				+ desCourseCodeStr + ""
				+ "\\(" + desCourseCredits + "cr\\)"
				+ ","
				+ "\"" + desCourseName + "\"" + "."
				+ desCourseTypeStr + ","
				+ "period" + ":" + desCoursePeriod
				+ "." + "\\]" + ","
				+ "year" + "=" + desCourseYear + "\\]";
		
		findIgnoringWhitespace(designatedCoursePatternStr1, output1, "in the return value of toString", designatedCoursePatternStr1.substring(0, 16));
		// StringBuilder expectedString;

		// Check the output of the toString method on randomly generated designated
		// course object
		for (int i = 0; i < 2; i++) {
			Object course = randomCourse();
			designatedCourse = allParamDesignatedCourse.instantiate(course, false, rand.nextInt(2000, yearNow + 1));
			desCourseCodeStr = getCourseCode.invoke(getCourseDesignatedCourse.invoke(designatedCourse));
			desCourseCredits = String.format(Locale.US, "%.2f",
					getCourseCredits.invoke(getCourseDesignatedCourse.invoke(designatedCourse)));
			desCourseName = getCourseName.invoke(getCourseDesignatedCourse.invoke(designatedCourse));
			desCourseTypeStr = (getCourseType.invoke(getCourseDesignatedCourse.invoke(designatedCourse)) == 0) ? "Optional"
					: "Mandatory";
			desCoursePeriod = getPeriod.invoke(getCourseDesignatedCourse.invoke(designatedCourse));
			desCourseYear = getYearDesignatedCourse.invoke(designatedCourse);

			String output = toStringDesignatedCourse.call(designatedCourse);
			// TODO: How to make the test pass with both ',' and '.' as the decimal
			// separator.
			System.out.println("Checking the output of the toString method is in the specified format for sample output 1");
			System.out.println(output);
			String designatedCoursePatternStr = "^\\[course=\\["
					+ desCourseCodeStr + ""
					+ "\\(" + desCourseCredits + "cr\\)"
					+ ","
					+ "\"" + desCourseName + "\"" + "."
					+ desCourseTypeStr + ","
					+ "period" + ":" + desCoursePeriod
					+ "." + "\\]" + ","
					+ "year" + "=" + desCourseYear + "\\]";
			findIgnoringWhitespace(designatedCoursePatternStr, output, "in the return value of toString", designatedCoursePatternStr.substring(0, 16));
		}
	}
}
