package dev.m3s.programming2.homework2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;

@TestCategories({ @TestCategory("Student") })
public class StudentTests {

	private Object student;

	@BeforeEach
	public void setUp() {
		student = paramlessStudent.create();
	}

	@Test
	@DisplayName("constantValuesUsageTest")
	public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework2/";
		File file = new File(path + "Student.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }


	@Test
	@DisplayName("Parameterized constructor")
	void parameterizedConstructorTests() throws Exception {
		String fName = randomString(6);
		String lName = randomString(8);
		Object student = twoParamStudent.create(lName, fName);
		// Names
		assertEquals(firstName.read(student), fName,
				explain("firstName was not set correctly")
		);
		assertEquals(lastName.read(student), lName,
				explain("lastName was not set correctly")
		);
		// ID
		assertWithin(id.read(student), Range.closed(1, 100), "id",
				explain("id should be set in the constructor")
		);

		// Check if getRandomId is called in constructors
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework2/";
		File file = new File(path + "Student.java");
		
		files.add(file);
		
		Object[] result = runCheck("dev.m3s.checkstyle.programming2.RandomIdCheck", files);

		assertTrue((boolean) result[0], explain(result[1].toString()));

		//Check if constructor chaining is utilized
		files = new ArrayList<File>();
		
		files.add(file);

		result = runCheck("dev.m3s.checkstyle.programming2.ConstructorChainingCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

		// Years
		assertEquals(startYear.read(student), yearNow,
				explain("Default start year is not the current year")
		);
		assertWithout(graduationYear.read(student),
				Range.closed(minYear, yearNow), "graduationYear",
				explain("Graduation year should not be set by default")
		);
		// Birthdate
		assertMatches(studentBirthDate.read(student), NOT_AVAILABLE_PATTERN,
				"Date of birth has incorrect default value"
		);
		// Setting only the first name
		fName = randomString(8);
		student = twoParamStudent.create(null, fName);
		assertEquals(firstName.read(student), fName,
				explain("firstName was not set correctly")
		);
		assertMatches(lastName.read(student), NO_NAME_PATTERN, "Setting lastName to null in constructor shouldn't be possible");

		// Setting only the last name
		lName = randomString(8);
		student = twoParamStudent.create(lName, (Object)null);
		assertEquals(lastName.read(student), lName,
				explain("lastName was not set correctly")
		);
		assertMatches(firstName.read(student), NO_NAME_PATTERN, "Setting firstName to null in constructor shouldn't be possible");
	}

	@Test
	@DisplayName("firstName")
	void firstNameTests() {
		// Default value
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"Default value of 'firstName' is not 'No name'"
		);
		// setFirstName(null) -> firstName
		firstName.callDefaultSetter(student, null);
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"Setting firstName to null shouldn't be possible."
		);
		// setFirstName -> firstName
		String randomName = randomString(24);
		firstName.callDefaultSetter(student, randomName);
		assertEquals(firstName.read(student), randomName,
				explain("firstName was not set correctly")
		);
		// getFirstName
		assertEquals(firstName.callDefaultGetter(student), randomName,
				explain("getFirstName() does not return the correct value")
		);
		// firstName -> getFirstName
		randomName = randomString(8);
		firstName.write(student, randomName);
		assertEquals(firstName.callDefaultGetter(student), randomName,
				explain("getFirstName() does not return the correct value")
		);
	}

	@Test
	@DisplayName("lastName")
	void lastNameTests() {
		// Default value
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"Default value of 'lastName' is not 'No name'"
		);
		// setLastName(null) -> lastName
		lastName.callDefaultSetter(student, null);
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"Setting lastName to null shouldn't be possible."
		);

		// setLastName -> lastName
		String randomName = randomString(24);
		lastName.callDefaultSetter(student, randomName);
		assertEquals(lastName.read(student), randomName,
				explain("lastName was not set correctly")
		);
		// getLastName
		assertEquals(lastName.callDefaultGetter(student), randomName,
				explain("getLastName() does not return the correct value")
		);

		// lastName -> getLastName
		randomName = randomString(8);
		lastName.write(student, randomName);
		assertEquals(lastName.callDefaultGetter(student), randomName,
				explain("getLastName() does not return the correct value")
		);
	}

	@Test
	@DisplayName("id")
	void idTests() {
		// Default value
		assertWithin(id.read(student), Range.closed(1, 100), "id",
				explain("id should be within [1, 100]")
		);
		// setId(]1,100[) -> id
		assertEquals(id.matchesWithin(id.defaultSetter(), student,
				Range.closed(1, 100)
		), true, explain("id should be within [1, 100]"));
		// setId -> getId
		int randomId = rand.nextInt(1, 101);
		id.callDefaultSetter(student, randomId);
		assertEquals(id.callDefaultGetter(student), randomId,
				explain("getId() does not return the correct value")
		);
		// id -> getId
		randomId = rand.nextInt(100) + 1;
		id.write(student, randomId);
		assertEquals(id.callDefaultGetter(student), randomId,
				explain("getId() does not return the correct value")
		);
	}

	@Test
	@DisplayName("startYear")
	void startYearTests() {
		// Default value
		assertEquals(startYear.read(student), yearNow,
				explain(String.format("Default value of startYear is not %d",
						yearNow
				))
		);
		// setStartYear(]minYear, yearNow[) -> startYear
		assertEquals(startYear.matchesWithin(setStartYear, student,
				Range.closed(minYear, yearNow)
		), true);
		// setStartYear -> getStartYear
		int randYear = rand.nextInt(2001, yearNow);
		setStartYear.call(student, randYear);
		assertEquals(getStartYear.call(student), randYear,
				explain("getStartYear() does not return the correct value")
		);

		// startYear -> getStartYear
		randYear = rand.nextInt(2001, yearNow);
		startYear.write(student, randYear);
		assertEquals(getStartYear.call(student), randYear,
				explain("getStartYear() does not return the correct value")
		);
	}

	@Test
	@DisplayName("graduationYear")
	void graduationYearTests() {
		// Default value
		assertWithout(graduationYear.read(student),
				Range.closed(minYear, yearNow), "graduationYear",
				explain("graduationYear should not be within " + String.format(
						"[%d, %d]", minYear, yearNow))
		);
		// Default value with getter
		assertEquals(graduationYear.defaultGetter()
								   .returnsWithin(student,
										   Range.closed(minYear, yearNow)
								   ), false, explain("graduationYear should be within [%d, %d]", minYear, yearNow));
		// graduationYear -> getGraduationYear
		int newStartYear = startYear.write(student,
				rand.nextInt(minYear, 2020)
		);
		int randYear = rand.nextInt(newStartYear, yearNow);
		graduationYear.write(student, randYear);
		assertEquals(graduationYear.callDefaultGetter(student), randYear,
				explain("getGraduationYear() does not return the correct value")
		);
	}

	@Test
	@DisplayName("setDegreeTitle")
	void setDegreeTitleTest() {
		Object degrObject = degree.read(student);
		String newTitle = randomString(24);
		setDegreeTitle.call(student, newTitle);
		assertEquals(degreeTitle.read(degrObject), newTitle,
				explain("setDegreeTitle() does not set the correct value")
		);
		setDegreeTitle.call(student, (Object) null);
		assertEquals(degreeTitle.callDefaultGetter(degrObject), newTitle,
				expectedUnchanged("degreeTitle", newTitle, "after attempting to set degreeTitle to null"));
	}

	@Test
	@DisplayName("addCourse")
	 
	void addCourseTest() {
		Object degrObject = degree.read(student);

		// Degree.myCourses empty?
		assertEquals(count.read(degrObject), 0,
				explain("New Degree object should not have any courses")
		);
		Object[] coursesInDegree = (Object[]) myCourses.read(degrObject);
		assertEquals(coursesInDegree[0], null,
				explain("New Degree object should not have any courses")
		);

		// addCourse(valid) returns true?
		Object newStudentCourse = randomStudentCourse(false);
		assertEquals(addCourse.call(student, newStudentCourse), true,
			 explain("addCourse() with valid course should return true"));
		System.out.println(
				"Comparing the added StudentCourse object with the Degree object's myCourses[0]...");
		assertStudentCoursesEqual(newStudentCourse, coursesInDegree[0]);
		assertEquals(count.read(degrObject), 1,
				explain("Expected the count to be 1")
		);
		// addCourse(null) returns false?
		assertEquals(addCourse.call(student, (Object) null), false, 
			explain("addCourse() with invalid course should return false"));
		assertEquals(count.read(degrObject), 1,
				explain("Expected the count to stay 1")
		);
		System.out.println("Adding 49 more courses.");
		for (int i = 0; i < 49; i++) {
			addCourse.invoke(student, randomStudentCourse(true));
		}
		System.out.println(
				"50 courses added. Adding one more course, expecting addCourse to return false.");
		assertEquals(addCourse.call(student, randomStudentCourse(false)), false,
				explain("calling addCourse() when there are maximum amount of courses should return false")
		);
	}

	@Test
	@DisplayName("addCourses")
	 
	void addCoursesTest() {
		Object degrObject = degree.read(student);
		assertEquals(count.read(degrObject), 0,
				explain("New Degree object should not have any courses")
		);
		int coursesToAdd = rand.nextInt(2, 10);
		int nullsToAdd = rand.nextInt(2, 10);
		int coursesAdded = 0;
		int nullsAdded = 0;
		Object[] courses = new Object[coursesToAdd + nullsToAdd];
		int i = 0;
		while (true) {
			if (coursesAdded < coursesToAdd) {
				if (nullsAdded < nullsToAdd) {
					// 50/50
					if (rand.nextBoolean()) {
						courses[i] = randomStudentCourse(false);
						coursesAdded++;
					} else {
						courses[i] = null;
						nullsAdded++;
					}
				} else {
					// add course
					courses[i] = randomStudentCourse(false);
					coursesAdded++;
				}
			} else {
				if (nullsAdded == nullsToAdd) {
					break;
				}
				// add null
				courses[i] = null;
				nullsAdded++;
			}
			i++;
		}
		int testAddCourses = addCourses.call(student, courses);
		assertEquals(testAddCourses, coursesToAdd,
			explain("addCourses should return the amount of courses added"));

		assertEquals(count.read(degrObject), coursesToAdd,
				explain("Expected Degree.count to go up by " + coursesToAdd)
		);
		int nextTarget = rand.nextInt(47, 50);
		int coursesToAddUntil4X = nextTarget - coursesToAdd;
		System.out.format("Adding courses until there are %d.%n", nextTarget);
		courses = new Object[coursesToAddUntil4X];
		for (i = 0; i < coursesToAddUntil4X; i++) {
			courses[i] = randomStudentCourse(true);
		}
		addCourses.call(student, courses);
		assertEquals(count.read(degrObject), nextTarget,
				explain("Expected Degree.count to go up by "
						+ coursesToAddUntil4X)
		);
		coursesToAdd = rand.nextInt(4, 8);
		System.out.format(
				"Adding %d more courses, expecting %d as return value.%n",
				coursesToAdd, 50 - nextTarget
		);
		System.out.println(nextTarget);
		courses = new Object[coursesToAdd];
		for (i = 0; i < coursesToAdd; i++) {
			courses[i] = randomStudentCourse(true);
		}
		testAddCourses = addCourses.call(student, courses);
		assertEquals(testAddCourses, 50 - nextTarget,
			 explain("Expected addCourses to return the amount of courses it added"));
	}

	@Test
	@DisplayName("setTitleOfThesis")
	 
	void setTitleOfThesisTest() {
		Object degrObject = degree.read(student);
		String newTitle = randomString(24);
		setTitleOfThesis.call(student, newTitle);
		assertEquals(titleOfThesis.read(degrObject), newTitle, explain("Incorrect titleOfThesis value"));
		setTitleOfThesis.call(student, (Object) null);
		assertEquals(titleOfThesis.matches(degrObject, null), false, 
			expectedUnchanged("return value of getTitleOfThesis", newTitle, "after trying to set it to null"));
	}

	@Test
	@DisplayName("printCourses")
	 
	void printCoursesTest() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
								.map(i -> i == 6 ? 'F' : i)
								.map(i -> i == 7 ? 'A' : i)
								.limit(testCases * maxCoursesPerCase)
								.iterator();
		for (int i = 0; i < testCases; i++) {
			student = paramlessStudent.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addCourse.call(student,
						randomStudentCourse(grades.nextInt(), regex, false)
				);
				studentCourseRegexes[j] = regex.toString();
			}
			String output = captureOutput(studentPrintCourses, student);
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output,
						"in the output of printCourses", re.substring(0, 9) 
				);
			}
		}
	}

	@Test
	@DisplayName("printDegree")
	 
	void printDegreeTest() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
								.map(i -> i == 6 ? 'F' : i)
								.map(i -> i == 7 ? 'A' : i)
								.limit(testCases * maxCoursesPerCase)
								.iterator();
		for (int i = 0; i < testCases; i++) {
			student = paramlessStudent.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addCourse.call(student,
						randomStudentCourse(grades.nextInt(), regex, false)
				);
				studentCourseRegexes[j] = regex.toString();
			}
			String degreeTitle = randomString(1).toUpperCase() + randomString(5)
					+ " of " + randomString(1).toUpperCase() + randomString(5);
			setDegreeTitle.call(student, degreeTitle);
			String thesisTitle = greatTitle();
			setTitleOfThesis.call(student, thesisTitle);

			// Read the console output
			System.out.println("Calling printDegree()");
			String output = captureOutput(printDegree, student);
			// Verify degree information
			String degreePattern = "^Degree \\[Title: \"?" + degreeTitle
					+ "\"? \\(courses: " + coursesToAdd + "\\)"
					+ "Thesis title: \"?" + thesisTitle + "\"?";

			findIgnoringWhitespace(degreePattern, output,
					"in the output of printDegree", degreePattern.substring(0, 7)
			);

			// Verify course information
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output,
						"in the output of printDegree", re.substring(0, 9) 
				);
			}
		}
	}

	@Test
	@DisplayName("birthDate")
	void birthDateTests() {
		// Default value
		assertMatches(studentBirthDate.read(student), NOT_AVAILABLE_PATTERN, "birthDate has incorrect default value");
		// setBirthDate(null) -> birthDate
		assertMatches(setBirthDate.call(student, (Object) null),
				NO_CHANGE_PATTERN, "birthDate should remain unchanged when attempting to set it to null"
		);
		assertThat("Setting birthDate to null shouldn't be possible.",
				studentBirthDate.read(student), Matchers.notNullValue()
		);
		// setBirthDate -> birthDate
		String randomId = makePersonID();
		String expectedBD = personIdToBirthDate(randomId);
		// note that in StringMethods.returns() expected value is handled as regex
		// (a stricter check might be needed in the future)
		assertEquals(setBirthDate.call(student, randomId), expectedBD,
			explain("Incorrect return value of setBirthDate")
		);
		assertEquals(studentBirthDate.read(student), expectedBD, 
			explain("Incorrect birthdate")
		);
		// getBirthDate
		assertEquals(studentBirthDate.defaultGetter().call(student), expectedBD,
			explain("Incorrect return value of getBirthDate")

		);

		// invalid id
		String invalidId = makePersonIDWithIncorrectChecksum();
		assertMatches(setBirthDate.call(student, invalidId), NO_CHANGE_PATTERN);
		assertEquals(studentBirthDate.read(student), expectedBD,
				explain("Expected birthDate to stay " + expectedBD)
		);
		// invalid id (random string)
		String randString = randomString(11);
		assertMatches(setBirthDate.call(student, randString),
				NO_CHANGE_PATTERN,
				"birthDate should remain unchanged when attempting to set it to an invalid id"
		);
		assertEquals(studentBirthDate.matches(student, randString), false,
				explain("Setting 'birthDate' to " + randString
						+ " shouldn't be possible.")
		);
	}

	@Test
	@DisplayName("getStudyYears")
	 
	void getStudyYearsTests() {
		// The student has not graduated
		// Default value = current year - start year (current year) = 0
		assertEquals(getStudyYears.returns(student, 0), true,
				explain("getStudyYears should return 0 when start year is same as the current year"));

		// New starting year
		int newStartYear = rand.nextInt(minYear + 1, yearNow);
		startYear.write(student, newStartYear);
		int expectedStudyYears = yearNow - newStartYear;
		assertEquals(getStudyYears.call(student), expectedStudyYears,
				explain("Incorrect getStudyYears() return value"));

		// After graduation
		graduate(student, newStartYear, yearNow);
		assertEquals(getStudyYears.call(student), expectedStudyYears,
				explain("Incorrect getStudyYears() return value after student has graduated"));

		// Test another graduation year
		Object student2 = paramlessStudent.create();
		startYear.write(student2, minYear);
		graduate(student2, newStartYear, newStartYear);
		assertEquals(getStudyYears.call(student2), 0,
				explain("Incorrect getStudyYears() return value after student has graduated"));
	}

	@Test
	@DisplayName("getRandomId")
	void getRandomIdTest() throws Exception {
		Range<Integer> valid = Range.closed(1, 100);
		for (int i = 0; i < 1000; i++) {
			int value = getRandomId.invoke(student);
			assertEquals(valid.includes(value), true,
					explain("Expected random id within " + valid + ", got "
							+ value)
			);
		}
	
		/* 
		Disabled as this test requires the use of two param nextInt method, which requires java version 17 or higher
		// Check if the random method utilizes gets its min and max values from ConstantValues
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework2/";
		File file = new File(path + "Student.java");
		
		files.add(file);
		
		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValuesIdCheck", files);

		assertTrue((boolean) result[0], explain(result[1].toString()));
		*/
	}
}
