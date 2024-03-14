package dev.m3s.programming2.homework2;

import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

import dev.m3s.maeaettae.jreq.*;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
@TestCategories({ @TestCategory("Student") })
class V2StudentTests {
	static IntFields degreeCount = classStudent.intField()
			.hasName("degreeCount")
			.isPrivate();

	static Fields degrees = classStudent.hasField()
			.hasName("degrees")
			.hasType(Types.asArray(classDegree))
			.isPrivate();


	static Methods setDegreeTitle = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("setDegreeTitle")
			.hasParameter(int.class)
			.hasParameter(String.class);

	static BooleanMethods addCourse = classStudent.booleanMethod()
			.hasName("addCourse")
			.hasParameter(int.class)
			.hasParameter(classStudentCourse);

	static IntMethods addCourses = classStudent.intMethod()
			.hasName("addCourses")
			.hasParameter(int.class)
			.hasParameter(Types.asArray(classStudentCourse));

	static Methods printDegrees = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("printDegrees");

	static Methods setTitleOfThesis = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("setTitleOfThesis")
			.hasParameter(int.class)
			.hasParameter(String.class);

	private Object student;

	@BeforeEach
	void setUp() {
		student = paramlessStudent.create();
		// Assert that the default number of degrees is correct
		assertEquals(degreeCount.read(student), 3, explain("Expected default number of degrees to be 3"));
		// Assert that the degrees are not null:
		assertNotNull(degrees.read(student),
				explain("Expected 'degrees' to be initialized"));
		Object[] defaultDegrees = (Object[]) degrees.read(student);
		assertNotNull(defaultDegrees[0],
				explain("Expected non-null value in degrees[0]"));
		assertNotNull(defaultDegrees[1],
				explain("Expected non-null value in degrees[1]"));
		assertNotNull(defaultDegrees[2],
				explain("Expected non-null value in degrees[2]"));
	}

	@Test
	@DisplayName("setDegreeTitle")
	void setDegreeTitleTests() {
		Object[] studentDegrees = (Object[]) degrees.read(student);
		Object bachelors = studentDegrees[0];

		Object masters = studentDegrees[1];

		Object doctoral = studentDegrees[2];

		String newTitle = "Bachelor of " + randomString(1).toUpperCase() + randomString(5);
		setDegreeTitle.call(student, 0, newTitle);
		assertEquals(degreeTitle.read(bachelors), newTitle, explain("setDegreeTitle() does not set the correct value"));

		setDegreeTitle.call(student, 0, null);
		assertEquals(degreeTitle.read(bachelors), newTitle, 
			expectedUnchanged("degreeTitle", newTitle, "attempting to set degreeTitle to null"));

		newTitle = "Master of " + randomString(1).toUpperCase() + randomString(
				5);
		setDegreeTitle.call(student, 1, newTitle);
		assertEquals(degreeTitle.read(masters), newTitle, explain("setDegreeTitle() does not set the correct value"));
		setDegreeTitle.call(student, 1, null);
		assertEquals(degreeTitle.read(masters), newTitle, 
			expectedUnchanged("degreeTitle", newTitle, "attempting to set degreeTitle to null"));

		newTitle = randomString(1).toUpperCase() + randomString(5) + " of "
				+ randomString(1).toUpperCase() + randomString(5);
		setDegreeTitle.call(student, 2, newTitle);
		assertEquals(degreeTitle.read(doctoral), newTitle, explain("setDegreeTitle() does not set the correct value"));
		setDegreeTitle.call(student, 2, null);
		assertEquals(degreeTitle.read(doctoral), newTitle, 
			expectedUnchanged("degreeTitle", newTitle, "attempting to set degreeTitle to null"));

		// To verify that things wont' break:
		setDegreeTitle.call(student, -1, newTitle);
		setDegreeTitle.call(student, 4, newTitle);
	}

	@Test
	@DisplayName("addCourse")
	void addCourseTests() {
		Object[] studentDegrees = (Object[]) degrees.read(student);
		Object degrObject, newStudentCourse;
		Object[] coursesInDegree;
		for (int dgr = 0; dgr < 3; dgr++) {
			System.out.format("Testing the Degree object in degrees[%d]...%n",
					dgr);
			degrObject = studentDegrees[dgr];
			assertNotNull(degrObject,
					explain(String.format("Expected non-null value in degrees[%d]%n",
							dgr)));

			// degrees[x].myCourses empty?
			assertEquals(count.read(degrObject), 0,
					explain("New Degree object should not have any courses"));
			coursesInDegree = (Object[]) myCourses.read(degrObject);
			assertEquals(coursesInDegree[0], null,
					explain("New Degree object should not have any courses"));

			// addCourse(x, null)
			addCourse.call(student, dgr, null);
			assertEquals(count.read(degrObject), 0,
					explain("Expected count to stay 0 after trying to add null courses.\n"
							+ seeConsoleOutput));

			// addCourse(x, valid) returns true?
			newStudentCourse = randomStudentCourse(false);
			assertTrue(addCourse.returns(student, true, dgr, newStudentCourse),
					explain("Incorrect addCourse return value"));
			System.out.format(
					"Comparing the added StudentCourse object with the Degree object's myCourses[%d]...%n",
					dgr);
			assertStudentCoursesEqual(newStudentCourse, coursesInDegree[0]);

			// count == 1?
			assertEquals(count.read(degrObject), 1, explain("Expected the value of count to be 1"));

			// addCourse(x, null) returns false?
			assertTrue(addCourse.returns(student, false, dgr, null),
					explain("Expected addCourse with null course parameter to return false"));
			assertEquals(count.read(degrObject), 1,
					explain("Expected the count to stay 1\n" + seeConsoleOutput));
			System.out.println("Adding 49 more courses.");
			for (int i = 0; i < 49; i++) {
				addCourse.invoke(student, dgr, randomStudentCourse(true));
			}
			System.out.println(
					"50 courses added. Adding one more course, expecting addCourse to return false.");
			assertEquals(addCourse.call(student, dgr,
					randomStudentCourse(false)), false, explain("calling addCourse() when there are maximum amount of courses should return false"));
		}
	}

	@Test
	@DisplayName("addCourses")
	void addCoursesTests() {
		Object[] studentDegrees = (Object[]) degrees.read(student);
		Object degrObject;
		for (int dgr = 0; dgr < 3; dgr++) {
			System.out.format("Testing the Degree object in degrees[%d]...%n",
					dgr);
			degrObject = studentDegrees[dgr];
			assertNotNull(degrObject,
					explain(String.format("Expected non-null value in degrees[%d]%n",
							dgr)));
			assertEquals(count.read(degrObject), 0,
					explain("New Degree object should not have any courses\n"
							+ seeConsoleOutput));
			addCourses.call(student, dgr, null);
			assertEquals(count.read(degrObject), 0,
					explain("Expected count to stay 0 after trying to add null courses.\n"
							+ seeConsoleOutput));
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
			int testAddCourses = addCourses.call(student, dgr, courses);
			assertEquals(testAddCourses, coursesToAdd, explain("addCourses should return the amount of courses added"));
					
			assertEquals(count.read(degrObject), coursesToAdd,
				explain("Expected Degree.count to go up by " + coursesToAdd)
			);
			int nextTarget = rand.nextInt(47, 50);
			int coursesToAddUntil4X = nextTarget - coursesToAdd;
			System.out.format("Adding courses until there are %d.%n",
					nextTarget);
			courses = new Object[coursesToAddUntil4X];
			for (i = 0; i < coursesToAddUntil4X; i++) {
				courses[i] = randomStudentCourse(true);
			}
			addCourses.call(student, dgr, courses);
			assertEquals(count.read(degrObject), nextTarget,
					explain("Expected Degree.count to go up by " + coursesToAddUntil4X));
			coursesToAdd = rand.nextInt(4, 8);
			System.out.format(
					"Adding %d more courses, expecting %d as return value.%n",
					coursesToAdd, 50 - nextTarget);
			courses = new Object[coursesToAdd];
			for (i = 0; i < coursesToAdd; i++) {
				courses[i] = randomStudentCourse(true);
			}
			testAddCourses = addCourses.call(student, dgr, courses);
			assertEquals(testAddCourses, 50-nextTarget, 
				explain("Expected addCourses() to return the amount of courses it added"));
		}
	}

	@Test
	@DisplayName("setTitleOfThesis")
	void setTitleOfThesisTests() {
		Object[] studentDegrees = (Object[]) degrees.read(student);
		Object degrObject;
		for (int dgr = 0; dgr < 3; dgr++) {
			degrObject = studentDegrees[dgr];
			String newTitle = greatTitle();
			setTitleOfThesis.call(student, dgr, newTitle);
			assertEquals(titleOfThesis.read(degrObject), newTitle, 
					explain("Expected setTitleOfThesis to be set to \"%s\"", newTitle));
			setTitleOfThesis.call(student, dgr, null);
			assertEquals(titleOfThesis.read(degrObject), newTitle, 
					expectedUnchanged("titleOfThesis", degrObject, "after trying to set it to null"));
		}
	}

	@Test
	@DisplayName("printCourses")
	void printCoursesTests() {
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
				addCourse.call(student, j % 3,
						randomStudentCourse(grades.nextInt(), regex, false));
				studentCourseRegexes[j] = regex.toString();
			}
			String output = captureOutput(studentPrintCourses, student);
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output, "in the output of printCourses", re.substring(0, 9));
			}
		}
	}

	@Test
	@DisplayName("printDegrees")
	void printDegreesTest() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
				.map(i -> i == 6 ? 'F' : i)
				.map(i -> i == 7 ? 'A' : i)
				.limit(testCases * maxCoursesPerCase)
				.iterator();
		int[] coursesPerDegree;
		String[] degreeTitles = new String[3];
		String[] thesisTitles = new String[3];

		for (int i = 0; i < testCases; i++) {
			coursesPerDegree = new int[3];
			student = paramlessStudent.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addCourse.call(student, j % 3,
						randomStudentCourse(grades.nextInt(), regex, false));
				coursesPerDegree[j % 3]++;
				studentCourseRegexes[j] = regex.toString();
			}
			// Set the titles
			String degreeTitle, thesisTitle;
			for (int dgr = 0; dgr < 3; dgr++) {
				degreeTitle = dgr == 0
						? "Bachelor of "
						: dgr == 1 ? "Master of "
								: randomString(
										1).toUpperCase() + randomString(5)
										+ " of ";
				degreeTitle += randomString(1).toUpperCase() + randomString(5);
				setDegreeTitle.call(student, dgr, degreeTitle);
				thesisTitle = greatTitle();
				setTitleOfThesis.call(student, dgr, thesisTitle);
				degreeTitles[dgr] = degreeTitle;
				thesisTitles[dgr] = thesisTitle;
			}
			// Read the console output
			System.out.println("Calling printDegrees()");
			String output = captureOutput(printDegrees, student);

			String degreePattern;
			// Verify each degree is represented in the output
			for (int dgr = 0; dgr < 3; dgr++) {
				degreePattern = "Degree \\[Title: \"?"
						+ degreeTitles[dgr]
						+ "\"? \\(courses: "
						+ coursesPerDegree[dgr] + "\\)"
						+ "Thesis title: \"?"
						+ thesisTitles[dgr] + "\"?";
				findIgnoringWhitespace(degreePattern, output,
						"in the output of printDegrees", degreePattern.substring(0, 6));
			}
			// Verify each course is represented in the output
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output, "in the output of printDegrees", re.substring(0, 9));
			}
		}
	}

	@Test
	@DisplayName("getStudyYears")
	void getStudyYearsTests() {
		// The student has not graduated
		// Default value = current year - start year (current year) = 0
		assertTrue(getStudyYears.returns(student, 0), 
				explain("getStudyYears should return 0 when start year is same as the current year")
				.expected(Integer.toString(0)).but("was: " + getStudyYears.call(student)));

		// New starting year
		int newStartYear = rand.nextInt(minYear + 1, yearNow);
		startYear.write(student, newStartYear);
		int expectedStudyYears = yearNow - newStartYear;
		assertEquals(getStudyYears.call(student), expectedStudyYears,
				explain("Incorrect getStudyYears() return value"));

		// After graduation
		graduateV2(student, newStartYear, yearNow);
		assertEquals(getStudyYears.call(student), expectedStudyYears,
				explain("Incorrect getStudyYears() return value after student has graduated"));

		// Test another graduation year
		Object student2 = paramlessStudent.create();
		startYear.write(student2, minYear);
		graduateV2(student2, newStartYear, newStartYear);
		assertEquals(getStudyYears.call(student2), 0,
				explain("Incorrect getStudyYears() return value after student has graduated"));
	}
}
