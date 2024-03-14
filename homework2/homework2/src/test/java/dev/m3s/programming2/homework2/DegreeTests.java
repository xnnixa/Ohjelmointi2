package dev.m3s.programming2.homework2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

import dev.m3s.maeaettae.jreq.Callable;
import dev.m3s.maeaettae.jreq.StringMethods;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

@TestCategories({ @TestCategory("Degree") })
public class DegreeTests {

	private Object degree;

	@BeforeEach
	public void setUp() {
		degree = paramlessDegree.create();
	}

	@Test
	@DisplayName("constantValuesUsageTest")
	public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework2/";
		File file = new File(path + "Degree.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }


	@Test
	@DisplayName("MAX_COURSES")
	void maxCoursesTest() {
		// MAX_COURSES (constant value)
		assertEquals(MAX_COURSES.read(degree), 50, explain("Expected maximum amount of courses to be 50"));
	}

	@Test
	@DisplayName("count, addStudentCourse(s)")
	void countTests() {
		// count (default value)
		assertEquals(count.read(degree), 0, explain("Invalid count default value"));
		// addStudentCourse, count=1
		addStudentCourse.call(degree, randomStudentCourse(false));
		assertEquals(count.read(degree), 1, explain("Invalid count value"));
		// addStudentCourses, count=3
		addStudentCourses.call(degree, randomStudentCourse(false),
				randomStudentCourse(false)
		);
		assertEquals(count.read(degree), 3, explain("Invalid count value"));

		// addStudentCourses(null), count=3
		addStudentCourses.call(degree, (Object) null);
		assertEquals(count.read(degree), 3,
				explain("Expected count to stay 3 after trying to add null courses.")
		);
		// addStudentCourse(null), count=3
		addStudentCourse.call(degree, (Object) null);
		assertEquals(count.read(degree), 3,
				explain("Expected count to stay 3 after trying to add null course.")
		);
		// addStudentCourse, count=50
		System.out.println("Adding 47 more courses...");
		for (int i = 0; i < 47; i++) {
			addStudentCourse.invoke(degree, randomStudentCourse(true));
		}
		assertEquals(count.read(degree), 50, 
				explain("Expected count to be maximum value of 50"));
		// addStudentCourse boundary test, count=50
		System.out.println("Adding one more course...");
		assertEquals(count.read(degree), 50,
				explain("Expected count to stay 50 after adding one more course.")
		);
		// addStudentCourses boundary test, count=50
		int numCourses = rand.nextInt(2, 5);
		System.out.println("Adding " + numCourses + " more courses...");
		Object[] courses = IntStream.range(0, numCourses)
									.mapToObj(i -> randomStudentCourse(true))
									.toArray();
		addStudentCourses.call(degree, courses);
		assertEquals(count.read(degree), 50,
				explain("Expected count to stay 50 after adding %d more courses.",
						numCourses
				)
		);
	}

	@Test
	@DisplayName("degreeTitle")
	void degreeTitleTests() {
		// degreeTitle (default value)
		assertMatches(degreeTitle.read(degree), NO_TITLE_PATTERN, "Invalid degreeTitle default value");

		Callable<?> setter = degreeTitle.defaultSetter();
		StringMethods getter = degreeTitle.defaultGetter();

		// getDegreeTitle (default value)
		assertGetterReturnsCurrentValue(degree, degreeTitle, getter, "degreeTitle");
		// setDegreeTitle
		String randomTitle = randomString(8) + " " + randomString(8);
		assertSetterUpdatesCurrentValue(degree, degreeTitle, setter, getter,
				randomTitle, "degreeTitle"
		);
		// getDegreeTitle (new value)
		assertGetterReturnsCurrentValue(degree, degreeTitle, getter, "degreeTitle");

		// setDegreeTitle(null)
		setter.call(degree, null);
		assertEquals(getter.call(degree), randomTitle,
				explain("Setting degreeTitle to null shouldn't be possible."));
		// Expecting no change in degreeTitle
		assertEquals(degreeTitle.read(degree), randomTitle,
				explain("Expected degreeTitle to stay \"%s\" "
						+ "after trying to set it to null", randomTitle)
		);
	}

	@Test
	@DisplayName("titleOfThesis")
	void titleOfThesisTests() {
		// titleOfThesis (default value)
		assertMatches(titleOfThesis.read(degree), NO_TITLE_PATTERN, "Invalid titleOfThesis default value");

		Callable<?> setter = titleOfThesis.defaultSetter();
		StringMethods getter = titleOfThesis.defaultGetter();

		// getTitleOfThesis (default value)
		assertGetterReturnsCurrentValue(degree, titleOfThesis, getter, "titleOfThesis");
		// setTitleOfThesis
		String randomTitle = randomString(8) + " " + randomString(8);
		assertSetterUpdatesCurrentValue(degree, titleOfThesis, setter, getter,
				randomTitle, "titleOfThesis"
		);
		// getTitleOfThesis (new value)
		assertGetterReturnsCurrentValue(degree, titleOfThesis, getter, "titleOfThesis");

		// setTitleOfThesis(null)9
		setter.call(degree, null);
		assertEquals(getter.call(degree), randomTitle,
				explain("Setting titleOfThesis to null shouldn't be possible."));
		// Expecting no change in titleOfThesis
		assertEquals(titleOfThesis.read(degree), randomTitle,
				explain("Expected titleOfThesis to stay \"%s\" "
						+ "after trying to set it to null", randomTitle)
		);
	}

	@Test
	@DisplayName("myCourses, addStudentCourse(s)")
	void myCoursesTests() {
		Object[] returnedCourses = (Object[]) myCourses.read(degree);

		// myCourses default array length
		int length = 0;
		for (; length < returnedCourses.length; length++) {
			if (returnedCourses[length] == null) {
				break;
			}
		}
		System.out.println("Verifying that the default array length is 0...");
		assertTrue(length == 0,
				explain("Expected a new degree object to have no courses.")
		);
		// Add one using addStudentCourse
		Object studentCourse = randomStudentCourse(false);
		addStudentCourse.call(degree, studentCourse);

		// getCourses
		returnedCourses = (Object[]) getCourses.call(degree);
		System.out.println("Accessing getCourses()[0]...");

		// Array reference has changed if the student code does
		// defensive copying, so a simple equality check of the
		// reference is not enough.
		assertStudentCoursesEqual(studentCourse, returnedCourses[0]);

		// Access the array held by myCourses
		Object[] heldCourses = (Object[]) myCourses.read(degree);
		System.out.println("Accessing myCourses[0]...");
		assertStudentCoursesEqual(studentCourse, heldCourses[0]);

		// Add a few courses more
		int numCourses = rand.nextInt(2, 5);
		System.out.println("Adding " + numCourses + " more courses...");
		Object[] courses = IntStream.range(0, numCourses)
									.mapToObj(i -> randomStudentCourse(false))
									.toArray();
		addStudentCourses.call(degree, courses);
		returnedCourses = (Object[]) getCourses.call(degree);
		// getCourses
		for (int i = 0; i < numCourses; i++) {
			// Access the array returned by getCourses
			System.out.println("Checking getCourses()[" + i + "]...");
			assertStudentCoursesEqual(courses[i], returnedCourses[i + 1]);
			// Access the array held by myCourses
			System.out.println("Checking myCourses[" + i + "]...");
			assertStudentCoursesEqual(courses[i], heldCourses[i + 1]);
		}
	}

	@Test
	@DisplayName("isCourseCompleted, getCredits(ByX)")
	void getCreditsTests() {
		double totalCredits = 0;
		HashMap<Character, Double> byBaseMap = new HashMap<>();
		HashMap<Integer, Double> byTypeMap = new HashMap<>();

		// StudentCourses for addStudentCourse
		int numCourses = rand.nextInt(8, 13);
		// A few more courses for addStudentCourses
		int lastCourses = rand.nextInt(2, 5);
		// A, P, S, A, P, S, etc.
		OfInt bases = IntStream.iterate(0, i -> (i + 1) % 3)
							   .map("APS"::charAt)
							   .limit(numCourses + lastCourses)
							   .iterator();
		// 0, 1, 0, 1, 0, 1, 0, 1, etc.
		OfInt types = IntStream.iterate(0, i -> Math.abs(i - 1))
							   .limit(numCourses + lastCourses)
							   .iterator();
		// 0, 1, 2, 3, 4, 5, F, A, 0, 1, 2, etc.
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
								.map(i -> i == 6 ? 'F' : i)
								.map(i -> i == 7 ? 'A' : i)
								.limit(numCourses + lastCourses)
								.iterator();
		Object newStudentCourse;
		char base;
		int type;
		double credits;
		int grade;
		for (int i = 0; i < numCourses; i++) {
			// Next student course
			base = (char) bases.next()
							   .intValue();
			type = types.nextInt();
			credits = rand.nextInt(56);
			grade = grades.nextInt();
			if (grade != 0 && grade != 'F') {
				totalCredits += credits;
			}
			newStudentCourse = createStudentCourseAndTestIsCourseCompleted(
					byBaseMap, byTypeMap, base, type, credits, grade);
			// addStudentCourse()
			addStudentCourse.call(degree, newStudentCourse);
		}
		// The remaining courses use addStudentCourses
		Object[] courses = new Object[lastCourses];
		for (int i = 0; i < lastCourses; i++) {
			base = (char) bases.next()
							   .intValue();
			type = types.nextInt();
			credits = rand.nextInt(56);
			grade = grades.nextInt();
			if (grade != 0 && grade != 'F') {
				totalCredits += credits;
			}
			courses[i] = createStudentCourseAndTestIsCourseCompleted(byBaseMap,
					byTypeMap, base, type, credits, grade
			);
		}
		// addStudentCourses()
		addStudentCourses.call(degree, courses);
		System.out.println(
				"-----------------------------------------------------\n"
						+ "Total credits: " + totalCredits
						+ "\nCredits by base: " + byBaseMap
						+ "\nCredits by type: " + byTypeMap
						+ "\n-----------------------------------------------------");

		// getCredits
		assertEquals(getCredits.call(degree), totalCredits,
				explain("Invalid getCredits return value")
		);
		// getCreditsByBase
		double currentAmount;
		for (Character baseChar : byBaseMap.keySet()) {
			currentAmount = byBaseMap.get(baseChar);
			assertEquals(getCreditsByBase.call(degree, baseChar), currentAmount, explain("getCreditsByBase returns incorrect amount of credits for course base '%c'", baseChar));
		}
		// getCreditsByType
		for (int typeInt : byTypeMap.keySet()) {
			currentAmount = byTypeMap.get(typeInt);
			assertEquals(getCreditsByType.call(degree, typeInt), currentAmount, explain("getCreditsByType returns incorrect amount of credits for type %d", typeInt));

		}
		
		//test isCourseCompleted null parameter
		boolean completed = isCourseCompleted.call(degree, null, explain("null studentCourse should not be passed"));
		assertFalse(completed);
	}

	private Object createStudentCourseAndTestIsCourseCompleted(
			HashMap<Character, Double> byBaseMap,
			HashMap<Integer, Double> byTypeMap, char base, int type,
			double credits, int grade) {
		boolean numericGrading = grade != 'F' && grade != 'A';
		// New Course instance
		System.out.println();
		Object newCourse = randomCourse(base, type, credits, numericGrading,
				null, false
		);
		// New StudentCourse instance
		Object newStudentCourse = randomStudentCourse(grade, newCourse);
		System.out.println();
		// isCourseCompleted()
		boolean completed = isCourseCompleted.call(degree, newStudentCourse);
		if (grade > 0 && grade != 'F') {
			assertTrue(completed,
					explain("Expected isCourseCompleted to return "
									+ "true for a StudentCourse object with gradeNum %c.",
							(grade < 6 ? '0' + grade : grade)
					)
			);
			byBaseMap.compute(base,
					(b, byBase) -> (byBase == null) ? credits : byBase + credits
			);
			byTypeMap.compute(type,
					(t, byType) -> (byType == null) ? credits : byType + credits
			);
		} else {
			assertFalse(completed,
					explain("Expected isCourseCompleted to return "
									+ "false for a StudentCourse object with gradeNum %c.",
							(grade < 6 ? '0' + grade : grade)
					)
			);
		}
		return newStudentCourse;
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
			degree = paramlessDegree.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addStudentCourse.call(degree,
						randomStudentCourse(grades.nextInt(), regex, false)
				);
				studentCourseRegexes[j] = regex.toString();
			}
			String output = captureOutput(printCourses, degree);
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output,
						"in the output of printCourses", re.substring(0, 9)
				);
			}
		}
	}

	@Test
	@DisplayName("toString")
	void toStringTests() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
								.map(i -> i == 6 ? 'F' : i)
								.map(i -> i == 7 ? 'A' : i)
								.limit(testCases * maxCoursesPerCase)
								.iterator();
		for (int i = 0; i < testCases; i++) {
			degree = paramlessDegree.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addStudentCourse.call(degree,
						randomStudentCourse(grades.nextInt(), regex, false)
				);
				studentCourseRegexes[j] = regex.toString();
			}
			String output = degreeToString.call(degree);
			String title = degreeTitle.get(degree);
			String thesisTitle = titleOfThesis.get(degree);
			String degreePattern = "^Degree\\[Title:\"?" + title
					+ "\"?\\(courses:" + coursesToAdd + "\\)"
					+ "Thesis title:\"?" + thesisTitle + "\"?";
			findIgnoringWhitespace(degreePattern, output,
					"in the return value of toString", degreePattern.substring(0, 10)
			);
			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output,
						"in the return value of toString", re.substring(0, 9) 
				);
			}
		}
	}
}
