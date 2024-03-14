package dev.m3s.programming2.homework2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import dev.m3s.maeaettae.jreq.*;

import static dev.m3s.programming2.homework2.H2Matcher.*;
import static org.junit.jupiter.api.Assertions.fail;

public class H2 {

	static final String ENV_VAR = "CI_COMMIT_TAG";
	static final String V1 = "[Hh]omework[.\\-_]?2[.\\-_][Vv]1.*";
	static final String V2 = "[Hh]omework[.\\-_]?2[.\\-_][Vv]2.*";

	// *** REQUIRED PACKAGE ***
	static Packages location = Packages.requirePackage(
			H2.class.getPackageName());

	// *** REQUIRED CLASSES ***
	static Classes classStudent = location.hasClass()
			.hasName("Student");
	static Classes classCourse = location.hasClass()
			.hasName("Course");
	static Classes classDegree = location.hasClass()
			.hasName("Degree");
	static Classes classPersonID = location.hasClass()
			.hasName("PersonID");
	static Classes classStudentCourse = location.hasClass()
			.hasName("StudentCourse");

	// *** REQUIRED CONSTRUCTORS ***
	// PersonID default constructor
	static Constructors paramlessPersonID = classPersonID.hasConstructor();

	// Course constructors
	static Constructors paramlessCourse = classCourse.hasConstructor();
	static Constructors sevenParamsCourse = classCourse.hasConstructor()
			.hasParameters(String.class, int.class)
			.hasParameter(List.of(Character.class, char.class))
			.hasParameters(int.class, int.class, double.class, boolean.class);
	static Constructors copyCourse = classCourse.hasConstructor()
			.hasParameter(classCourse);

	// StudentCourse constructors
	static Constructors paramlessStudentCourse
			= classStudentCourse.hasConstructor();
	static Constructors threeParamsStudentCourse
			= classStudentCourse.hasConstructor()
			.hasParameters(classCourse, int.class, int.class);

	// Degree default constructor
	static Constructors paramlessDegree = classDegree.hasConstructor();

	// Student constructors
	static Constructors paramlessStudent = classStudent.hasConstructor();
	static Constructors twoParamStudent = classStudent.hasConstructor()
			.hasParameters(String.class, String.class);

	// *** REQUIRED ATTRIBUTES ***
	// PersonID attribute
	static StringFields birthDate = classPersonID.stringField()
			.hasName("birthDate", "birth[Dd]ate")
			.isPrivate();

	// Course attributes
	static StringFields name = classCourse.stringField()
			.hasName("name")
			.isPrivate();

	static StringFields courseCode = classCourse.stringField()
			.hasName("courseCode")
			.isPrivate();

	static Fields courseBase = classCourse.hasField()
			.hasName("courseBase")
			.hasType(List.of(Character.class, char.class))
			.isPrivate();

	static IntFields courseType = classCourse.intField()
			.hasName("courseType")
			.isPrivate();

	static IntFields period = classCourse.intField()
			.hasName("period")
			.isPrivate();

	static DoubleFields credits = classCourse.doubleField()
			.hasName("credits")
			.isPrivate();

	static BooleanFields numericGrade = classCourse.booleanField()
			.hasName("numericGrade")
			.isPrivate();

	// StudentCourse attributes
	static Fields course = classStudentCourse.hasField()
			.hasName("course")
			.hasType(classCourse)
			.isPrivate();

	static IntFields gradeNum = classStudentCourse.intField()
			.hasName("gradeNum")
			.isPrivate();

	static IntFields yearCompleted = classStudentCourse.intField()
			.hasName("yearCompleted")
			.isPrivate();

	// Degree attributes
	static IntFields MAX_COURSES = classDegree.intField()
			.isStatic()
			.hasName("MAX_COURSES")
			.isPrivate();

	static IntFields count = classDegree.intField()
			.hasName("count")
			.isPrivate();

	static StringFields degreeTitle = classDegree.stringField()
			.hasName("degreeTitle")
			.isPrivate();

	static StringFields titleOfThesis = classDegree.stringField()
			.hasName("titleOfThesis")
			.isPrivate();

	static Fields myCourses = classDegree.hasField()
			.hasName("myCourses")
			.hasType(Types.asArray(classStudentCourse))
			.isPrivate();

	// Student attributes
	static StringFields firstName = classStudent.stringField()
			.hasName("firstName")
			.isPrivate();

	static StringFields lastName = classStudent.stringField()
			.hasName("lastName")
			.isPrivate();

	static IntFields id = classStudent.intField()
			.hasName("id")
			.isPrivate();

	static IntFields startYear = classStudent.intField()
			.hasName("startYear")
			.isPrivate();

	static IntFields graduationYear = classStudent.intField()
			.hasName("graduationYear")
			.isPrivate();

	static Fields degree = classStudent.hasField()
			.hasName("degree")
			.hasType(classDegree)
			.isPrivate();

	static StringFields studentBirthDate = classStudent.stringField()
			.hasName("birthDate", "birth[Dd]ate")
			.isPrivate();

	// *** REQUIRED METHODS ***
	// PersonID methods
	static StringMethods setPersonID = classPersonID.stringMethod()
			.hasName("setPersonID", "setPersonI[Dd]")
			.hasParameter(String.class);

	static BooleanMethods checkPersonIDNumber = classPersonID.booleanMethod()
			.isPrivate()
			.hasName("checkPersonIDNumber", "checkPersonI[Dd]Number")
			.hasParameter(String.class);

	static BooleanMethods checkLeapYear = classPersonID.booleanMethod()
			.isPrivate()
			.hasName("checkLeapYear")
			.hasParameter(int.class);

	static BooleanMethods checkValidCharacter = classPersonID.booleanMethod()
			.isPrivate()
			.hasName("checkValidCharacter")
			.hasParameter(String.class);

	static BooleanMethods checkBirthdate = classPersonID.booleanMethod()
			.isPrivate()
			.hasName("checkBirthdate", "checkBirth[Dd]ate")
			.hasParameter(String.class);

	// Course methods
	static StringMethods getCourseTypeString = classCourse.stringMethod()
			.hasName("getCourseTypeString");

	static Methods setCourseCode = classCourse.hasMethod()
			.hasReturnType(void.class)
			.hasName("setCourseCode")
			.hasParameter(int.class)
			.hasParameter(List.of(Character.class, char.class));

	static Methods setCredits = classCourse.hasMethod()
			.isPrivate()
			.hasReturnType(void.class)
			.hasName("setCredits")
			.hasParameter(double.class);

	static StringMethods courseToString = classCourse.stringMethod()
			.hasName("toString");

	// StudentCourse methods
	static Methods setGrade = classStudentCourse.hasMethod()
			.isProtected()
			.hasName("setGrade")
			.hasParameter(int.class);

	static BooleanMethods checkGradeValidity
			= classStudentCourse.booleanMethod()
			.isPrivate()
			.hasName("checkGradeValidity")
			.hasParameter(int.class);

	static BooleanMethods isPassed = classStudentCourse.booleanMethod()
			.hasName("isPassed");

	static IntMethods getYear = classStudentCourse.intMethod()
			.hasName("getYear");

	static Methods setYear = classStudentCourse.hasMethod()
			.hasReturnType(void.class)
			.hasName("setYear")
			.hasParameter(int.class);

	static StringMethods studentCourseToString
			= classStudentCourse.stringMethod()
			.hasName("toString");

	// Degree methods
	static Methods getCourses = classDegree.hasMethod()
			.hasReturnType(Types.asArray(classStudentCourse))
			.hasName("getCourses");

	static Methods addStudentCourses = classDegree.hasMethod()
			.hasName("addStudentCourses")
			.hasParameter(Types.asArray(classStudentCourse));

	static BooleanMethods addStudentCourse = classDegree.booleanMethod()
			.hasName("addStudentCourse")
			.hasParameter(classStudentCourse);

	static DoubleMethods getCreditsByBase = classDegree.doubleMethod()
			.hasName("getCreditsByBase")
			.hasParameter(List.of(Character.class, char.class));

	static DoubleMethods getCreditsByType = classDegree.doubleMethod()
			.hasName("getCreditsByType")
			.hasParameter(int.class);

	static DoubleMethods getCredits = classDegree.doubleMethod()
			.hasName("getCredits");

	static BooleanMethods isCourseCompleted = classDegree.booleanMethod()
			.isPrivate()
			.hasName("isCourseCompleted")
			.hasParameter(classStudentCourse);

	static Methods printCourses = classDegree.hasMethod()
			.hasReturnType(void.class)
			.hasName("printCourses");

	static StringMethods degreeToString = classDegree.stringMethod()
			.hasName("toString");

	// Student methods
	static Methods setDegreeTitle = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("setDegreeTitle")
			.hasParameter(String.class);

	static BooleanMethods addCourse = classStudent.booleanMethod()
			.hasName("addCourse")
			.hasParameter(classStudentCourse);

	static IntMethods addCourses = classStudent.intMethod()
			.hasName("addCourses")
			.hasParameter(Types.asArray(classStudentCourse));

	static Methods studentPrintCourses = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("printCourses");

	static Methods printDegree = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("printDegree");

	static Methods setTitleOfThesis = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("setTitleOfThesis")
			.hasParameter(String.class);

	static StringMethods setBirthDate = classStudent.stringMethod()
			.hasName("setBirthDate", "setBirth[Dd]ate")
			.hasParameter(String.class);

	static BooleanMethods hasGraduated = classStudent.booleanMethod()
			.hasName("hasGraduated");

	static BooleanMethods canGraduate = classStudent.booleanMethod()
			.isPrivate()
			.hasName("canGraduate");

	static IntMethods getStudyYears = classStudent.intMethod()
			.hasName("getStudyYears");

	static IntMethods getRandomId = classStudent.intMethod()
			.isPrivate()
			.hasName("getRandomId", "getRandomI[Dd]");

	static StringMethods studentToString = classStudent.stringMethod()
			.hasName("toString");

	// Non-default setters/getters
	static StringMethods setGraduationYear = classStudent.stringMethod()
			.hasName("setGraduationYear")
			.hasParameters(int.class);

	static final IntMethods getStartYear = classStudent.intMethod()
			.hasName("getStartYear", "get[Ss]tartYear");

	static final Methods setStartYear = classStudent.hasMethod()
			.hasName("setStartYear", "set[Ss]tartYear")
			.hasParameters(int.class);

	static final Pattern NO_NAME_PATTERN = Pattern.compile("\"?No\\s+name\"?",
			Pattern.CASE_INSENSITIVE);
	static final Pattern NO_TITLE_PATTERN = Pattern.compile("\"?No\\s+title\"?",
			Pattern.CASE_INSENSITIVE);
	static final Pattern NOT_AVAILABLE_PATTERN = Pattern.compile(
			"\"?Not\\s+available[.!\"]*", Pattern.CASE_INSENSITIVE);
	static Pattern NO_CHANGE_PATTERN = Pattern.compile("\"?No\\s+change[.!\"]*",
			Pattern.CASE_INSENSITIVE);
	static final Pattern INVALID_BIRTHDAY_PATTERN = Pattern.compile(
			"\"?Invalid\\s+birthday[.!\"]*", Pattern.CASE_INSENSITIVE);
	static final Pattern INCORRECT_CHECK_MARK_PATTERN = Pattern.compile(
			"\"?Incorrect\\s+check\\s*mark[.!\"]*", Pattern.CASE_INSENSITIVE);
	static Pattern CHECK_REQUIRED_CREDITS_PATTERN = Pattern.compile(
			"\"?Check\\s+(the\\s+)?amount\\s+of\\s+required\\s+credits[.!\"]*",
			Pattern.CASE_INSENSITIVE);
	static final Pattern CHECK_GRADUATION_YEAR_PATTERN = Pattern.compile(
			"\"?Check\\s+(the\\s+)?graduation\\s+year[.!\"]*",
			Pattern.CASE_INSENSITIVE);
	static final Pattern OK_PATTERN = Pattern.compile("\"?Ok[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	static String CHECK_REQUIRED_CREDITS = "Check amount of required credits";
	static String CHECK_GRADUATION_YEAR = "Check graduation year";
	static String OK = "Ok";

	static final String seeConsoleOutput = "(<== see Console output for context)";

	static int minYear = 2001;
	static int yearNow = LocalDate.now()
			.getYear();

	// Credits
	static final double MIN_CREDITS = 0.0;
	static final double MAX_CREDITS = 300.0;
	static final double BACHELOR_CREDITS = 180.0;
	static final double MASTER_CREDITS = 120.0;
	static final double BACHELOR_OPTIONAL = 30.0;
	static final double MASTER_OPTIONAL = 70.0;
	static final double BACHELOR_MANDATORY = 150.0;
	static final double MASTER_MANDATORY = 50.0;
	static final double MAX_COURSE_CREDITS = 55.0;
	static final int MIN_PERIOD = 1;
	static final int MAX_PERIOD = 5;
	static final int BACHELOR_TYPE = 0;
	static final int MASTER_TYPE = 1;
	static final int OPTIONAL = 0;
	static final int MANDATORY = 1;
	static final int ALL = 2;

	static Random rand = new Random();

	static Object randomCourse(char base, int type, double credits,
			boolean numericGrading,
			StringBuilder expectedString, boolean silent) {
		String randName = randomString(10);
		int randCode = rand.nextInt(1, 1000000);
		int randPeriod = rand.nextInt(1, 6);

		if (expectedString != null) {
			// Example:
			// [811104P ( 5.00 cr), "Programming 1". Mandatory, period: 1.]
			String regex = String.format(Locale.ROOT,
					"\\[%s \\( %.2f cr\\), \"%s\". %s, period: %d.\\]",
					String.valueOf(randCode) + base, credits, randName,
					type == 0 ? "Optional" : "Mandatory", randPeriod);
			regex = regex.replaceAll("\\.", Matcher.quoteReplacement("\\.?"));
			expectedString.append(regex);
		}

		if (silent) {
			return sevenParamsCourse.instantiate(randName, randCode, base, type,
					randPeriod, credits, numericGrading);
		}
		return sevenParamsCourse.create(randName, randCode, base, type,
				randPeriod, credits, numericGrading);
	}

	static Object randomCourse(double credits) {
		char randBase = "APS".charAt(rand.nextInt(3));
		int randType = rand.nextInt(2);
		return randomCourse(randBase, randType, credits, true, null, false);
	}

	static Object randomCourse(boolean numericGrading,
			StringBuilder expectedString, boolean silent) {
		char randBase = "APS".charAt(rand.nextInt(3));
		int randType = rand.nextInt(2);
		double randCredits = rand.nextInt(56);
		return randomCourse(randBase, randType, randCredits, numericGrading,
				expectedString, silent);
	}

	static Object randomCourse() {
		return randomCourse(rand.nextBoolean(), null, false);
	}

	static Object randomStudentCourse(int grade, Object course) {
		return randomStudentCourse(grade, course, null, null, false);
	}

	static Object randomStudentCourse(int grade, StringBuilder expectedString,
			boolean silent) {
		boolean numericGrading = grade >= 0 && grade <= 5;
		StringBuilder expectedCourseString = new StringBuilder();
		Object newCourse = randomCourse(numericGrading, expectedCourseString,
				silent);
		return randomStudentCourse(grade, newCourse, expectedCourseString,
				expectedString, silent);
	}

	static Object randomStudentCourse(boolean numericGrading, boolean silent) {
		char randGrade = numericGrading
				? (char) rand.nextInt(6)
				: rand.nextBoolean() ? 'A' : 'F';
		return randomStudentCourse(randGrade, null, silent);
	}

	static Object randomStudentCourse(boolean silent) {
		return randomStudentCourse(rand.nextBoolean(), silent);
	}

	private static Object randomStudentCourse(int grade, Object course,
			StringBuilder expectedCourseString,
			StringBuilder expectedString,
			boolean silent) {
		int randYear = rand.nextInt(minYear, yearNow + 1);
		if (expectedString != null && expectedCourseString != null) {
			// Example:
			// {Course.toString()} Year: 2021, Grade: "Not graded".]
			expectedString.append("\\[?");
			expectedString.append(expectedCourseString);
			String regex = String.format(" Year: %d, Grade: %s\\.?\\] ",
					randYear, grade == 0
							? "\"?Not graded\"?"
							: (char) (grade < 6 ? '0' + grade : grade));
			expectedString.append(regex);
		}
		if (silent) {
			return threeParamsStudentCourse.instantiate(course, grade,
					randYear);
		}
		return threeParamsStudentCourse.create(course, grade, randYear);
	}

	static String randomString(int length) {
		return rand.ints(length, 'a', 'a' + 26)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint,
						StringBuilder::append)
				.toString();
	}

	static void assertCoursesEqual(Object course1, Object course2) {
		System.out.println(
				"Checking whether the two course objects are equal:");

		System.out.print(" => Course 2: ");
		String name2 = name.read(course2);
		System.out.print(" => Course 1: ");
		assertTrue(name.matches(course1, name2));

		System.out.print(" => Course 2: ");
		String code2 = courseCode.read(course2);
		System.out.print(" => Course 1: ");
		assertTrue(courseCode.matches(course1, code2));

		System.out.print(" => Course 2: ");
		Character base2 = (Character) courseBase.read(course2);
		System.out.print(" => Course 1: ");
		assertTrue(courseBase.matches(course1, base2));

		System.out.print(" => Course 2: ");
		int period2 = period.read(course2);
		System.out.print(" => Course 1: ");
		assertTrue(period.matches(course1, period2));

		System.out.print(" => Course 2: ");
		double credits2 = credits.read(course2);
		System.out.print(" => Course 1: ");
		assertTrue(credits.matches(course1, credits2));

		System.out.print(" => Course 2: ");
		boolean numericGrade2 = numericGrade.read(course2);
		System.out.print(" => Course 1: ");
		assertTrue(numericGrade.matches(course1, numericGrade2));

		System.out.println("=> The course objects are equal.\n");
	}

	static void assertStudentCoursesEqual(Object studentCourse1,
			Object studentCourse2) {
		System.out.println(
				"Checking whether the two student course objects are equal:");

		System.out.print("=> StudentCourse 2: ");
		Object course2 = course.read(studentCourse2);
		System.out.print("=> StudentCourse 1: ");
		assertCoursesEqual(course.read(studentCourse1), course2);

		System.out.print("=> StudentCourse 2: ");
		int gradeNum2 = gradeNum.read(studentCourse2);
		System.out.print("=> StudentCourse 1: ");
		assertTrue(gradeNum.matches(studentCourse1, gradeNum2));

		System.out.print("=> StudentCourse 2: ");
		int year2 = yearCompleted.read(studentCourse2);
		System.out.print("=> StudentCourse 1: ");
		assertTrue(yearCompleted.matches(studentCourse1, year2));

		System.out.println("=> The student course objects are equal.\n");
	}

	static boolean isValidGraduationYear(int year) {
		return minYear <= year && year <= yearNow;
	}

	static void setAllConditions(Object student, int sYear) {
		setStartYear.call(student, sYear);
		setCredits(student, 180);
		setBachelorsThesis(student);
	}

	static void setAllConditionsV2(Object student, int sYear) {
		setStartYear.call(student, sYear);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);
		setThesis(student, 0);
		setThesis(student, 1);
	}

	static List<Object> setCredits(Object student, double targetCredits) {
		int target = (int) targetCredits;
		int accumulatedCredits = 0;
		List<Object> studentCourses = new ArrayList<>();
		while (accumulatedCredits < targetCredits) {
			int creditsNow = Math.min(target - accumulatedCredits,
					rand.nextInt(35, 56));
			Object sc = randomStudentCourse(rand.nextInt(1, 6), randomCourse(creditsNow));
			studentCourses.add(sc);
			addCourse.call(student, sc);
			accumulatedCredits += creditsNow;
		}
		return studentCourses;
	}

	static List<Object> setCredits(Object student, int degreeNo, double targetCredits) {
		int target = (int) targetCredits;
		int accumulatedCredits = 0;
		List<Object> studentCourses = new ArrayList<>();
		while (accumulatedCredits < target) {
			int creditsNow = Math.min(target - accumulatedCredits, rand.nextInt(35, 56));
			Object sc = randomStudentCourse(rand.nextInt(1, 6), randomCourse(creditsNow));
			studentCourses.add(sc);
			V2StudentTests.addCourse.call(student, degreeNo, sc);
			accumulatedCredits += creditsNow;
		}
		return studentCourses;
	}

	static void setBachelorsThesis(Object student) {
		setTitleOfThesis.call(student, greatTitle());
	}

	static void setThesis(Object student, int i) {
		V2StudentTests.setTitleOfThesis.call(student, i, greatTitle());
	}

	static void graduate(Object student, int sYear, int gYear) {
		setStartYear.call(student, sYear);
		setCredits(student, 180);
		setBachelorsThesis(student);
		setGraduationYear.call(student, gYear);
	}

	static void graduateV2(Object student, int sYear, int gYear) {
		setStartYear.call(student, sYear);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);
		setThesis(student, 0);
		setThesis(student, 1);
		setGraduationYear.call(student, gYear);
	}

	static String makePersonIDWithIncorrectChecksum() {
		String personId = makePersonID();
		char correctCC = personId.charAt(10);
		String validCCs = "0123456789ABCDEFHJKLMNPRSTUVWXY";
		char incorrectCC = validCCs.charAt(
				(validCCs.indexOf(correctCC) + rand.nextInt(1, 31)) % 31);
		return personId.substring(0, 10) + incorrectCC;
	}

	static String makePersonID() {
		return makePersonID("-+A".charAt(rand.nextInt(3)));
	}

	static String makePersonID(char centurySign) {
		String dd, mm, yy;
		char checksum;

		dd = pad(rand.nextInt(28) + 1, 2);
		mm = pad(rand.nextInt(12) + 1, 2);

		if (centurySign == 'A') {
			int yyNow = (LocalDate.now()
					.getYear() % 1000 % 100);
			yy = pad(rand.nextInt(yyNow), 2);
		} else {
			yy = pad(rand.nextInt(100), 2);
		}

		String individualNumber = pad(rand.nextInt(898) + 2, 3);
		int remainder = Integer.parseInt(dd + mm + yy + individualNumber) % 31;
		checksum = "0123456789ABCDEFHJKLMNPRSTUVWXY".charAt(remainder);
		return dd + mm + yy + centurySign + individualNumber + checksum;
	}

	static String pad(int paddee, int amount) {
		StringBuilder padded = new StringBuilder(Integer.toString(paddee));
		for (int i = padded.length(); i < amount; i++) {
			padded.insert(0, '0');
		}
		return padded.toString();
	}

	static String personIdToBirthDate(String id) {
		String yy = switch (id.charAt(6)) {
			case '+' -> "18";
			case '-' -> "19";
			default -> "20";
		};
		return id.substring(0, 2) + '.' + id.substring(2, 4) + '.' + yy
				+ id.substring(4, 6);
	}

	static String greatTitle() {
		// Create a mutable ArrayList for shuffling
		List<String> words = new ArrayList<>(
				List.of("The", "Bachelor", "Masters", "Thesis", "Title", "How",
						"To", "Survive", "Happy", "Ending", "New", "Exciting",
						"Purpose", "Of", "Life", "Christmas", "Most",
						"Wonderful", "Time", "Year", "Dreaming"));
		Collections.shuffle(words);
		return words.stream()
				.limit(rand.nextInt(3) + 3)
				.reduce("", (a, b) -> a + ' ' + b)
				.stripLeading();
	}

	static String captureOutput(Callable<?> method, Object instance) {
		// start capture
		PrintStream stdOut = System.out;
		ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(newOut));
		method.invoke(instance);
		// end capture
		System.setOut(stdOut);
		return newOut.toString();
	}

	static <T> void assertGetterReturnsCurrentValue(Object receiver,
			Property<T> attribute,
			Callable<T> getter,
			String variableName) {
		System.out.println(addBorders(
				"Comparing the return value of the getter to the "
						+ "value\nof the attribute (ignoring the visibility modifier):"));
		// Read the value reflectively, bypassing visibility,
		// and compare it to the getter's return value
		T expected = attribute.read(receiver);
		if (expected instanceof String) {
				assertTrue(getter.returns(receiver, expected), 
				explain("Expected the return value of getter method of %s to match value of the attribute",variableName)
					.expected("\"" + expected + "\"").but("was: \"" + getter.invoke(receiver).toString() + "\""));
		}else{
			assertTrue(getter.returns(receiver, expected), 
				explain("Expected the return value of getter method of %s to match value of the attribute",variableName)
					.expected(expected.toString()).but("was: " + getter.invoke(receiver).toString()));
		}
	}
	static <T> void assertGetterReturnsNewValue(Object receiver,
			Property<T> attribute,
			Callable<T> getter,
			T newValue,
			String variableName) {
		System.out.println(addBorders("""
				Comparing the return value of the getter to the value
				of the attribute after setting a new value directly
				(ignoring the visibility modifier):"""));
		// Set the value reflectively,
		// bypassing setter and visibility,
		// and compare it to the getter's return value
		String expected = getter.invoke(receiver).toString();
		attribute.write(receiver, newValue);
		if (newValue instanceof String) {
				assertTrue(getter.returns(receiver, newValue),
			explain("Expected the return value of getter method of %s to match value of the attribute", variableName)
					.expected("\"" + newValue.toString() + "\"").but("was: \"" + expected + "\""));
		}else{
			assertTrue(getter.returns(receiver, newValue),
			explain("Expected the return value of getter method of %s to match value of the attribute", variableName)
					.expected(newValue.toString()).but("was: " + expected));
		}
				
	}

	static <T> void assertSetterUpdatesCurrentValue(Object receiver,
			Property<T> attribute,
			Callable<?> setter,
			Callable<?> getter,
			T newValue,
			String variableName) {
		System.out.println(addBorders("""
				Comparing the value of the attribute before and after
				setting a new value using the setter (ignoring the
				attribute's visibility modifier)"""));
		T currentValue = attribute.read(receiver);
		if (currentValue.equals(newValue)) {
			System.out.println("""
					The current value and the new value are the same.
					The setter should update the current value.
					~> The test will be inconclusive...""");
		}
		// call setter and read the value reflectively
		String getterValue = getter.invoke(receiver).toString();
		setter.call(receiver, newValue);

		// different error feedback based on variable datatype
		if (newValue instanceof String) {
			assertTrue(attribute.matches(receiver, newValue),
					explain("Expected the setter method of %s to set the value of the attribute.", variableName)
						.expected("\"" + newValue.toString() + "\"").but("was: \"" + getterValue + "\""));
		}
		else{
			assertTrue(attribute.matches(receiver, newValue),
					explain("Expected the setter method of %s to set the value of the attribute.", variableName)
						.expected(newValue.toString()).but("was: " + getterValue));
		}
	}

	static <T> void assertGetterReturnsSetValue(Object receiver,
			Callable<?> setter,
			Callable<T> getter,
			T newValue,
			String variableName) {
		System.out.println(addBorders(
				"Comparing the return value of the getter to the "
						+ "value\nset by the setter:"));
		T currentValue = getter.call(receiver);
		if (currentValue.equals(newValue)) {
			System.out.println("""
					The current value and the new value are the same.
					The setter should update the current value.
					~> The test will be inconclusive...""");
		}
		// set the value via setter and compare it to the getter's return value
		setter.call(receiver, newValue);
		String getterValue = getter.invoke(receiver).toString();
		
		if (newValue instanceof String) {
			assertTrue(getter.returns(receiver, newValue),
				explain("Expected the getter method of %s to return the value set by the setter.", variableName)
					.expected("\"" + newValue.toString() + "\"").but("was: \"" + getterValue + "\""));
		}else{
			assertTrue(getter.returns(receiver, newValue),
					explain("Expected the getter method of %s to return the value set by the setter.", variableName)
						.expected(newValue.toString()).but("was: " + getterValue));
		}
	}

	static <T> FailureMessage expectedUnchanged(String name, T expected, String after) {
	// If T is Double, format the numbers to 1 decimal place
	if (expected instanceof Double) {
		return explain(String.format("Expected %s to stay ⟨%.1f⟩ %s", name, expected, after));
	}
		return explain(String.format("Expected %s to stay ⟨%s⟩ %s", name, expected, after));
	}

	// Makes the message blocks stand out
	static String addBorders(String message) {
		return "-----------------------------------------------------\n"
				+ message + "\n"
				+ "-----------------------------------------------------";
	}

	static void findIgnoringWhitespace(String expectedPattern, String actual,
			String location, String identifyPattern) {
		String shownPattern = expectedPattern.replaceAll(Pattern.quote("\\n"),
				"\n")
				.replaceAll(Pattern.quote(
						"\\p{Space}*"), "")
				.replaceAll(Pattern.quote(
						"\\p{Space}+"), " ")
				.replaceAll(Pattern.quote(
						"\\p{Space}"), " ");
		
		String actualPattern = shownPattern.replaceAll("\\s", "");
		Pattern compiled = Pattern.compile(actualPattern,
				Pattern.CASE_INSENSITIVE);
		String actualActual = actual.replaceAll("\\s", "");
		String regexExplanation = RegexMatcher.regexExplanation(compiled);
		Pattern identifyCompiled = Pattern.compile(identifyPattern, Pattern.CASE_INSENSITIVE);

		Matcher matcher = identifyCompiled.matcher(actualActual);

		// identify the part of the string that is being matced to the pattern
		// this is required for the findErrorIndex() method to work
		assertTrue(matcher.find(), explain(String.format("Invalid output string. Expected a string matching the pattern %s to be found in the output\n"
									+ "( * * * * * spaces, tabs, newlines and case are ignored * * * * * )"
								+ "%n⮱%n\"%s\"%n⮳%n%s:%n*%n* * * * * *%n\"%s\"%n"
								+ "* * * * * *%n*" + (regexExplanation.isBlank()
										? ""
										: ("%nRegular expression explanation:%n"
												+ regexExplanation)),
						identifyPattern, identifyPattern, location, actual)).asIs());
		
		actualActual = actualActual.substring(matcher.start());
		matcher = compiled.matcher(actualActual);
		boolean matches = matcher.find();
		String errorString = "";

		if (!matches){ // if not matching, iterate the output string one char at time in order to find out the error point
			int errorIndex = findErrorIndex(compiled, actualActual);

			// move the index by the amount of removed whitespace before the error index
			// for getting the error location in the original string with whitespaces
			int startIndex = 0;
			int index;
			while (startIndex < actual.length()) {
				index = actual.indexOf(" ", startIndex);
				int newIndex = actual.indexOf("\n", startIndex);
				if (newIndex != -1 && (index == -1 || newIndex < index)) {
					index = newIndex;
				}
				if (index != -1) {
					startIndex = index + 1;
					if (index < errorIndex) {
						errorIndex++;
					}
				} else {
					break;
				}
			}

			errorString = actual.substring(0, errorIndex);
			System.out.println("Error is caused by the next character after: " + "\"" + errorString + "\"\n");
		}

		assertTrue(matches,
				explain(String.format(
				"Invalid output string. The string was correct until the character after: \"" + errorString + "\"\n" +
						"%nExpected to find a string matching the following regular expression%n"
								+ "( * * * * * spaces, tabs, newlines and case are ignored * * * * * )"
								+ "%n⮱%n\"%s\"%n⮳%n%s:%n*%n* * * * * *%n\"%s\"%n"
								+ "* * * * * *%n*" + (regexExplanation.isBlank()
										? ""
										: ("%nRegular expression explanation:%n"
												+ regexExplanation)),
						shownPattern, location, actual)).asIs());
	}

	private static int findErrorIndex(Pattern pattern, String input) {
		System.out.println("Error found in the printed output string. Locating the error by reading the output string one character at a time");
        Matcher matcher = pattern.matcher(input);
		// iterate through the input one character at a time to find the point where string no longer matching pattern
        for (int i = 0; i < input.length(); i++) {
            matcher = matcher.region(0, i);
            if (matcher.matches() || !matcher.hitEnd()) {
				return i-1;
            }
        }
        return 0;
    }

		// Run singular checkstyle check
	static Object[] runCheck(String checkName, List<File> files) throws Exception {

        for (File currentFile : files) {
            if (!currentFile.exists()) {
                fail(String.format("File %s not found", currentFile.getPath()));
            }
        }
		
		boolean checkPassed = true;
        String errorMessage = "";

		ByteArrayOutputStream sos = new ByteArrayOutputStream();
		AuditEventDefaultFormatter formatter = new AuditEventDefaultFormatter();
        AuditListener listener = new DefaultLogger(sos, OutputStreamOptions.CLOSE, sos, OutputStreamOptions.CLOSE, formatter);

		DefaultConfiguration configuration = new DefaultConfiguration("CustomConfig");
		DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
		configuration.addChild(treeWalker);
		treeWalker.addChild(new DefaultConfiguration(checkName));

		Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(configuration);
        checker.addListener(listener);

		if (checker.process(files) > 0){
			checkPassed = false;
			errorMessage = extractErrorMessage(sos.toString());
		}

        checker.destroy();

		return new Object[]{checkPassed, errorMessage};
    }

	// Extract the error message from the output string using regex
	static String extractErrorMessage(String output) {
        Pattern startPattern = Pattern.compile("\\[ERROR\\]");
        Pattern endPattern = Pattern.compile("\\[([^\\]]+)\\]");

		Matcher matcher = startPattern.matcher(output);
		String modifiedText = matcher.replaceAll("");
		matcher = endPattern.matcher(output);
		modifiedText = matcher.replaceAll("");

		String errorMessage = "";
		
        String[] lines = modifiedText.split("\n");

		boolean multiple_errors = lines.length > 3;

		if (multiple_errors) {
			errorMessage += "Multiple errors found:";
		}

		for (int i = 1; i < lines.length - 1; i++) {
			String line = lines[i];
			String[] parts = line.split(": ", 2);
			if (parts.length > 1) {
				errorMessage += "\n" + parts[1];
			}
		}

        return errorMessage;
    }
}
