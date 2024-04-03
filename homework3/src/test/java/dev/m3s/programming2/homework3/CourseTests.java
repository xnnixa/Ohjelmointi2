package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static dev.m3s.programming2.homework3.H3.*;
import dev.m3s.maeaettae.jreq.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories({ @TestCategory("Course") })
class CourseTests {

	private Object course;

	@BeforeEach
	void setUp() {
		course = paramlessCourse.instantiate();
	}

	@Test
	@DisplayName("constantValuesUsageTest")
	public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework3/";
		File file = new File(path + "Course.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }

	@Test
	@DisplayName("All attributes should be non-static")
	void requireNonStaticAttributes() {
		classCourse.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("All methods should be non-static")
	void requireNonStaticMethods() {
		classCourse.hasMethod().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test constructors")
	void constructorTests() {
		paramlessCourse.instantiate(); // Constructor 1
		course = randomCourse(); // Constructor 2
		assertCoursesEqual(course, copyCourse.instantiate(course)); // Constructor 3
	}

	@Test
	@DisplayName("Constructor parameters")
	void constructorParameterTests() {
		//Create initializing course object with invalid parameters
		Object course1 = sevenParamsCourse.create(null, -100000, 'A', -1, 6, -1.0, true);

		//Test name
		String courseName = name.callDefaultGetter(course1);
		assertMatches(courseName, NO_TITLE_PATTERN, 
				"Setting name to null in constructor shouldn't be possible");

		//Test courseCode
		String testCourseCode = courseCode.callDefaultGetter(course1);
		assertMatches(testCourseCode, NOT_AVAILABLE_PATTERN,
				"Setting courseCode to invalid value in constructor shouldn't be possible");

		//Test courseType
		int testCourseType = courseType.callDefaultGetter(course1);
		assertEquals(testCourseType, 0,
				 explain("Setting courseType to invalid value in constructor shouldn't be possible"));

		//Test period
		int testPeriod = period.callDefaultGetter(course1);
		assertEquals(testPeriod, 0, 
				explain("Setting period to invalid value in constructor shouldn't be possible"));

		//Test credits
		double testCredits = credits.callDefaultGetter(course1);
		assertEquals(testCredits, 0.0, 
				explain("Setting credits to invalid value in constructor shouldn't be possible"));
	}

	@Test
	@DisplayName("Test name/getName/setName")
	void nameTests() {
		Callable<?> setter = name.defaultSetter();
		StringMethods getter = name.defaultGetter();
		// Default value
		assertMatches(name.read(course), NO_TITLE_PATTERN, "Invalid name default value");

		String randomName = randomString(10);

		// setName -> name
		assertSetterUpdatesCurrentValue(course, name, setter, getter, randomName, "name");

		// name -> getName
		assertGetterReturnsCurrentValue(course, name, getter, "name");

		// setName(null) -> name
		name.callDefaultSetter(course, null);
		assertNotNull(name.read(course), explain("Name should not be settable to null"));

		// setName("") -> getName
		name.callDefaultSetter(course, "");
		assertEquals(name.read(course), randomName, 
				expectedUnchanged("name", randomName, "after trying to set courseCode to invalid empty value"));

	}

	@Test
	@DisplayName("Test courseCode")
	void courseCodeTests() {
		// Default value
		assertMatches(courseCode.read(course), NOT_AVAILABLE_PATTERN, "Invalid courseCode default value");
		int randCode = rand.nextInt(100000, 1000000);
		char randBase = randBase();
		StringMethods getter = courseCode.defaultGetter();
		String validCourseCode = "" + randCode + randBase;

		// setCourseCode -> courseCode
		setCourseCode.call(course, randCode, randBase);
		assertEquals(courseCode.read(course), validCourseCode,
				explain("Invalid courseCode value"));

		// setCourseCode(1, valid) -> getCourseCode
		randBase = randBase();
		validCourseCode = "" + 1 + randBase;
		setCourseCode.call(course, 1, randBase);
		assertEquals(getter.call(course), validCourseCode,
				explain("Invalid return value of getCourseCode"));

		randCode = rand.nextInt(100000, 1000000);
		randBase = randBase();
		validCourseCode = "" + randCode + randBase;

		// Lower case bases
		for (int i = 0; i < 3; i++) {
			randCode = rand.nextInt(1000000);
			randBase = "aps".charAt(i);
			validCourseCode = "" + randCode + randBase;
			setCourseCode.call(course, randCode, randBase);
			//Convert lower case base to uppercase
			validCourseCode = "" + randCode + Character.toUpperCase(randBase);
			assertEquals(courseCode.read(course), validCourseCode,
					explain("Course base should always be converted to uppercase "));
		}


		// courseCode -> getCourseCode
		courseCode.write(course, validCourseCode);
		assertEquals(getter.call(course),
				courseCode.read(course),
				explain("Invalid return value of getCourseCode"));

		// setCourseCode(0, valid) -> getCourseCode
		randBase = randBase();
		setCourseCode.call(course, 0, randBase);
		assertEquals(getter.call(course), validCourseCode,
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// setCourseCode(negative len 6 + sign, valid) -> courseCode
		setCourseCode.call(course, -rand.nextInt(100000, 1000000), randBase());
		assertTrue(courseCode.matches(course, validCourseCode),
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// setCourseCode(negative len 5 + sign, valid) -> getCourseCode
		setCourseCode.call(course, -rand.nextInt(10000, 100000), randBase());
		assertEquals(getter.call(course), validCourseCode,
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// setCourseCode(1000000, valid) -> getCourseCode
		setCourseCode.call(course, 1000000, randBase());
		assertEquals(getter.call(course), validCourseCode,
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// setCourseCode(valid, invalid)
		setCourseCode.call(course, rand.nextInt(100000, 1000000),
				(char) rand.nextInt('B', 'P'));
		assertEquals(getter.call(course), validCourseCode,
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));
		assertEquals(courseCode.read(course), validCourseCode,
				expectedUnchanged("courseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));
	}

	@Test
	@DisplayName("Test courseBase")
	@SuppressWarnings("unchecked")
	void courseBaseTests() {
		// Default value
		//"Checking whether private Character courseBase is ' ' (space)");
		assertEquals(courseBase.read(course), ' ',
				explain("The attribute courseBase should be ' ' (space) by default"));

		int randCode = rand.nextInt(100000, 1000000);
		char correctBase = randBase();
		Callable<Character> getter = (Callable<Character>) courseBase.defaultGetter();

		// setCourseCode -> courseBase & getCourseBase
		setCourseCode.call(course, randCode, correctBase);
		assertEquals(courseBase.read(course), correctBase,
				explain("Invalid courseBase value"));
		assertEquals(getter.call(course), correctBase, explain("Invalid return value of getCourseBase"));

		// setCourseCode(invalid, valid) -> getCourseBase
		char nextBase = randBase();
		while (nextBase == correctBase) {
			nextBase = randBase();
		}
		setCourseCode.call(course, rand.nextInt(1000000, 10000000), nextBase);
		assertEquals(getter.call(course), correctBase,
				expectedUnchanged("courseBase", correctBase,
				 "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// Lower case bases
		for (int i = 0; i < 3; i++) {
			randCode = rand.nextInt(1000000);
			correctBase = "aps".charAt(i);
			setCourseCode.call(course, randCode, correctBase);
			correctBase = Character.toUpperCase(correctBase);
			assertEquals(getter.call(course), correctBase,
					explain("courseBase should be converted to uppercase."));
		}

		// setCourseCode(valid, invalid) -> getCourseBase
		setCourseCode.call(course, rand.nextInt(100000, 1000000),
				(char) rand.nextInt('B', 'P'));
		assertTrue(getter.returns(course, correctBase), explain(
				"Expected courseBase to stay " + correctBase
						+ " after trying to call setCourseCode with invalid arguments\n"
						+ explain(seeConsoleOutput)));

		assertTrue(courseBase.matches(course, correctBase),
				explain(seeConsoleOutput));
	}

	@Test
	@DisplayName("Test courseType and getCourseTypeString")
	void courseTypeTests() {
		Callable<?> setter = courseType.defaultSetter();
		IntMethods getter = courseType.defaultGetter();

		// Optional
		setter.call(course, 0);
		assertEquals(courseType.read(course), 0,
				explain("Incorrect courseType value"));
		assertEquals(getter.call(course), 0,
				explain("Incorrect getCourseType return value"));
		assertTrue(getCourseTypeString.returns(course, "(?i)Optional"),
				explain("Incorrect courseTypeString for courseType %d", courseType.read(course))
				.expected("String matching the pattern: (?i)Optional").but("was: " + getCourseTypeString.invoke(course)));

		// Mandatory
		setter.call(course, 1);
		assertEquals(courseType.read(course), 1,
				explain("Incorrect courseType value"));
		assertEquals(getter.call(course), 1,
				explain("Incorrect getCourseType return value"));
		assertTrue(getCourseTypeString.returns(course, "(?i)Mandatory"), 
				explain("Incorrect courseTypeString for courseType %d", courseType.read(course))
				.expected("String matching the pattern: (?i)Mandatory").but("was: " + getCourseTypeString.invoke(course)));

		// -1
		setter.call(course, -1);
		assertEquals(getter.call(course), 1, 
				expectedUnchanged("the return value of getCourseType", 1,
				"after trying to set courseCode to invalid value " + getter.invoke(course) + "."));
		// Expect unchanged values
		assertEquals(courseType.read(course), 1, explain("Incorrect courseType value"));
		assertTrue(getCourseTypeString.returns(course, "(?i)Mandatory"), explain("Invalid courseTypeString for courseType %d",  courseType.read(course))
				.expected("String matching the pattern: (?i)Mandatory").but("was: " + getCourseTypeString.invoke(course)));

		// 2
		setter.call(course, 0);
		setter.call(course, 2);
		assertFalse(getter.returns(course, 2),
				explain(seeConsoleOutput));
		// Expect 0
		assertEquals(courseType.read(course), 0, expectedUnchanged("courseType", 0,
				"after trying to set courseType to invalid value " + getter.invoke(course) + "."));
		assertTrue(getCourseTypeString.returns(course, "(?i)Optional"), explain("Invalid courseTypeString for courseType %d", courseType.read(course))
			.expected("String matching the pattern: (?i)Optional").but("was: " + getCourseTypeString.invoke(course)));
	}

	@Test
	@DisplayName("Test period")
	void periodTests() {
		// Default value
		assertEquals(period.read(course), 0, explain("Expected period default value to be 0"));

		IntMethods getter = period.defaultGetter();
		Callable<?> setter = period.defaultSetter();

		// setPeriod([1, 5]) -> period
		assertTrue(period.matchesWithin(setter, course, Range.closed(1, 5)),
				explain("Invalid period value").expected("value within [1,5]").but("was: " + period.read(course)));

		// period -> getPeriod
		int valid = rand.nextInt(1, 6);
		assertSetterUpdatesCurrentValue(course, period, setter, getter, valid, "period");

		// setPeriod(0) -> getPeriod
		setter.call(course, 0);
		assertEquals(period.read(course), valid,
				expectedUnchanged("period", valid, "after trying to set period to 0"));

		// setPeriod(negative)
		int invalid = -rand.nextInt(100);
		setter.call(course, invalid);
		assertEquals(getter.call(course), valid,
				expectedUnchanged("the return value of getPeriod", valid,
				"after trying to set period to invalid value " + getter.invoke(course) + "."));

		// setPeriod(random positive > 6)
		invalid = rand.nextInt(7, 100);
		setter.call(course, invalid);
		assertTrue(getter.returns(course, valid),
				expectedUnchanged("the return value of getPeriod", valid,
				"after trying to set period to invalid value " + getter.invoke(course) + "."));
	}

	@Test
	@DisplayName("Test credits")
	void creditsTests() {
		// Default value
		assertEquals(credits.read(course), 0.0,
				explain("Invalid credits default value"));

		DoubleMethods getter = credits.defaultGetter();

		// setCredits([0.0, 55.0]) -> credits
		assertTrue(credits.matchesWithin(setCourseCredits, course,
				Range.closed(0.0, 55.0)),
				explain("Incorrect credits value").expected("value within [1,55.0]").but("was: " + credits.read(course)));

		// setCredits(valid)
		double valid = rand.nextInt(0, 56);
		assertSetterUpdatesCurrentValue(course, credits, setCourseCredits, credits.defaultGetter(), valid, "credits");

		// getCredits
		valid = rand.nextInt(0, 56);
		assertGetterReturnsNewValue(course, credits, getter, valid, "credits");

		// setCredits(negative)
		double invalid = -rand.nextInt(100);
		setCourseCredits.call(course, invalid);
		assertEquals(getter.call(course), valid,
				expectedUnchanged("the return value of getCredits", valid,
						"after trying to set credits to invalid value " + getter.invoke(course) + "."));

	// setCredits(random positive > 55.0)
	invalid = rand.nextInt(56, 100);
	setCourseCredits.call(course, invalid);
	assertEquals(getter.call(course), valid,
			expectedUnchanged("the return value of getCredits", valid,
					"after trying to set credits to invalid value " + getter.invoke(course) + "."));

	}

	@Test
	@DisplayName("Test numericGrade")
	void numericGradeTests() {
		Callable<?> setter = numericGrade.defaultSetter();
		BooleanMethods getter = numericGrade.defaultGetter();
		// setNumericGrade -> numericGrade
		boolean current = numericGrade.read(course);
		// isNumericGrade (default value)
		assertGetterReturnsCurrentValue(course, numericGrade, getter, "numericGrade");
		// setNumericGrade(!current)
		assertSetterUpdatesCurrentValue(course, numericGrade, setter, getter, !current, "numericGrade");
		// setNumericGrade(current)
		assertSetterUpdatesCurrentValue(course, numericGrade, setter, getter, current, "numericGrade");
		// isNumericGrade (new value)
		assertGetterReturnsNewValue(course, numericGrade, getter, !current, "numericGrade");
	}

	@Test
	@DisplayName("Test toString")
	void toStringTests() {
		for (int i = 0; i < 3; i++) {
			StringBuilder expectedString = new StringBuilder();
			course = randomCourse(rand.nextBoolean(), expectedString);
			String returned = courseToString.call(course);
			findIgnoringWhitespace(expectedString.toString(), returned, "in the return value of toString", expectedString.substring(0, 7));
		}
	}

	static char randBase() {
		return "APS".charAt(rand.nextInt(3));
	}
}
