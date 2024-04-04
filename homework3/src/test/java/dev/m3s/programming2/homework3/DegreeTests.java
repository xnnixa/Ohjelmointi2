package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import java.util.HashMap;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.m3s.maeaettae.jreq.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@TestCategories({ @TestCategory("Degree") })
class DegreeTests {

	private Object degree;

	@BeforeEach
	void setUp() {
		degree = paramlessDegree.instantiate();
	}

	@Test
	@DisplayName("constantValuesUsageTest")
	public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework3/";
		File file = new File(path + "Degree.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }

	@Test
	@DisplayName("All methods should be non-static")
	void requireNonStaticMethods() {
		classDegree.hasMethod().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test MAX_COURSES")
	void maxCoursesTest() {
		assertEquals(MAX_COURSES.read(degree), 50, explain("Expected maximum amount of courses to be 50"));
	}

	@Test
	@DisplayName("Test myCourses addStudentCourse(s)")
	@SuppressWarnings("unchecked") // This is a test, we know what we're doing
	void myCoursesTests() {
		List<Object> returnedCourses = (List<Object>) getCourses.call(degree);

		// Default array length
		int length = 0;
		for (; length < returnedCourses.size(); length++) {
			if (returnedCourses.get(length) == null) {
				break;
			}
		}
		assertEquals(length, 0,
				explain("Expected a new degree object to have no courses.")
		);
		// Add one
		Object studentCourse = randomStudentCourse();
		addStudentCourse.call(degree, studentCourse);

		// Access the array returned by getCourses
		returnedCourses = (List<Object>) getCourses.call(degree); // Array reference might have changed (defensive copying)
		System.out.println("Accessing getCourses()[0]...");
		assertStudentCoursesEqual(studentCourse, returnedCourses.get(0));
		List<Object> heldCourses = (List<Object>) myCourses.read(degree);

		// Access the array held by myCourses
		System.out.println("Accessing myCourses[0]...");
		assertStudentCoursesEqual(studentCourse, heldCourses.get(0));

		// Add two
		Object newStudentCourse1 = randomStudentCourse();
		Object newStudentCourse2 = randomStudentCourse();
		List<Object> studentCoursesList = new ArrayList<Object>();
		studentCoursesList.add(newStudentCourse1);
		studentCoursesList.add(newStudentCourse2);
		addStudentCourses.call(degree, studentCoursesList);

		// Access the array returned by getCourses
		returnedCourses = (List<Object>) getCourses.call(degree);
		System.out.println("Accessing getCourses()[1]...");
		assertStudentCoursesEqual(newStudentCourse1, returnedCourses.get(1));
		System.out.println("Accessing getCourses()[2]...");
		assertStudentCoursesEqual(newStudentCourse2, returnedCourses.get(2));

		// Access the array held by myCourses
		System.out.println("Accessing myCourses[2]...");
		assertStudentCoursesEqual(newStudentCourse2, heldCourses.get(2));

		System.out.println("Accessing myCourses[0]...");
		assertStudentCoursesEqual(studentCourse, heldCourses.get(0));
	}

	// NOTE: the count attribute in degree class is removed.
	// Tests using count attribute for retrieving the number of courses are changed.
	// size of myCourses is used instead of count
	@Test
	@DisplayName("Test course count after calling addStudentCourse(s)")
	@SuppressWarnings("unchecked")
	void countTests_V2() {
		List<Object> degreeCourses = new ArrayList<Object>();
		int numCourses = 0;
		int expected = 0;

		// TEST if the initial size of degree.myCourses is 0
		// Add a course
		degreeCourses = (List<Object>) getCourses.call(degree);
		System.out.println(String.format("Checking whether %s is %d", "Initial number of courses", 0));
		numCourses = degreeCourses.size();

		assertEquals(numCourses, expected, explain("Incorrect number of courses in the degree"));

		// TEST if the size of degree.myCourses is 1 after adding one course
		System.out.println(String.format("Initial number of courses: OK "));
		// Add one course
		expected = 1;
		System.out.println(String.format("Checking whether %s is %d",
				"the number of courses in the degree after adding one course", expected));
		addStudentCourse.call(degree, randomStudentCourse());
		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();

		assertEquals(numCourses, expected, explain("Incorrect number of courses in the degree"));
	// TEST if the size of degree.myCourses is 3 after adding a list of 2 courses
		// using addCourses
		List<Object> studentCourseList = new ArrayList<Object>();
		// Add 2 courses to the list
		studentCourseList.add(randomStudentCourse());
		studentCourseList.add(randomStudentCourse());
		expected = 3;
		System.out.println(String.format("Checking whether %s is %d",
				"the number of courses in the degree after calling addCourses", expected));
		// Add the list of courses by calling addCourses
		addStudentCourses.call(degree, studentCourseList);
		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();

		assertEquals(numCourses, expected, explain("Incorrect number of courses in the degree"));

		// TEST if the size of degree.myCourses does not change after attempting to add
		// a null course
		expected = 3;
		System.out.println(String.format("Checking whether %s is %d",
				"the number of courses in the degree after calling addCourse with a null course", expected));
		// Attempt to add a null course
		addStudentCourse.call(degree, (Object) null);
		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();

		assertEquals(numCourses, expected, "Expected count to stay 3 after trying to add null course.");

		// TEST if the size of degree.myCourses does not change after attempting to add
		// a null course list
		System.out.println(String.format("Checking whether %s is %d",
				"the number of courses in the degree after calling addCourses with a null parameter", expected));
		// Attempt to add a null course list
		addStudentCourses.call(degree, (Object) null);
		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();
		expected = 3;
		assertEquals(numCourses, expected, "Expected count to stay 3 after trying to add null course list.\n");

		// TEST if the size of degree.myCourses does not exceed MAX_COURSES
		System.out.println(String.format("Checking whether %s is %d", "the number of courses", expected));
		System.out.println("Adding 47 more courses...");
		System.out.println("Adding 47 more courses...");

		for (int i = 0; i < 47; i++) {
			addStudentCourse.invoke(degree, randomStudentCourse());
		}
		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();
		expected = 50;
		assertEquals(expected, numCourses, "Expected count to be 50 after trying to add null course list.");

		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();
		expected = 50;

		System.out.println(String.format("Checking whether %s does not exceed %d", "the number of courses", 50));
		System.out.println("Adding 47 more courses...");

		assertEquals(numCourses, expected, "Expected number of courses to be 50 after adding 47 more courses.");

		System.out.println("Adding one more course...");
		degreeCourses = (List<Object>) getCourses.call(degree);
		numCourses = degreeCourses.size();
		expected = 50;
		addStudentCourses.call(degree, (Object) null);

		assertEquals(numCourses, expected,
				"Expected number of courses remain 50 after attempting to add a course when the list is full");

	}

	@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V1)
	@Test
	@DisplayName("Test course count after calling addStudentCourse(s)")
	void countTests_V1() {

		assertEquals(count.read(degree), 0, "Invalid count default value");

		addStudentCourse.call(degree, randomStudentCourse());
		assertEquals(count.read(degree), 1, "Incorrect count value");

		List<Object> studentCourseList = new ArrayList<Object>();
		studentCourseList.add(randomStudentCourse());
		studentCourseList.add(randomStudentCourse());
		addStudentCourses.call(degree, studentCourseList);
		assertEquals(count.read(degree), 3, "Invalid count value");

		addStudentCourses.call(degree, (Object) null);
		assertTrue(count.matches(degree, 3),
				"Expected count to stay 3 after trying to add null courses.");
		addStudentCourse.call(degree, (Object) null);
		assertTrue(count.matches(degree, 3),
				"Expected count to stay 3 after trying to add null course.");
		System.out.println("Adding 47 more courses...");
		for (int i = 0; i < 47; i++) {
			addStudentCourse.invoke(degree, randomStudentCourse());
		}
		assertEquals(count.read(degree), 50, "invalid count value");
		System.out.println("Adding one more course...");
		addStudentCourse.call(degree, randomStudentCourse());
		assertEquals(count.read(degree), 50, 
				expectedUnchanged("count",50,"after trying to add course at max courses"));
	}

	@Test
	@DisplayName("Test degreeTitle")
	void degreeTitleTests() {
		Callable<?> setter = degreeTitle.defaultSetter();
		StringMethods getter = degreeTitle.defaultGetter();

		// Default value
		assertMatches(degreeTitle.read(degree), NO_TITLE_PATTERN, "Invalid degreeTitle default value");
		String randomTitle = randomString(8) + " " + randomString(8);

		// setDegreeTitle -> degreeTitle & getDegreeTitle
		assertGetterReturnsCurrentValue(degree, degreeTitle, getter, "degreeTitle");
		assertSetterUpdatesCurrentValue(degree, degreeTitle, setter, getter,
		randomTitle, "degreeTitle"
		);
		// setDegreeTitle(null) -> getDegreeTitle
		setter.call(degree, null);
		assertEquals(getter.call(degree), randomTitle, 
				expectedUnchanged("the return value of getDegreeTitle", randomTitle, "after trying to set it to null"));
			
		// Expecting no change in degreeTitle
		assertEquals(degreeTitle.read(degree), randomTitle, 
				expectedUnchanged("degreeTitle", randomTitle, "after trying to set it to null"));
	}

	@Test
	@DisplayName("Test titleOfThesis")
	void titleOfThesisTests() {
		Callable<?> setter = titleOfThesis.defaultSetter();
		StringMethods getter = titleOfThesis.defaultGetter();

		// Default value
		assertMatches(titleOfThesis.read(degree), NO_TITLE_PATTERN);
		String randomTitle = randomString(8) + " " + randomString(8);

		// setTitleOfThesis -> titleOfThesis & getTitleOfThesis
		assertSetterUpdatesCurrentValue(degree, titleOfThesis, setter, getter,
				randomTitle, "titleOfThesis"
		);
		assertGetterReturnsCurrentValue(degree, titleOfThesis, getter, "titleOfThesis");

		// setTitleOfThesis(null) -> getTitleOfThesis
		setter.call(degree, null);
		assertEquals(getter.call(degree), randomTitle,
				expectedUnchanged("the return value of getTitleOfThesis", randomTitle, "after trying to set it to null"));

		// Expecting no change in titleOfThesis
		assertTrue(titleOfThesis.matches(degree, randomTitle),
				expectedUnchanged("titleOfThesis", randomTitle, "after trying to set it to null"));
	}

	@Test
	@DisplayName("Test isCourseCompleted/getCredits")
	void getCreditsTests() {
		double totalCredits = 0;
		HashMap<Character, Double> byBaseMap = new HashMap<>();
		HashMap<Integer, Double> byTypeMap = new HashMap<>();

		int testCases = 16;
		// A, P, S, A, P, S, etc.
		OfInt bases = IntStream.iterate(0, i -> (i + 1) % 3)
				.map(i -> (char) "APS".charAt(i))
				.limit(testCases).iterator();
		// 0, 1, 0, 1, 0, 1, 0, 1, etc.
		OfInt types = IntStream.iterate(0, i -> Math.abs(i - 1))
				.limit(testCases).iterator();
		// 0, 1, 2, 3, 4, 5, F, A, 0, 1, 2, etc.
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
				.map(i -> i == 6 ? 'F' : i)
				.map(i -> i == 7 ? 'A' : i)
				.limit(testCases).iterator();

		Object newStudentCourse;
		char base;
		int type;
		double credits;
		int grade;
		// Last two test cases reserved for addStudentCourses
		for (int i = 0; i < testCases - 2; i++) {
			// Next test case
			base = (char) bases.next().intValue();
			type = types.nextInt();
			credits = rand.nextInt(56);
			grade = grades.nextInt();
			if (grade != 0 && grade != 'F') {
				totalCredits += credits;
			}
			newStudentCourse = createStudentCourseAndTestIsCourseCompleted(
					byBaseMap, byTypeMap, base, type, credits, grade);
			addStudentCourse.call(degree, newStudentCourse);
		}
		// Last test case (add two at the same time)
		// First
		base = (char) bases.next().intValue();
		type = types.nextInt();
		credits = rand.nextInt(56);
		grade = grades.nextInt();
		if (grade != 0 && grade != 'F') {
			totalCredits += credits;
		}
		newStudentCourse = createStudentCourseAndTestIsCourseCompleted(
				byBaseMap, byTypeMap, base, type, credits, grade);
		// Second
		base = (char) bases.next().intValue();
		type = types.nextInt();
		credits = rand.nextInt(56);
		grade = grades.nextInt();
		if (grade != 0 && grade != 'F') {
			totalCredits += credits;
		}
		Object lastStudentCourse = createStudentCourseAndTestIsCourseCompleted(
				byBaseMap, byTypeMap, base, type, credits, grade);
		// addStudentCourses()
		List<Object> studentCoursesList = new ArrayList<Object>();
		studentCoursesList.add(newStudentCourse);
		studentCoursesList.add(lastStudentCourse);
		addStudentCourses.call(degree, studentCoursesList);

		// Test

		assertEquals(getCredits.call(degree), totalCredits, "Incorrect amount of total credits");
		for (Character baseChar : byBaseMap.keySet()) {
			assertEquals(getCreditsByBase.call(degree, baseChar), byBaseMap.get(baseChar),
					explain("Credits for course base " + baseChar + " don't match"));
		}
		for (int typeInt : byTypeMap.keySet()) {
			assertEquals(byTypeMap.get(typeInt), getCreditsByType.call(degree, typeInt),
					explain("Credits for course type " + typeInt + " don't match"));
		}
	}

	private Object createStudentCourseAndTestIsCourseCompleted(HashMap<Character, Double> byBaseMap,
			HashMap<Integer, Double> byTypeMap, char base, int type, double credits, int grade) {
		boolean numericGrading = grade != 'F' && grade != 'A';
		// New Course instance
		Object newCourse = randomCourse(base, type, credits, numericGrading, null);
		// New StudentCourse instance
		Object newStudentCourse = randomStudentCourse(grade, newCourse);
		// isCourseCompleted()
		boolean completed = isCourseCompleted.call(degree, newStudentCourse);
		if (grade > 0 && grade != 'F') {
			assertTrue(completed, "Expected isCourseCompleted to return "
					+ "true for a StudentCourse object with gradeNum "
					+ (char) (grade < 6 ? '0' + grade : grade)
					+ ".");
			byBaseMap.compute(base, (b, byBase) -> (byBase == null) ? credits : byBase + credits);
			byTypeMap.compute(type, (t, byType) -> (byType == null) ? credits : byType + credits);
		} else {
			assertFalse(completed, "Expected isCourseCompleted to return "
					+ "false for a StudentCourse object with gradeNum "
					+ (char) (grade < 6 ? '0' + grade : grade));
		}
		return newStudentCourse;
	}

	@Test
	@DisplayName("Test getGPA")
	@SuppressWarnings("unchecked")
	void testGPA() {
		List<Object> optionalCoursesList = new ArrayList<>();
		List<Object> mandatoryCoursesList = new ArrayList<>();
		double optionalGrade = 0;
		double mandatoryGrade = 0;
		double totalGrade = 0;

		double optionalCount = 0;
		double mandatoryCount = 0;

		Callable<?> getterCourse = course.defaultGetter();
		IntMethods getterCourseType = courseType.defaultGetter();
		IntMethods getterGrade = gradeNum.defaultGetter();

		for (int i = 0; i < 25; i++) {
			Object newStudentCourse = randomStudentCourse();
			Object course = getterCourse.call(newStudentCourse);
			int type = getterCourseType.call(course);
			double grade = getterGrade.call(newStudentCourse);
			if (type == 0) {
				optionalCoursesList.add(newStudentCourse);
				if (isNumericGrade.invoke(course)) {
					optionalGrade += grade;
					totalGrade += grade;
					optionalCount++;
				}
			} else {
				mandatoryCoursesList.add(newStudentCourse);
				if (isNumericGrade.invoke(course)) {
					mandatoryGrade += grade;
					totalGrade += grade;
					mandatoryCount++;
				}
				
			}
		
		}
		addStudentCourses.call(degree, optionalCoursesList);
		addStudentCourses.call(degree, mandatoryCoursesList);

		List<Double> gpaOptional = (List<Double>) getGPA.call(degree, 0);
		List<Double> gpaMandatory = (List<Double>) getGPA.call(degree, 1);
		List<Double> gpaAll = (List<Double>) getGPA.call(degree, 2);
		double totalCount = optionalCount + mandatoryCount;

		System.out.println(gpaOptional);
		System.out.println(gpaMandatory);
		System.out.println(gpaAll);

		// TODO: check the implementation

		assertEquals(gpaOptional.get(0), optionalGrade, explain("Incorrect GPA value"));
		assertEquals(gpaMandatory.get(0), mandatoryGrade, explain("Incorrect GPA value"));
		assertEquals(gpaAll.get(0), totalGrade, explain("Incorrect GPA value"));
		assertEquals(gpaOptional.get(1), optionalCount, explain("Incorrect GPA value"));
		assertEquals(gpaMandatory.get(1), mandatoryCount, explain("Incorrect GPA value"));
		assertEquals(gpaAll.get(1), totalCount, explain("Incorrect GPA value"));
		assertEquals(gpaOptional.get(2), (optionalCount != 0) ? Math.round(optionalGrade / optionalCount * 100.0) / 100.0 : 0.0, explain("Incorrect GPA value"));
		assertEquals(gpaMandatory.get(2), (mandatoryCount != 0) ? Math.round(mandatoryGrade / mandatoryCount * 100.0) / 100.0 : 0.0, explain("Incorrect GPA value"));
		assertEquals(gpaAll.get(2), (totalCount != 0) ? Math.round(totalGrade / totalCount * 100.0) /  100.0 : 0.0, explain("Incorrect GPA value"));

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
			degree = paramlessDegree.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addStudentCourse.call(degree, randomStudentCourse(grades.nextInt(), regex));
				studentCourseRegexes[j] = regex.toString();
			}
			String output = captureOutput(printCourses, degree);

			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output, "in the output of printCourses", re.substring(0, 9) );
			}
		}
	}

	@Test
	@DisplayName("Test toString")
	void toStringTests() {
		int testCases = 3;
		int maxCoursesPerCase = 5;
		OfInt grades = IntStream.iterate(0, i -> i == 7 ? 0 : i + 1)
				.map(i -> i == 6 ? 'F' : i)
				.map(i -> i == 7 ? 'A' : i)
				.limit(testCases * maxCoursesPerCase).iterator();
		for (int i = 0; i < testCases; i++) {
			degree = paramlessDegree.instantiate();
			int coursesToAdd = rand.nextInt(3, maxCoursesPerCase + 1);
			String[] studentCourseRegexes = new String[coursesToAdd];
			StringBuilder regex;

			for (int j = 0; j < coursesToAdd; j++) {
				regex = new StringBuilder();
				addStudentCourse.call(degree, randomStudentCourse(grades.nextInt(), regex));
				studentCourseRegexes[j] = regex.toString();
			}
			String output = degreeToString.call(degree);
			String title = degreeTitle.get(degree);
			String thesisTitle = titleOfThesis.get(degree);
			String degreePattern = "^Degree\\[Title:\"?" + title
					+ "\"?\\(courses:" + coursesToAdd + "\\)"
					+ "Thesis title:\"?" + thesisTitle + "\"?";
			findIgnoringWhitespace(degreePattern, output, "in the return value of toString", degreePattern.substring(0, 7));


			for (String re : studentCourseRegexes) {
				findIgnoringWhitespace(re, output, "in the return value of toString", re.substring(0, 9));
			}
		}
	}
}
