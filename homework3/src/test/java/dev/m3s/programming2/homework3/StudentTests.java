package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import dev.m3s.maeaettae.jreq.Quantifier;
import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@TestCategories({ @TestCategory("Student") })
class StudentTests {

	private Object student;
	private final String graduationYearError = "New Student object should not have a valid graduation year.";

	@BeforeEach
	void setUp() {
		student = twoParamStudent.instantiate(null, (Object) null);
	}

	@Test
	@DisplayName("Student inherits the class person")
	void inheritence() {
		classStudent.declaresSupertype(classPerson).require();
	}

	@Test
	@DisplayName("All attributes should be private")
	void requirePrivateAttributes() {
		classStudent.hasField().isPrivate().require(Quantifier.FOR_ALL);
	}

	@Test
	@DisplayName("All attributes should be non-static")
	void requireNonStaticAttributes() {
		classStudent.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("All methods should be non-static")
	void requireNonStaticMethods() {
		classStudent.hasMethod().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test degrees initialization")
	void degreesInitializationTest() {
		assertNotNull(degrees.read(student), explain("Expected 'degrees' to be initialized"));
	}

	@Test
	@DisplayName("Test Student constructor with parameters")
	void parameterizedConstructorSetsNamesTest() throws Exception {
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

		String path = "src/main/java/dev/m3s/programming2/homework3/";
		File file = new File(path + "Student.java");
		
		files.add(file);
		
		Object[] result = runCheck("dev.m3s.checkstyle.programming2.RandomIdCheck", files);

		assertTrue((boolean) result[0], explain(result[1].toString()));


		// Years
		assertEquals(startYear.read(student), yearNow,
				explain("Default start year is not the current year")
		);

		// TODO: Is there default value for graduation!
		assertWithout(graduationYear.read(student),
				Range.closed(minYear, yearNow), "graduationYear",
				explain("Graduation year should not be set by default")
		);

		assertMatches(personBirthDate.read(student), NOT_AVAILABLE_PATTERN,
				"Date of birth has invalid default value"
		);
		student = twoParamStudent.create(null, (Object) null);

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
	@DisplayName("Test firstName")
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
	@DisplayName("Test lastName")
	void lastNameTests() {
		// Default value

		assertMatches(firstName.read(student), NO_NAME_PATTERN,
				"Default value of 'firstName' is not 'No name'"
		);

		// setLastName(null) -> lastName
		lastName.callDefaultSetter(student, null);
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
				"Setting firstName to null shouldn't be possible."
		);
		// setLastName -> lastName
		String randomName = randomString(24);
		assertTrue(lastName.matchesAfter(lastName.defaultSetter(), student,
				randomName), explain(seeConsoleOutput));
		// getLastName
		assertTrue(lastName.defaultGetter().returns(student, randomName), explain(seeConsoleOutput));
		// lastName -> getLastName
		assertTrue(lastName.defaultGetter().returns(student,
				lastName.write(student, randomString(8))), explain(seeConsoleOutput));
	}

	@Test
	@DisplayName("Test id")
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
	@DisplayName("Test startYear")
	void startYearTests() {
		assertEquals(startYear.read(student), yearNow,
				explain(String.format("Default value of startYear is not %d",
						yearNow))
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
	@DisplayName("Test graduationYear")
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
	@DisplayName("Test setDegreeTitle")
	@SuppressWarnings("unchecked")
	void setDegreeTitleTests() {

		List<Object> studentDegrees = (List<Object>) degrees.read(student);
		Object bachelors = studentDegrees.get(0);

		Object masters = studentDegrees.get(1);

		Object doctoral = studentDegrees.get(2);

		String newTitle = "Bachelor of " + randomString(1)
				.toUpperCase() + randomString(5);
		setDegreeTitle.call(student, 0, newTitle);
		assertEquals(degreeTitle.read(bachelors), newTitle,
				explain("setDegreeTitle() does not set the correct value")
		);

		setDegreeTitle.call(student, 0, null);
		assertEquals(degreeTitle.callDefaultGetter(bachelors), newTitle,
			expectedUnchanged("the return value of getDegreeTitle", newTitle, "after attempting to set degreeTitle to null"));

		newTitle = "Master of " + randomString(1)
				.toUpperCase() + randomString(5);
		setDegreeTitle.call(student, 1, newTitle);
		assertTrue(degreeTitle.matches(masters, newTitle),
			 explain("setDegreeTitle() does not set the correct value"));

		setDegreeTitle.call(student, 1, null);
		assertEquals(degreeTitle.callDefaultGetter(masters), newTitle,
				expectedUnchanged("the return value of getDegreeTitle", newTitle, "after attempting to set degreeTitle to null"));

		newTitle = randomString(1).toUpperCase()
				+ randomString(5) + " of "
				+ randomString(1).toUpperCase()
				+ randomString(5);
		setDegreeTitle.call(student, 2, newTitle);
		assertTrue(degreeTitle.matches(doctoral, newTitle), 
				explain("setDegreeTitle() does not set the correct value"));
		setDegreeTitle.call(student, 2, null);
		assertEquals(degreeTitle.callDefaultGetter(doctoral), newTitle,
				expectedUnchanged("the return value of getDegreeTitle", newTitle, "after attempting to set degreeTitle to null"));

		// To verify that things wont' break:
		setDegreeTitle.call(student, -1, newTitle);
		setDegreeTitle.call(student, 4, newTitle);
	}

	// There is no count attribute in V2. Therefore this test is different
	// for V1 and V2
	@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V1)
	@Test
	@DisplayName("Test addCourse")
	@SuppressWarnings("unchecked")
	void addCourseTests_V1() {
		List<Object> studentDegrees = (List<Object>) degrees.read(student);
		Object degrObject, newStudentCourse;
		List<Object> coursesInDegree;
		for (int dgr = 0; dgr < 3; dgr++) {
			System.out.format("Testing the Degree object in degrees[%d]...%n", dgr);
			degrObject = studentDegrees.get(dgr);
			assertNotNull(degrObject, explain("Expected non-null value in degrees[%d]%n", dgr));

			// degrees[x].myCourses empty?
			assertEquals(count.read(degrObject), 0,
				explain("New Degree object should not have any courses"));
			coursesInDegree = (List<Object>) myCourses.read(degrObject);
			assertEquals(coursesInDegree.size(),0, explain("New Degree object should not have any courses"));

			// addCourse(x, null)
			addCourse.call(student, dgr, null);
			assertTrue(count.matches(degrObject, 0), 
					expectedUnchanged("count", 0, "after trying to add null course"));

			// addCourse(x, valid) returns true?
			newStudentCourse = randomStudentCourse();
			assertEquals(addCourse.call(student, dgr, newStudentCourse), true,
					explain("addCourse() with valid course should return true"));
			System.out.format("Comparing the added StudentCourse object with the Degree object's myCourses[%d]...%n",
					dgr);
			assertStudentCoursesEqual(newStudentCourse, coursesInDegree.get(0));

			// count == 1?
			assertEquals(count.read(degrObject), 1, explain("Incorrect count value"));

			// addCourse(x, null) returns false?
			assertTrue(addCourse.returns(student, false, dgr, null), explain("Incorrect return value of addCourse"));
			assertEquals(count.read(degrObject), 1, 
					expectedUnchanged("count", 1, "after trying to add null course"));
			System.out.println("Adding 49 more courses.");
			for (int i = 0; i < 49; i++) {
				addCourse.invoke(student, dgr, randomStudentCourse());
			}
			System.out.println("50 courses added. Adding one more course, expecting addCourse to return false.");
			assertTrue(addCourse.returns(student, false, dgr, randomStudentCourse()), 
					explain("Incorrect return value of addCourse"));
		}
	}

	@Test
	@DisplayName("Test addCourse")
	@SuppressWarnings("unchecked")
	void addCourseTests_V2() {
		List<Object> studentDegrees = (List<Object>) degrees.read(student);
		Object degrObject, newStudentCourse;
		List<Object> coursesInDegree;
		for (int dgr = 0; dgr < 3; dgr++) {
			System.out.format("Testing the Degree object in degrees[%d]...%n", dgr);
			degrObject = studentDegrees.get(dgr);
			assertNotNull(degrObject, explain("Expected non-null value in degrees[%d]%n", dgr));
			coursesInDegree = (List<Object>) myCourses.read(degrObject);
			assertEquals(coursesInDegree.size(), 0, explain("New Degree object should not have any courses"));

			// addCourse(x, null)
			addCourse.call(student, dgr, null);
			assertEquals(coursesInDegree.size(), 0,
				expectedUnchanged("amount of courses in degree", 0, "after attempting to add null course to the degree"));

			// addCourse(x, valid) returns true?
			newStudentCourse = randomStudentCourse();
			assertTrue(addCourse.returns(student, true, dgr, newStudentCourse), explain(seeConsoleOutput));
			System.out.format("Comparing the added StudentCourse object with the Degree object's myCourses[%d]...%n",
					dgr);
			assertStudentCoursesEqual(newStudentCourse, coursesInDegree.get(0));

			// count == 1?
			assertEquals(coursesInDegree.size(), 1, explain("Expected amount of degrees in courses to be 1"));

			// addCourse(x, null) returns false?
			assertTrue(addCourse.returns(student, false, dgr, null), explain(seeConsoleOutput));
			assertEquals(coursesInDegree.size(), 1, expectedUnchanged("amount of courses in degree", 1, "after trying to add a null course"));
			System.out.println("Adding 49 more courses.");
			for (int i = 0; i < 49; i++) {
				addCourse.invoke(student, dgr, randomStudentCourse());
			}
			System.out.println("50 courses added. Adding one more course, expecting addCourse to return false.");
			assertTrue(addCourse.returns(student, false, dgr, randomStudentCourse()), explain("Invalid return value of addCourse"));
		}
	}

	// there is not count attribute in V2. This test has two versions
	@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V1)
	@Test
	@DisplayName("Test addCourses")
	@SuppressWarnings("unchecked")
	void addCoursesTests_V1() {
		List<Object> studentDegrees = (List<Object>) degrees.read(student);
		Object degrObject;
		for (int dgr = 0; dgr < 3; dgr++) {
			System.out.format("Testing the Degree object in degrees[%d]...%n", dgr);
			degrObject = studentDegrees.get(dgr);
			assertNotNull(degrObject, explain("Expected non-null value in degrees[%d]%n", dgr));
			assertEquals(count.read(degrObject), 0, explain("New Degree object should not have any courses"));
			addCourses.call(student, dgr, null);
			assertTrue(count.matches(degrObject, 0), 
					expectedUnchanged("count", 0, "after trying to add null courses"));
			int coursesToAdd = rand.nextInt(2, 10);
			int nullsToAdd = rand.nextInt(2, 10);
			int coursesAdded = 0;
			int nullsAdded = 0;
			List<Object> courses = new ArrayList<Object>(coursesToAdd + nullsToAdd);
			while (true) {
				if (coursesAdded < coursesToAdd) {
					if (nullsAdded < nullsToAdd) {
						// 50/50
						if (rand.nextBoolean()) {
							courses.add(randomStudentCourse());
							coursesAdded++;
						} else {
							courses.add(null);
							nullsAdded++;
						}
					} else {
						// add course
						courses.add(randomStudentCourse());
						coursesAdded++;
					}
				} else {
					if (nullsAdded == nullsToAdd) {
						break;
					}
					// add null
					courses.add(null);
					nullsAdded++;
				}
			}

			assertTrue(addCourses.returns(student, coursesToAdd, dgr, courses), explain("Invalid return value of addCourses"));

			assertEquals(count.read(degrObject), coursesToAdd, explain("Invalid count value"));
			int nextTarget = rand.nextInt(47, 50);
			int coursesToAddUntil4X = nextTarget - coursesToAdd;
			System.out.format("Adding courses until there are %d.%n", nextTarget);
			courses = new ArrayList<Object>(coursesToAddUntil4X);
			for (int i = 0; i < coursesToAddUntil4X; i++) {
				courses.add(randomStudentCourse());
			}
			addCourses.call(student, dgr, courses);
			assertEquals(count.read(degrObject), nextTarget,
					explain("Invalid count value"));
			coursesToAdd = rand.nextInt(4, 8);
			System.out.format("Adding %d more courses, expecting %d as return value.%n", coursesToAdd,
					50 - nextTarget);
			courses = new ArrayList<Object>(coursesToAdd);
			for (int i = 0; i < coursesToAdd; i++) {
				courses.add(randomStudentCourse());
			}
			assertTrue(addCourses.returns(student, 50 - nextTarget, dgr, courses), explain("Invalid return value of addCourses"));
			System.out.println("Ok");
		}
	}

	// there is not count attribute in V2. This test has two versions
	@Test
	@DisplayName("Test addCourses")
	@SuppressWarnings("unchecked")
	void addCoursesTests_V2() {
		List<Object> coursesInDegree;
		List<Object> studentDegrees = (List<Object>) degrees.read(student);
		Object degrObject;
		for (int dgr = 0; dgr < 3; dgr++) {
			System.out.format("Testing the Degree object in degrees[%d]...%n", dgr);
			degrObject = studentDegrees.get(dgr);
			assertNotNull(degrObject, explain("Expected non-null value in degrees[%d]%n", dgr));
			coursesInDegree = (List<Object>) myCourses.read(degrObject);

			assertEquals(coursesInDegree.size(), 0,
					explain("New Degree object should not have any courses"));
			addCourses.call(student, dgr, null);
			assertEquals(coursesInDegree.size(), 0, 
					expectedUnchanged("count", 0, "after trying to add null courses"));
			
			int coursesToAdd = rand.nextInt(2, 10);
			int nullsToAdd = rand.nextInt(2, 10);
			int coursesAdded = 0;
			int nullsAdded = 0;
			List<Object> courses = new ArrayList<Object>(coursesToAdd + nullsToAdd);
			while (true) {
				if (coursesAdded < coursesToAdd) {
					if (nullsAdded < nullsToAdd) {
						// 50/50
						if (rand.nextBoolean()) {
							courses.add(randomStudentCourse());
							coursesAdded++;
						} else {
							courses.add(null);
							nullsAdded++;
						}
					} else {
						// add course
						courses.add(randomStudentCourse());
						coursesAdded++;
					}
				} else {
					if (nullsAdded == nullsToAdd) {
						break;
					}
					// add null
					courses.add(null);
					nullsAdded++;
				}
			}

			assertTrue(addCourses.returns(student, coursesToAdd, dgr, courses), explain("Invalid return value of addCourses"));

			assertEquals(coursesInDegree.size(), coursesToAdd, explain("Invalid amount of courses in degree"));
			int nextTarget = rand.nextInt(47, 50);
			int coursesToAddUntil4X = nextTarget - coursesToAdd;
			System.out.format("Adding courses until there are %d.%n", nextTarget);
			courses = new ArrayList<Object>(coursesToAddUntil4X);
			for (int i = 0; i < coursesToAddUntil4X; i++) {
				courses.add(randomStudentCourse());
			}
			addCourses.call(student, dgr, courses);
			assertEquals(coursesInDegree.size(), nextTarget,
					explain("Invalid amount of courses in degree"));
			coursesToAdd = rand.nextInt(4, 8);
			System.out.format("Adding %d more courses, expecting %d as return value.%n", coursesToAdd,
					50 - nextTarget);
			courses = new ArrayList<Object>(coursesToAdd);
			for (int i = 0; i < coursesToAdd; i++) {
				courses.add(randomStudentCourse());
			}
			assertTrue(addCourses.returns(student, 50 - nextTarget, dgr, courses), explain("Invalid return value of addCourses"));
			System.out.println("Ok");
		}
	}

	@Test
	@DisplayName("Test setTitleOfThesis")
	@SuppressWarnings("unchecked")
	void setTitleOfThesisTests() {
		List<Object> studentDegrees = (List<Object>) degrees.read(student);
		Object degrObject;
		for (int dgr = 0; dgr < 3; dgr++) {
			degrObject = studentDegrees.get(dgr);
			String newTitle = greatTitle();
			setTitleOfThesis.call(student, dgr, newTitle);
			assertEquals(titleOfThesis.read(degrObject), newTitle, explain("Incorrect titleOfThesis value"));
			setTitleOfThesis.call(student, dgr, null);
			assertEquals(titleOfThesis.callDefaultGetter(degrObject), newTitle,
					expectedUnchanged("return value of getTitleOfThesis", newTitle, "after trying to set it to null"));


		}
	}

	@Test
	@DisplayName("Test printCourses")
	void printCoursesTests() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
				.map(i -> i == 6 ? 'F' : i)
				.map(i -> i == 7 ? 'A' : i)
				.limit(testCases * maxCoursesPerCase).iterator();
		for (int i = 0; i < testCases; i++) {
			student = twoParamStudent.instantiate(null, (Object) null);
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addCourse.call(student, j % 3, randomStudentCourse(grades.nextInt(), regex));
				studentCourseRegexes[j] = regex.toString();
			}
			String output = captureOutput(studentPrintCourses, student);
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output, "in the output of printCourses", re.substring(0, 9));
			}
		}
	}

	@Test
	@DisplayName("Test printDegrees")
	void printDegreesTest() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
				.map(i -> i == 6 ? 'F' : i)
				.map(i -> i == 7 ? 'A' : i)
				.limit(testCases * maxCoursesPerCase).iterator();
		int[] coursesPerDegree;
		String degreeTitles[] = new String[3];
		String thesisTitles[] = new String[3];

		for (int i = 0; i < testCases; i++) {
			coursesPerDegree = new int[3];
			student = twoParamStudent.instantiate(null, (Object) null);
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addCourse.call(student, j % 3, randomStudentCourse(grades.nextInt(), regex));
				coursesPerDegree[j % 3]++;
				studentCourseRegexes[j] = regex.toString();
			}
			// Set the titles
			String degreeTitle, thesisTitle;
			for (int dgr = 0; dgr < 3; dgr++) {
				degreeTitle = dgr == 0 ? "Bachelor of "
						: dgr == 1 ? "Master of "
								: randomString(1)
										.toUpperCase()
										+ randomString(5)
										+ " of ";
				degreeTitle += randomString(1).toUpperCase()
						+ randomString(5);
				setDegreeTitle.call(student, dgr, degreeTitle);
				thesisTitle = greatTitle();
				setTitleOfThesis.call(student, dgr, thesisTitle);
				degreeTitles[dgr] = degreeTitle;
				thesisTitles[dgr] = thesisTitle;
			}
			// Read the console output
			System.out.println("Calling printDegrees()");
			String output = captureOutput(printDegrees, student);


			// Verify each degree is represented in the output

			int endIndex = 0;
			
			for (int dgr = 0; dgr < 3; dgr++) {
				String degreePattern = "Degree\\[Title:\"?" + degreeTitles[dgr]
					+ "\"? \\(courses: " + coursesPerDegree[dgr] + "\\)"
					+ "Thesis title: \"?" + thesisTitles[dgr] + "\"?";
				//degree identifier --> "^Degree\[Title:"?"" + degreeTitle
				//length of the prefix is 20
				endIndex = 20 + degreeTitles[dgr].length()-2; // end index of degreePattern that fully includes the degreeTitle variable

				findIgnoringWhitespace(degreePattern, output, "in the output of printDegrees", degreePattern.substring(0, endIndex));
			}
			// Verify each course is represented in the output
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output, "in the output of printDegrees", re.substring(0, 9));
			}
		}
	}

	@Test
	@DisplayName("Test getStudyYears")
	void getStudyYearsTests() {
		// The student has not graduated
		// Default value = current year - start year (current year) = 0
		assertEquals(getStudyYears.call(student), 0, explain("Incorrect return value of getStudyYears"));

		// New starting year
		int newStartYear = minYear + (int) Math.ceil((yearNow - minYear) / 2);
		startYear.write(student, newStartYear);
		int expectedStudyYears = yearNow - newStartYear;
		assertEquals(getStudyYears.call(student), expectedStudyYears, explain("Incorrect return value of getStudyYears"));

		// After graduation
		graduate_H3(student, newStartYear, yearNow);
		assertEquals(getStudyYears.call(student), expectedStudyYears, explain("Incorrect return value of getStudyYears"));

		// Test another graduation year
		Object student2 = twoParamStudent.instantiate(null, (Object) null);
		startYear.write(student2, minYear);
		graduate_H3(student2, newStartYear, newStartYear);
		assertEquals(getStudyYears.call(student2), 0, explain("Incorrect return value of getStudyYears"));
	}

	@Test
	@DisplayName("Test birthDate")
	void birthDateTests() {
		// Default value
		assertMatches(personBirthDate.read(student), NOT_AVAILABLE_PATTERN, "birthDate has incorrect default value");
		// setBirthDate(null) -> birthDate
		assertMatches(setBirthDate.call(student, (Object) null),
				NO_CHANGE_PATTERN, "birthDate should remain unchanged when attempting to set it to null"
		);
		
		// setBirthDate -> birthDate
		String randomId = makePersonID();
		String expectedBD = personIdToBirthDate(randomId);
		// note that in StringMethods.returns() expected value is handled as regex
		// (a stricter check might be needed in the future)
		assertEquals(setBirthDate.call(student, randomId), expectedBD, explain("Incorrect return value of setBirthDate"));
		assertEquals(personBirthDate.read(student), expectedBD, explain("Incorrect birthDate value"));
		// getBirthDate
		assertEquals(personBirthDate.defaultGetter().call(student), expectedBD, explain("Incorrect return value of setBirthDate"));

		// invalid id
		String invalidId = makePersonIDWithIncorrectChecksum();

		assertMatches(setBirthDate.call(student, invalidId), NO_CHANGE_PATTERN, "Invalid return value of setBirthDate ");
		assertEquals(personBirthDate.read(student), expectedBD,
				explain("Expected birthDate to stay " + expectedBD)
		);
		// invalid id (random string)
		String randString = randomString(11);

		assertMatches(setBirthDate.call(student, randString),
				NO_CHANGE_PATTERN,
				"birthDate should remain unchanged when attempting to set it to an invalid id"
		);
		assertEquals(personBirthDate.matches(student, randString), false,
				explain("Setting 'birthDate' to " + randString
						+ " shouldn't be possible.")
		);
	}

	@Test
	@DisplayName("Test getRandomId")
	void getRandomIdTest() throws Exception {
		Range<Integer> valid = Range.closed(1, 100);
		for (int i = 0; i < 1000; i++) {
			// Allowed values for student ID are 1-100
			int value = getRandomId.invoke(student, MIN_STUDENT_ID, MAX_STUDENT_ID);
			assertTrue(valid.includes(value),
					explain("Expected random id within " + valid.toString() + ", got " + value));
		}
						
		/*
		Disabled as this test requires the use of two param nextInt method, which requires java version 17 or higher
		// Check if the random method utilizes gets its min and max values from ConstantValues
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework3/";
		File file = new File(path + "Student.java");
		
		files.add(file);
		
		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValuesIdCheck", files);

		assertTrue((boolean) result[0], explain(result[1].toString()));
		*/

	}

	
}
