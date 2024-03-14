package dev.m3s.programming2.homework2;

import dev.m3s.maeaettae.jreq.*;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@TestCategories({ @TestCategory("Course") })
public class CourseTests {

	private Object course;

	@BeforeEach
	public void setUp() {
		course = paramlessCourse.create();
	}

	@Test
	@DisplayName("constantValuesUsageTest")
	public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework2/";
		File file = new File(path + "Course.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }

	@Test
	@DisplayName("Constructors")
	void constructorTests() {
		// Constructor 1 tested in setUp()
		course = randomCourse(); // Constructor 2
		assertCoursesEqual(course, copyCourse.create(course)); // Constructor 3
	}

	@Test
	@DisplayName("Constructor parameters")
	void constructorParameterTests() {
		//Initialize course object with all invalid parameters
		Object course1 = sevenParamsCourse.create(null, -100000, 'A', -1, 6, -1.0, true);
		
		//Test name
		/*
		String courseName = name.read(course1);
		assertMatches(courseName, NO_TITLE_PATTERN, 
				"Expected constructor to not allow initializing name with null value");

		//Test courseCode
		String testCourseCode = courseCode.read(course1);
		assertMatches(testCourseCode, NOT_AVAILABLE_PATTERN,
				"Expected constructor to not allow initializing courseCode with invalid value");
		*/

		//Test courseType
		int testCourseType = courseType.read(course1);
		assertEquals(testCourseType, 0,
				 explain("Expected constructor to not allow initializing courseType with invalid value"));

		//Test period
		int testPeriod = period.read(course1);
		assertEquals(testPeriod, 0, 
				explain("Expected constructor to not allow initializing period with invalid value"));

		//Test credits
		double testCredits = credits.read(course1);
		assertEquals(testCredits, 0.0, 
				explain("Expected constructor to not allow initializing credits with invalid value"));
	}

	@Test
	@DisplayName("name")
	void nameTests() {
		// name (default value) 
		//assertMatches(name.read(course), NO_TITLE_PATTERN);
		// setName
		
		String randomName = randomString(10);
		name.callDefaultSetter(course, randomName);
		
		assertSetterUpdatesCurrentValue(course, name, name.defaultSetter(), name.defaultGetter(),
				randomName, "name"
		);
		// getName
		assertGetterReturnsNewValue(course, name, name.defaultGetter(),
				randomName, "name"
		);
		// null check
		name.callDefaultSetter(course, null);
		assertNotNull(name.read(course), explain("Name should not be settable to null"));
		// empty string check
		name.callDefaultSetter(course, "");
		assertEquals(name.read(course), randomName, explain("Name should not be settable to empty value.")
			.expected(randomName).but("was " + name.get(course)));
		//assertNotEquals(name.read(course), "");
	}

	@Test
	@DisplayName("courseCode")
	void courseCodeTests() {
		// courseCode (default value)
		//assertMatches(courseCode.read(course), NOT_AVAILABLE_PATTERN);

		int randCode = rand.nextInt(1000000);
		char randBase = randBase();
		String validCourseCode = "" + randCode + randBase;

		// setCourseCode
		setCourseCode.call(course, randCode, randBase);
		assertEquals(courseCode.read(course), validCourseCode, explain("Expected setCourseCode to set courseCode value"));

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

		// getCourseCode
		randCode = rand.nextInt(1000000);
		randBase = randBase();
		validCourseCode = "" + randCode + randBase;
		StringMethods getter = courseCode.defaultGetter();
		assertGetterReturnsNewValue(course, courseCode, getter,
				validCourseCode, "courseCode"
		);

		// Boundary test case 1 (valid)
		// setCourseCode(1, )
		randBase = randBase();
		validCourseCode = "" + 1 + randBase;
		setCourseCode.call(course, 1, randBase);
		assertEquals(getter.call(course), validCourseCode, 
				explain("Incorrect return value of getCourseCode"));

		// Boundary test case 2 (invalid)
		// setCourseCode(0, )
		randBase = randBase();
		setCourseCode.call(course, 0, randBase);
		assertEquals(getter.call(course), validCourseCode, 
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));
		// Boundary test case 3 (valid)
		// setCourseCode(999999, )
		randBase = randBase();
		validCourseCode = "" + 999999 + randBase;
		setCourseCode.call(course, 999999, randBase);
		assertEquals(getter.call(course), validCourseCode, 
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// Boundary test case 4 (invalid)
		// setCourseCode(1000000, )
		setCourseCode.call(course, 1000000, randBase());
		assertEquals(getter.call(course), validCourseCode,
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// negative courseCode
		// setCourseCode(negative, ) -> getCourseCode
		setCourseCode.call(course, -rand.nextInt(1000000), randBase());
		assertEquals(getter.call(course), validCourseCode,
				expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));

		// invalid courseBase
		// setCourseCode(, invalid)
		setCourseCode.call(course, rand.nextInt(1000000),
				(char) rand.nextInt('B', 'P')
		);
		assertEquals(courseCode.read(course), validCourseCode, expectedUnchanged("courseCode", validCourseCode,"after trying to set courseCode to invalid value " + courseCode.read(course) + "."));
		assertEquals(getter.call(course), validCourseCode, expectedUnchanged("the return value of getCourseCode", validCourseCode, "after trying to set courseCode to invalid value " + courseCode.read(course) + "."));
	}

	@Test
	@DisplayName("courseBase")
	void courseBaseTests() {
		// courseBase (default value)
		//System.out.println(
		//		"Checking whether private Character courseBase is ' ' (space)");
		//Character value = (Character) courseBase.get(course);
		//assertEquals(courseBase.read(course), ' ',
		//		explain("The attribute courseBase should be ' ' (space) by default"));
		
		int randCode = rand.nextInt(1000000);
		char correctBase = randBase();
		Callable getter = courseBase.defaultGetter();

		// setCourseCode -> courseBase & getCourseBase
		setCourseCode.call(course, randCode, correctBase);
		assertEquals(courseBase.read(course), correctBase, explain("Incorrect courseBase value"));
		assertEquals(getter.call(course), correctBase, explain("Incorrect courseBase value"));

		// Lower case bases
		for (int i = 0; i < 3; i++) {
			randCode = rand.nextInt(1000000);
			correctBase = "aps".charAt(i);
			setCourseCode.call(course, randCode, correctBase);
			correctBase = Character.toUpperCase(correctBase);
			assertEquals(getter.call(course), correctBase,
					explain("courseBase should be converted to uppercase."));
		}

		// setCourseCode(invalid, )
		int invalid = rand.nextInt(1000000, 10000000);
		// Different base
		char nextBase;
		do {
			nextBase = randBase();
		} while (nextBase == correctBase);
		setCourseCode.call(course, invalid, nextBase);
		assertEquals(getter.call(course), correctBase,
				expectedUnchanged("the return value of getCourseBase", correctBase,
				"after trying to set courseCode to invalid value " + getter.invoke(course) + ".")
		);
		// setCourseCode(, invalid)
		int valid = rand.nextInt(1000000);
		setCourseCode.call(course, valid, (char) rand.nextInt('B', 'P'));
		assertEquals(courseBase.read(course), correctBase,
				expectedUnchanged("courseBase", correctBase,
						"after trying to set courseCode to invalid value " + getter.invoke(course) + "."));
		assertEquals(getter.call(course), correctBase,
				expectedUnchanged("the return value of getCourseBase", correctBase,
				"after trying to set courseCode to invalid value " + getter.invoke(course) + "."));
		
	}

	@Test
	@DisplayName("courseType")
	void courseTypeTests() {
		Callable<?> setter = courseType.defaultSetter();
		IntMethods getter = courseType.defaultGetter();

		// "Optional"
		setter.call(course, 0);
		assertEquals(courseType.read(course), 0, explain("Incorrect courseType value"));
		assertEquals(getter.call(course), 0, explain("Incorrect return value of getCourseType"));
		assertTrue(getCourseTypeString.returns(course, "(?i)Optional"), 
				explain("Invalid courseTypeString for courseType %d",  courseType.read(course))
				.expected("String matching the pattern: (?i)Optional").but("was: " + getCourseTypeString.invoke(course)));

		// "Mandatory"
		setter.call(course, 1);
		assertEquals(courseType.read(course), 1, explain("Incorrect courseType value"));
		assertEquals(getter.call(course), 1, explain("Incorrect return value of getCourseType"));
		assertTrue(getCourseTypeString.returns(course, "(?i)Mandatory"), 
				explain("Invalid courseTypeString for courseType %d", courseType.read(course))
				.expected("String matching the pattern: (?i)Mandatory").but("was: " + getCourseTypeString.invoke(course)));

		// Negative
		int invalid = -rand.nextInt(1,100);
		setter.call(course, invalid);
		// Expect unchanged values
		assertEquals(getter.call(course), 1, 
				expectedUnchanged("the return value of getCourseType", 1,
				"after trying to set courseCode to invalid value " + getter.invoke(course) + "."));

		assertEquals(courseType.read(course), 1, explain("Incorrect courseType value"));
		assertTrue(getCourseTypeString.returns(course, "(?i)Mandatory"), explain("Invalid courseTypeString for courseType %d",  courseType.read(course))
				.expected("String matching the pattern: (?i)Mandatory").but("was: " + getCourseTypeString.invoke(course)));

		// Set to 0
		setter.call(course, 0);
		// Invalid positive
		invalid = rand.nextInt(2, 100);
		setter.call(course, invalid);
		// Expect 0
		assertEquals(getter.call(course), 0, 
				expectedUnchanged("return value of getCourseType", 0,
				"after trying to set courseType to invalid value " + getter.invoke(course) + "."));

		assertEquals(courseType.read(course), 0, expectedUnchanged("courseType", 0,
				"after trying to set courseType to invalid value " + getter.invoke(course) + "."));
		assertTrue(getCourseTypeString.returns(course, "(?i)Optional"), explain("Invalid courseTypeString for courseType %d", courseType.read(course))
			.expected("String matching the pattern: (?i)Optional").but("was: " + getCourseTypeString.invoke(course)));
	}

	@Test
	@DisplayName("period")
	void periodTests() {
		// period (default value)
		assertEquals(period.read(course), 0, explain("Expected period default value to be 0"));

		IntMethods getter = period.defaultGetter();
		Callable<?> setter = period.defaultSetter();

		// setPeriod(boundaries)
		assertTrue(period.matchesWithin(setter, course, Range.closed(1, 5)),
				explain("Invalid period value").expected("value within [1,5]").but("was: " + period.read(course)));
		// setPeriod(valid)
		int valid = rand.nextInt(1, 6);
		assertSetterUpdatesCurrentValue(course, period, setter, getter, valid, "period");

		// getPeriod
		valid = rand.nextInt(1, 6);
		assertGetterReturnsNewValue(course, period, getter, valid, "period");

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
	@DisplayName("credits")
	void creditsTests() {
		// credits (default value)
		assertEquals(credits.read(course), 0.0,
				explain("Invalid credits default value"));

		// setCredits(boundaries)
		assertTrue(credits.matchesWithin(setCredits, course,
				Range.closed(0.0, 55.0)),
				explain("Incorrect credits value").expected("value within [1,55.0]").but("was: " + credits.read(course)));

		// setCredits(valid)
		double valid = rand.nextInt(0, 56);
		assertSetterUpdatesCurrentValue(course, credits, setCredits, credits.defaultGetter(), valid, "credits");

		// getCredits
		DoubleMethods getter = credits.defaultGetter();
		valid = rand.nextInt(0, 56);
		assertGetterReturnsNewValue(course, credits, getter, valid, "credits");

		// setCredits(negative)
		double invalid = -rand.nextInt(100);
		setCredits.call(course, invalid);
		assertEquals(getter.call(course), valid,
				expectedUnchanged("the return value of getCredits", valid,
						"after trying to set credits to invalid value " + getter.invoke(course) + "."));
		// setCredits(random positive > 55.0)
		invalid = rand.nextInt(56, 100);
		setCredits.call(course, invalid);
		assertEquals(getter.call(course), valid,
				expectedUnchanged("the return value of getCredits", valid,
						"after trying to set credits to invalid value " + getter.invoke(course) + "."));
	}

	@Test
	@DisplayName("numericGrade")
	void numericGradeTests() {
		Callable setter = numericGrade.defaultSetter();
		BooleanMethods getter = numericGrade.defaultGetter();
		// numericGrade exists (default value doesn't matter)
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
	@DisplayName("toString")
	void toStringTests() {
		for (int i = 0; i < 3; i++) {
			StringBuilder expectedString = new StringBuilder();
			course = randomCourse(rand.nextBoolean(), expectedString, false);
			String returned = courseToString.call(course);
			findIgnoringWhitespace(expectedString.toString(), returned,
					"in the return value of toString", expectedString.toString().substring(0, 7)
			);
		}
	}

	static char randBase() {
		return "APS".charAt(rand.nextInt(3));
	}

}
