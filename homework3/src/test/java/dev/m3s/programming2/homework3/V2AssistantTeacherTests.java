package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import dev.m3s.maeaettae.jreq.Callable;
import dev.m3s.maeaettae.jreq.IntMethods;
import dev.m3s.maeaettae.jreq.Quantifier;
import dev.m3s.maeaettae.jreq.Range;
import dev.m3s.maeaettae.jreq.StringMethods;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;


@TestCategories({ @TestCategory("AssistantTeacher") })
class V2AssistantTeacherTests {

	private Object assistantTeacher;
	private String fName = randomString(6);;
	private String lName = randomString(8);

	private final double randSalaryValues[] = { 1000.00, 1500.50, 1200.75, 2000.80 };
	private final double randEurosPerHourValues[] = { 10, 15, 25, 1.5, 2 };
	private final double randHoursValues[] = { 1, 3, 5, 11, 20 };
	private double randSalaryValue = randSalaryValues[rand.nextInt(0, randSalaryValues.length)];
	private double randPerHourValue = randEurosPerHourValues[rand.nextInt(0, randEurosPerHourValues.length)];
	private double randHourValue = randHoursValues[rand.nextInt(0, randHoursValues.length)];

	private Object createAssistantT(String lName, String fName, String pId, int sYear, double salary) {
		Object testAssistantT = twoParamAssistantTeacher.instantiate(lName, fName);
		setBirthDate.invoke(testAssistantT, pId);
		setStartYearEmployee.invoke(testAssistantT, sYear);
		Object monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, randSalaryValue);
		setPayment.invoke(testAssistantT, monPayment);
		return testAssistantT;
	}

	private Object createAssistantT(String lName, String fName, String pId, int sYear, double eurosPerHour,
			double hours) {
		Object testAssistantT = twoParamAssistantTeacher.instantiate(lName, fName);
		setBirthDate.call(testAssistantT, pId);
		setStartYearEmployee.call(testAssistantT, sYear);
		Object hourlyPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.call(hourlyPayment, eurosPerHour);
		setHours.call(hourlyPayment, hours);
		setPayment.invoke(testAssistantT, hourlyPayment);
		return testAssistantT;
	}

	@BeforeAll
	static void init() {
		classAssistantTeacher.require();
		twoParamAssistantTeacher.require();
	}

	@BeforeEach
	void setUp() {
		assistantTeacher = twoParamAssistantTeacher.instantiate(lName, fName);
	}

	@Test
	@DisplayName("Inheritance relations")
	void testInheritance_V2() {
		// Checking AssistantTeacher is inherits Employee and Person through Employee
		classAssistantTeacher.declaresSupertype(classEmployee).require();
		classAssistantTeacher.hasSupertype(classPerson).require();
	}

	@Test
	@DisplayName("Interface relations")
	void testInterface_V2() {
		// Checking AssistantTeacher implements Teacher interface
		classAssistantTeacher.hasSupertype(interfacePayment).require();
		classAssistantTeacher.declaresSupertype(interfaceTeacher).require();
	}

	@Test
	@DisplayName("All required attributes should be private and non-static")
	void requiredAttributesNonStaticPrivate_V2() {
		coursesAssistanT.require();
		classAssistantTeacher.hasField().isPrivate().require(Quantifier.FOR_ALL);
		classAssistantTeacher.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test Constructor")
	void assistantTConstructor_V2() {

		// Check the constructor sets lname and fname are set correctly
		System.out.println("Check the constructor sets the last name and the first name");
		System.out.println(String.format("Calling new AssistantTeacher( %s, %s )", lName, fName));
		assertEquals(firstName.read(assistantTeacher), fName, explain("Invalid firstName value"));
		assertEquals(lastName.read(assistantTeacher), lName, explain("Invalid lastName value"));
	}

	@Test
	@DisplayName("empId is of the assistant teacher is in the specified format and range")
	void empIdTests_V2() {

		// Check the constuctor generates a the empId within the ranges
		// public static final int MIN_EMP_ID = 2001;
		// public static final int MAX_EMP_ID = 3000;
		// empId should be of the form “OY_ASSISTANT_”
		System.out.println("Checking the employee id is set in the specified format");
		String idString = getIdString.call(assistantTeacher);
		int idNumber = Integer.valueOf(idString.substring(idString.length() - 4));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber && idNumber <= 3000,
				explain("Invalid employeeId")
				.expected("a value between 2001 and 3000").but("was: " + idNumber));
	}

	@Test
	@DisplayName("Test getRandomId")
	void testEmpIdGeneratedRandomly_V2() {
		// Allowed values for employee IDs are 2001 - 3000
		Object assistantTeacher1 = twoParamAssistantTeacher.instantiate(lName, fName);
		String idString1 = getIdString.call(assistantTeacher1);
		Pattern idPattern1 = Pattern.compile("OY_ASSISTANT_2\\d{3}");
		assertMatches(idString1, idPattern1,
				String.format("Expected to find a string matching regular expression%n%s%n"
						+ "in the return value of toString:%n%s",
						idPattern1, idString1));
		int idNumber1 = Integer.valueOf(idString1.substring(13));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber1 && idNumber1 <= 3000,
				String.format("Employee id should be between 2001 and 3000. It was %d.", idNumber1));

		Object assistantTeacher2 = twoParamAssistantTeacher.instantiate(lName, fName);
		String idString2 = getIdString.call(assistantTeacher2);
		Pattern idPattern2 = Pattern.compile("OY_ASSISTANT_2\\d{3}");
		assertMatches(idString2, idPattern2,
				String.format("Expected to find a string matching regular expression%n%s%n"
						+ "in the return value of toString:%n%s",
						idPattern2, idString2));
		int idNumber2 = Integer.valueOf(idString2.substring(13));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber2 && idNumber2 <= 3000,
				explain("Invalid employeeId")
				.expected("a value between 2001 and 3000").but("was: " + idNumber2));
		System.out.println("Checking randomly generated id's are different");
		assertFalse(idNumber1 == idNumber2,
				String.format("Expected %s and %s to be different", idString1, idString2));
	}

	@Test
	@DisplayName("Test firstName")
	void firstNameTests_V2() {
		assistantTeacher = twoParamAssistantTeacher.instantiate(null, (Object) null);
		
		Callable<?> setter = firstName.defaultSetter();
		StringMethods getter = firstName.defaultGetter();
		// Default value
		assertMatches(firstName.read(assistantTeacher), NO_NAME_PATTERN,
				"Default value of 'firstName' is not 'No name'"
		);		// setFirstName(null) -> firstName

		// setFirstName -> firstName
		String randomName = randomString(8);
		System.out.println("Checking the setFirstName method sets the first name of the employee");
		assertSetterUpdatesCurrentValue(assistantTeacher, firstName, setter, getter, randomName, "firstName");

		// getFirstName
		assertGetterReturnsCurrentValue(assistantTeacher, firstName, getter, "firstName");
		// firstName -> getFirstName
		randomName = randomString(8);
		assertGetterReturnsNewValue(assistantTeacher, firstName, getter, randomName, "firstName");

		System.out.println("Checking the employee first name can not be set to null");
		assertEquals(firstName.read(assistantTeacher), randomName,
				expectedUnchanged("firstName", randomName, "after trying to set firstName to null"));
	}

	@Test
	@DisplayName("Test lastName")
	void lastNameTests_V2() {
		assistantTeacher = twoParamAssistantTeacher.instantiate(null, (Object) null);
		Callable<?> setter = lastName.defaultSetter();
		StringMethods getter = lastName.defaultGetter();
		// Default value
		assertMatches(lastName.read(assistantTeacher), NO_NAME_PATTERN,
				"Default value of 'lastName' is not 'No name'"
		);		// setFirstName(null) -> firstName

		// setLastName -> lastName
		String randomName = randomString(8);
		System.out.println("Checking the setFirstName method sets the lastName of the employee");
		assertSetterUpdatesCurrentValue(assistantTeacher, lastName, setter, getter, randomName, "lastName");

		// getLastName
		assertGetterReturnsCurrentValue(assistantTeacher, lastName, getter, "lastName");
		// lastName -> getLastName
		randomName = randomString(8);
		assertGetterReturnsNewValue(assistantTeacher, lastName, getter, randomName, "lastName");

		System.out.println("Checking the employee lastName can not be set to null");
		assertEquals(lastName.read(assistantTeacher), randomName,
				expectedUnchanged("lastName", randomName, "after trying to set lastName to null"));
	}

	@Test
	@DisplayName("Test birthDate")
	void birthDateTests_V2() {
		StringMethods getter =personBirthDate.defaultGetter();

		// Default value
		assertMatches(personBirthDate.read(assistantTeacher), NOT_AVAILABLE_PATTERN,
				"Invalid default value of birthDate");
		// setBirthDate(null) -> birthDate
		assertMatches(setBirthDate.call(assistantTeacher, (Object) null),
				NO_CHANGE_PATTERN, "personBirthDate should not be settable to null"
		);

		// setBirthDate -> birthDate
		String randomId = makePersonID();
		String expectedBD = personIdToBirthDate(randomId);
		// note that in StringMethods.returns() expected value is handled as regex
		// (a stricter check might be needed in the future)
		assertEquals(setBirthDate.call(assistantTeacher, randomId), expectedBD, 
				explain("Invalid return value of setBirthDate"));
		assertEquals(personBirthDate.read(assistantTeacher), expectedBD,
				explain("Invalid birthDate value"));

		// getBirthDate
		assertGetterReturnsCurrentValue(assistantTeacher, personBirthDate, getter, "birthDate");

		// invalid id
		String invalidId = makePersonIDWithIncorrectChecksum();
		String validId = personIdToBirthDate(makePersonID());

		assertMatches(setBirthDate.call(assistantTeacher, invalidId), NO_CHANGE_PATTERN,
				"Incorrect return value of setBirthDate");
		assertEquals(personBirthDate.read(assistantTeacher), expectedBD,
				expectedUnchanged("birthDate", expectedBD, "after trying to set it with invalid ID: " + invalidId)
		);
		// invalid id (random string)
		String randString = randomString(11);
				assertMatches(setBirthDate.call(assistantTeacher, randString), NO_CHANGE_PATTERN,
				"Invalid return value of setBirthDate"
		);

		assertFalse(personBirthDate.matches(assistantTeacher, randString),
				explain("Setting 'birthDate' to " + randString + " shouldn't be possible."));
		
		setBirthDate.call(assistantTeacher, validId);
		setBirthDate.call(assistantTeacher, null);
		assertFalse(personBirthDate.matches(assistantTeacher, null),
				expectedUnchanged("personBirthDate", validId, "after trying to set birthDate to null"));
	}

	@Test
	@DisplayName("Test startYear")
	void startYearTests_V1() {
		Callable<?> setter = empStartYear.defaultSetter();
		IntMethods getter = empStartYear.defaultGetter();
		// Check default value is current year
		System.out.println("Checking the start year has the current year as the default value");
		assertEquals(empStartYear.read(assistantTeacher), yearNow, 
				explain("Invalid default value of startYear"));

		// setStartYear -> getStartYear
		// Check setStartYear sets the startYear attribute
		int randYear = rand.nextInt(minYear, yearNow);
		assertGetterReturnsSetValue(assistantTeacher, setter, getter, randYear, "startYear");

		// Check the startYear is within the specified range 2000 < startYear <
		// currentYear
		System.out.println("Checking the start year default value is the current year");
		assertTrue(empStartYear.matchesWithin(setStartYearEmployee, assistantTeacher, Range.closed(minYear, yearNow)),
						explain("Invalid startYear value")
						.expected(String.format("value betweem [%d, %d]", minYear, yearNow))
						.but("was: " + empStartYear.read(assistantTeacher)));

		// startYear -> getStartYear
		assertGetterReturnsNewValue(assistantTeacher, empStartYear, getter, rand.nextInt(2001, yearNow), "startYear");

	}

	@Test
	@DisplayName("Test payment")
	void paymentTests_V2() {

		// Checking if setPayment sets the payment with MonthlyPayment with salary in
		// the example output
		System.out.println("Case 1:");
		double salaryValue = 756.85;
		System.out.println("Setting payment to monthly payment with salary " + salaryValue);
		Object monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		System.out.println("Checking getPayment returns the MontlyPayment object");
		assertTrue(getPayment.returnsAfter(setPayment, assistantTeacher, monPayment),
				explain("getPayment should return MonthyPayment object"));

		// Checking if setPayment sets the payment with MonthlyPayment with random
		// salary value
		System.out.println("Case 2:");
		salaryValue = randSalaryValue;
		System.out.println("Setting payment to monthly payment with salary " + salaryValue);
		monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		System.out.println("Checking getPayment returns the MontlyPayment object");
		assertTrue(getPayment.returnsAfter(setPayment, assistantTeacher, monPayment),
				explain("getPayment should return MonthyPayment object"));

		// Checking if setPayment sets the payment in HourBasedPayment with values in
		// the example output
		System.out.println("Case 3:");
		double eurosPerHour = 3.5;
		double hours = 11;
		System.out.println("Setting payment to hour based payment with "
				+ eurosPerHour + " euros per hour and "
				+ hours + " hours.");
		Object hourlyPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.invoke(hourlyPayment, eurosPerHour);
		setHours.invoke(hourlyPayment, hours);
		System.out.println("Checking getPayment returns the HourBasedPayment object");
		assertTrue(getPayment.returnsAfter(setPayment, assistantTeacher, hourlyPayment),
				explain("getPayment should return HourBasedPayment object"));


		// Checking if setPayment sets the payment in HourBasedPayment with random
		// values
		System.out.println("Case 4:");
		eurosPerHour = randPerHourValue;
		hours = randHourValue;
		System.out.println("Setting payment to hour based payment with "
				+ eurosPerHour + " euros per hour and "
				+ hours + " hours.");
		hourlyPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.invoke(hourlyPayment, eurosPerHour);
		setHours.invoke(hourlyPayment, hours);
		System.out.println("Checking getPayment returns the HourBasedPayment object");
		assertTrue(getPayment.returnsAfter(setPayment, assistantTeacher, hourlyPayment),
				explain("getPayment should return HourBasedPayment object"));

		// Checking that setPayment can't set null value
		System.out.println("Case 5:");
		setPayment.call(assistantTeacher, (Object) null);
		assertFalse(payment.matchesAfter(payment.defaultSetter(), assistantTeacher, null),
				"Setting 'Payment' to null shouldn't be possible.");

	}

	@Test
	@DisplayName("Test calculatePayment")
	void calculatePaymentTests_V2() {

		// Checking calculatePayment returns correct amount when assistant teacher has
		// monthly payment with example salary value
		System.out.println("Case 1:");
		double salaryValue = 756.85;
		System.out.println("Checking calculatePayment returns correct amount when assistant teacher has monthly payment"
				+ " with salary value " + salaryValue);
		Object monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		setPayment.invoke(assistantTeacher, monPayment);
		assertEquals(calculatePayment.call(assistantTeacher), salaryValue,
				explain(String.format(
						"Expected calculatePayment returns %.2f when assistant teacher has monthly payment with salary %.2f",
						salaryValue, salaryValue)));

		// Checking calculatePayment returns correct amount when assistant teacher has
		// monthly payment with random salary value
		System.out.println("Case 2:");
		salaryValue = randSalaryValue;
		System.out.println("Checking calculatePayment returns correct amount when assistant teacher has monthly payment"
				+ " with salary value " + salaryValue);
		monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		setPayment.invoke(assistantTeacher, monPayment);
		assertEquals(calculatePayment.call(assistantTeacher), salaryValue, 
				explain(String.format(
						"Expected calculatePayment returns %.2f when assistant teacher has monthly payment with salary %.2f",
						salaryValue, salaryValue)));

		// Checking calculatePayment returns correct amount when assistant teacher has
		// hour-based salary with values in the example output
		System.out.println("Case 3:");
		double eurosPerHour = 3.5;
		double hours = 11;
		System.out.println(
				"Checking calculatePayment returns correct amount when assistant teacher has hour-based payment "
						+ "with " + eurosPerHour + " euros per hour"
						+ " and " + hours + " hours.");
		Object hbPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.invoke(hbPayment, eurosPerHour);
		setHours.invoke(hbPayment, hours);
		setPayment.invoke(assistantTeacher, hbPayment);
		assertEquals(calculatePayment.call(assistantTeacher), eurosPerHour * hours, 
				explain(String.format(
						"Expected calculatePayment returns %.2f when assistant teacher has hour-based payment with %.2f euros per hour and %.2f hours",
						eurosPerHour * hours, eurosPerHour, hours)));

		// Checking calculatePayment returns correct amount when assistant teacher has
		// hour-based salary with random values
		System.out.println("Case 4:");
		eurosPerHour = randPerHourValue;
		hours = randHourValue;
		System.out.println(
				"Checking calculatePayment returns correct amount when assistant teacher has hour-based payment "
						+ "with " + eurosPerHour + " euros per hour"
						+ " and " + hours + " hours.");
		hbPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.invoke(hbPayment, eurosPerHour);
		setHours.invoke(hbPayment, hours);
		setPayment.invoke(assistantTeacher, hbPayment);
		assertEquals(calculatePayment.call(assistantTeacher), eurosPerHour * hours,
				explain(String.format(
						"Expected calculatePayment returns %.2f when assistant teacher has hour-based payment with %.2f euros per hour and %.2f hours",
						eurosPerHour * hours, eurosPerHour, hours)));
	}

	@Test
	@DisplayName("Test courses")
	@SuppressWarnings("unchecked")
	void coursesTests_V2() {

		// Default array length
		// Check the courses list is empty when the
		List<Object> returnedCourses = (List<Object>) coursesAssistanT.read(assistantTeacher);
		int length = 0;
		for (; length < returnedCourses.size(); length++) {
			if (returnedCourses.get(length) == null) {
				break;
			}
		}
		System.out.println("Case1:");
		System.out.println("Checking the courses list empty when the object is created.");
		assertTrue(length == 0, explain("Expected the inital course list to be empty").expected("course list length to be 0").but("was: " + length));

		List<Object> coursesList = new ArrayList<>();

		Object course1 = sevenParamsCourse.instantiate("More basic studies", 223344, 'A', MANDATORY, 1, 50.50, false);
		Object desCourse1 = allParamDesignatedCourse.instantiate(course1, false, 2023);

		Object course2 = sevenParamsCourse.instantiate("Even more basic studies", 556667, 'A', OPTIONAL, 3, 50.00,
				false);
		Object desCourse2 = allParamDesignatedCourse.instantiate(course2, false, 2023);

		Object course3 = sevenParamsCourse.instantiate("Extra master studies", 666666, 'S', OPTIONAL, 5, 8.00, false);
		Object desCourse3 = allParamDesignatedCourse.instantiate(course3, false, 2022);

		Object course4 = sevenParamsCourse.instantiate("Final master studies", 888888, 'S', MANDATORY, 5, 18.00, false);
		Object desCourse4 = allParamDesignatedCourse.instantiate(course4, false, 2023);

		System.out.println("Case 2:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing one designated course:");
		// Add one
		coursesList.add(desCourse1);
		setCoursesAssistantT.call(assistantTeacher, coursesList);

		// Access the array returned by getCourses
		// @formatter:off
		returnedCourses = (List<Object>) coursesAssistanT.read(assistantTeacher); // Array reference might have changed
		                                                                          // (defensive copying)
		// @formatter:on
		System.out.println("Accessing courses list");
		assertDesignatedCoursesEqual(desCourse1, returnedCourses.get(0));

		System.out.println("Case 3:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing 4 designated courses in the example output:");
		// Add three other courses
		coursesList.add(desCourse2);
		coursesList.add(desCourse3);
		coursesList.add(desCourse4);
		setCoursesAssistantT.call(assistantTeacher, coursesList);

		// Access the array returned by getCourses
		// @formatter:off
		returnedCourses = (List<Object>) coursesAssistanT.read(assistantTeacher); // Array reference might have changed
		                                                                          // (defensive copying)
		// @formatter:on
		System.out.println("Accessing courses list");
		assertDesignatedCoursesEqual(desCourse1, returnedCourses.get(0));
		assertDesignatedCoursesEqual(desCourse2, returnedCourses.get(1));
		assertDesignatedCoursesEqual(desCourse3, returnedCourses.get(2));
		assertDesignatedCoursesEqual(desCourse4, returnedCourses.get(3));

		System.out.println("Case 4:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing randomly generated designated courses");
		List<Object> randomCoursesList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList.add(randomDesignatedCourse());
		}
		setCoursesAssistantT.call(assistantTeacher, randomCoursesList);

		// Access the array returned by getCourses
		// @formatter:off
		returnedCourses = (List<Object>) coursesAssistanT.read(assistantTeacher); // Array reference might have changed
		                                                                          // (defensive copying)
		// @formatter:on
		System.out.println("Accessing courses list");
		System.out.println(randomCoursesList);
		for (int i = 0; i < 5; i++) {
			assertDesignatedCoursesEqual(randomCoursesList.get(i), returnedCourses.get(i));
		}
		System.out.println("Case 5:");
		System.out.println("Checking the courses list cannot be set to null");
		setCoursesAssistantT.call(assistantTeacher, (Object) null);

		assertEquals(coursesAssistanT.read(assistantTeacher), randomCoursesList, explain("invalid courses value")
				.expected("a list of designated courses").but("was: null"));
	}

	@Test
	@DisplayName("Test getEmployeeIdString")
	void getEmployeeIdStringTest() {
		System.out
				.println("Checking getEmployeeIdString returns the correct string for the empId for AssistantTeacher");
		assertEquals(getEmployeeIdString.call(assistantTeacher), "OY_ASSISTANT_",
				explain("Invalid return value of getEmployeeIdString"));
	}

	@Test
	@DisplayName("Test getCourses")
	@SuppressWarnings("unchecked")
	void getCoursesTests_V2() {

		// Default array length
		// Check the courses list is empty when the
		// ? Should these be used somewhere?
		List<Object> teachersCourses = (List<Object>) coursesAssistanT.read(assistantTeacher);
		int length = getCoursesAssistantT.call(assistantTeacher).length();
		System.out.println("Case1:");
		System.out.println("Checking the return value when the courses list is empty");
		assertEquals(length, 0, "getCourses should return empty string when the courses list is empty.");

		List<Object> coursesList = new ArrayList<>();

		Object course1 = sevenParamsCourse.instantiate("More basic studies", 223344, 'A', MANDATORY, 1, 50.50, false);
		Object desCourse1 = allParamDesignatedCourse.instantiate(course1, false, 2023);

		Object course2 = sevenParamsCourse.instantiate("Even more basic studies", 556667, 'A', OPTIONAL, 3, 50.00,
				false);
		Object desCourse2 = allParamDesignatedCourse.instantiate(course2, false, 2023);

		Object course3 = sevenParamsCourse.instantiate("Extra master studies", 666666, 'S', OPTIONAL, 5, 8.00, false);
		Object desCourse3 = allParamDesignatedCourse.instantiate(course3, false, 2022);

		Object course4 = sevenParamsCourse.instantiate("Final master studies", 888888, 'S', MANDATORY, 5, 18.00, false);
		Object desCourse4 = allParamDesignatedCourse.instantiate(course4, false, 2023);

		System.out.println("Case 2:");
		System.out.println(
				"Checking the output of getCourses after calling setCourses with a list containing one designated course:");
		// Add one designated course
		coursesList.add(desCourse3);
		setCoursesAssistantT.call(assistantTeacher, coursesList);

		// Access the courses list and return String of getCourses
		String output = getCoursesAssistantT.call(assistantTeacher);

		// Build the expected output pattern
		StringBuilder coursesPatternSB = new StringBuilder();
		for (Object ds : coursesList) {

			Object course = getCourseDesignatedCourse.invoke(ds);

			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String designatedCoursePatternStr = "course=\\["
					+ getCourseCode.invoke(course)
					+ "\\("
					+ String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
					+ "cr\\)" + ","
					+ "\"" + getCourseName.invoke(course) + "\""
					+ "."
					+ desCourseTypeStr + ","
					+ "period" + ":" + getPeriod.invoke(course)
					+ "." + "\\]" + ","
					+ "year" + "=" + getYearDesignatedCourse.invoke(ds)
					+ "\\]";
			coursesPatternSB.append(designatedCoursePatternStr);
			// \n is not needed since there is  after the last item
			// coursesPatternSB.append("\\n");
		}
		System.out.println(output);
		findIgnoringWhitespace(coursesPatternSB.toString(), output, "in the return value of toString", coursesPatternSB.substring(0, 16));

		System.out.println("Case 3:");
		System.out.println(
				"Checking the output of getCourses after calling setCourses with a list containing 4 designated courses:");
		// Add three other courses
		coursesList.add(desCourse2);
		coursesList.add(desCourse3);
		coursesList.add(desCourse4);
		setCoursesAssistantT.call(assistantTeacher, coursesList);

		// Return String of getCourses
		String multipleCourseOutput = getCoursesAssistantT.call(assistantTeacher);

		// Build the expected output pattern
		StringBuilder multipleCoursesPatternSB = new StringBuilder();
		boolean firstCourse = true;
		for (Object ds : coursesList) {
			if (firstCourse) {
				firstCourse = false;
			} else {
				multipleCoursesPatternSB.append("\\n");
			}
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String designatedCoursePatternStr = "\\[course=\\["
					+ getCourseCode.invoke(course)
					+ "\\("
					+ String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
					+ "cr\\)" + ","
					+ "\"" + getCourseName.invoke(course) + "\""
					+ "."
					+ desCourseTypeStr + ","
					+ "period" + ":" + getPeriod.invoke(course)
					+ "." + "\\]" + ","
					+ "year" + "=" + getYearDesignatedCourse.invoke(ds)
					+ "\\]";
			multipleCoursesPatternSB.append(designatedCoursePatternStr);
		}
		findIgnoringWhitespace(multipleCoursesPatternSB.toString(), multipleCourseOutput,
				"in the return value of toString", multipleCoursesPatternSB.substring(0, 16));

		System.out.println("Case 4:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing randomly generated designated courses");
		List<Object> randomCoursesList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList.add(randomDesignatedCourse());
		}
		setCoursesAssistantT.call(assistantTeacher, randomCoursesList);

		// Return String of getCourses
		String randomCoursesOutput = getCoursesAssistantT.call(assistantTeacher);
		// Build the expected output pattern
		StringBuilder randomCoursesPatternSB = new StringBuilder();
		firstCourse = true;
		for (Object ds : randomCoursesList) {
			Object course = getCourseDesignatedCourse.invoke(ds);
			if (firstCourse) {
				firstCourse = false;
			} else {
				randomCoursesPatternSB.append("\\n");
			}
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String designatedCoursePatternStr = "\\[course=\\["
					+ getCourseCode.invoke(course)
					+ "\\("
					+ String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
					+ "cr\\)" + ","
					+ "\"" + getCourseName.invoke(course) + "\""
					+ "."
					+ desCourseTypeStr + ","
					+ "period" + ":" + getPeriod.invoke(course)
					+ "." + "\\]" + ","
					+ "year" + "=" + getYearDesignatedCourse.invoke(ds)
					+ "\\]";
			randomCoursesPatternSB.append(designatedCoursePatternStr);
		}
		// System.out.println(randomCoursesOutput);
		// System.out.println(multipleCoursesPatternSB.toString());
		findIgnoringWhitespace(randomCoursesPatternSB.toString(), randomCoursesOutput, "in the return value of toString", randomCoursesPatternSB.substring(0, 16));
	}

	@Test
	@DisplayName("Test toString")
	void toStringTests_V2() {

		// Test case 1, example output from the task description
		// Employee output
		// Employee id: OY_ASSISTANT_2604
		// First name: Goofy, Last name: The Dog
		// Birthdate: 141200A2315
		// Start year: 2023
		// Payment: 3.5 eurosPerHour 11.0 hours
		Object testAssistantTeacher1 = createAssistantT("The Dog", "Goofy", "141200A2315", 2023, 3.5, 11.0);
		List<Object> coursesList = new ArrayList<>();

		Object course1 = sevenParamsCourse.instantiate("More basic studies", 223344, 'A', MANDATORY, 1, 50.50, false);
		Object desCourse1 = allParamDesignatedCourse.instantiate(course1, false, 2023);

		Object course2 = sevenParamsCourse.instantiate("Even more basic studies", 556667, 'A', OPTIONAL, 3, 50.00,
				false);
		Object desCourse2 = allParamDesignatedCourse.instantiate(course2, false, 2023);

		Object course3 = sevenParamsCourse.instantiate("Extra master studies", 666666, 'S', OPTIONAL, 5, 8.00, false);
		Object desCourse3 = allParamDesignatedCourse.instantiate(course3, false, 2022);

		Object course4 = sevenParamsCourse.instantiate("Final master studies", 888888, 'S', MANDATORY, 5, 18.00, false);
		Object desCourse4 = allParamDesignatedCourse.instantiate(course4, false, 2023);
		coursesList.add(desCourse1);
		coursesList.add(desCourse2);
		coursesList.add(desCourse3);
		coursesList.add(desCourse4);
		setCoursesAssistantT.invoke(testAssistantTeacher1, coursesList);

		// TODO: How to make the test pass with both ',' and '.' as the decimal
		// separator.
		String output1 = testAssistantTeacher1.toString();
		System.out.println("Case 1:");
		System.out.println("Checking the output of the toString method is in the specified format for example output");
		StringBuilder outputSB = new StringBuilder();

		String testAssistantTeacher1Pattern = "^Teacher id:"
				+ getIdString.invoke(testAssistantTeacher1)
				+ "\"? First [Nn]ame: " + getFirstName.invoke(testAssistantTeacher1)
				+ ", Last [Nn]ame: " + getLastName.invoke(testAssistantTeacher1)
				+ "\"? Birthdate: " + getBirthDate.invoke(testAssistantTeacher1)
				+ "\"? Salary: "
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(testAssistantTeacher1)) + "";
		outputSB.append(testAssistantTeacher1Pattern);

		outputSB.append("\\nAssistant for courses:");

		StringBuilder multipleCoursesPatternSB = new StringBuilder();
		for (Object ds : coursesList) {
			multipleCoursesPatternSB.append("\\n");
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String designatedCoursePatternStr = "\\[course=\\["
					+ getCourseCode.invoke(course)
					+ "\\("
					+ String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
					+ "cr\\)" + ","
					+ "\"" + getCourseName.invoke(course) + "\""
					+ "."
					+ desCourseTypeStr + ","
					+ "period" + ":" + getPeriod.invoke(course)
					+ "." + "\\]" + ","
					+ "year" + "=" + getYearDesignatedCourse.invoke(ds)
					+ "\\]";
			multipleCoursesPatternSB.append(designatedCoursePatternStr);
		}
		outputSB.append(multipleCoursesPatternSB.toString());
		output1 = testAssistantTeacher1.toString();
		findIgnoringWhitespace(outputSB.toString(), output1, "in the return value of toString", outputSB.substring(0, 29));

		// Test cases, random data with hourly payment
		System.out.println("Case 2:");
		System.out.println("Checking the output of the toString method is in the specified format for random data");

		Object randomAssistantTeacher = createAssistantT(randomString(8), randomString(6), makePersonID(),
				rand.nextInt(minYear, yearNow), randPerHourValue, randHourValue);
		List<Object> randomCoursesList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList.add(randomDesignatedCourse());
		}
		setCoursesAssistantT.invoke(randomAssistantTeacher, randomCoursesList);
		String output2 = randomAssistantTeacher.toString();
		System.out.println(output2);
		StringBuilder output2SB = new StringBuilder();
		String randomAssistantTeacherPattern = "^Teacher id:"
				+ getIdString.invoke(randomAssistantTeacher) + "\\n"
				+ "\"? First [Nn]ame: " + getFirstName.invoke(randomAssistantTeacher)
				+ ", Last [Nn]ame: " + getLastName.invoke(randomAssistantTeacher) + "\\n"
				+ "\"? Birthdate: " + getBirthDate.invoke(randomAssistantTeacher) + "\\n"
				+ "\"? Salary: "
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(randomAssistantTeacher)) + "";
		output2SB.append(randomAssistantTeacherPattern);
		output2SB.append("\\nAssistant for courses:");

		multipleCoursesPatternSB = new StringBuilder();
		for (Object ds : randomCoursesList) {
			multipleCoursesPatternSB.append("\\n");
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String designatedCoursePatternStr = "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ " \\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
					+ "cr\\)" + ", "
					+ "\"" + getCourseName.invoke(course) + "\""
					+ ". "
					+ desCourseTypeStr + ", "
					+ "period" + ": " + getPeriod.invoke(course)
					+ "." + "\\]" + ", "
					+ "year" + "=" + getYearDesignatedCourse.invoke(ds)
					+ "\\]";
			multipleCoursesPatternSB.append(designatedCoursePatternStr);
		}
		output2SB.append(multipleCoursesPatternSB.toString());
		output2 = randomAssistantTeacher.toString();
		//findIgnoringWhitespace(output2SB.toString(), output2, "in the return value of toString");

		// Test cases, random data with monthly payment
		System.out.println("Case 3:");
		System.out.println(
				"Checking the output of the toString method is in the specified format for random data for monthly payment");

		Object randomAssistantTeacher2 = createAssistantT(randomString(8), randomString(6), makePersonID(),
				rand.nextInt(minYear, yearNow), randSalaryValue);
		List<Object> randomCoursesList2 = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList2.add(randomDesignatedCourse());
		}
		setCoursesAssistantT.invoke(randomAssistantTeacher2, randomCoursesList2);
		String output3 = randomAssistantTeacher2.toString();
		System.out.println(output3);
		StringBuilder output3SB = new StringBuilder();
		String randomAssistantTeacherPattern2 = "^Teacher id:"
				+ getIdString.invoke(randomAssistantTeacher2) + "\\n"
				+ "\"? First [Nn]ame: " + getFirstName.invoke(randomAssistantTeacher2)
				+ ", Last [Nn]ame: " + getLastName.invoke(randomAssistantTeacher2) + "\\n"
				+ "\"? Birthdate: " + getBirthDate.invoke(randomAssistantTeacher2) + "\\n"
				+ "\"? Salary: "
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(randomAssistantTeacher2)) + "";
		output3SB.append(randomAssistantTeacherPattern2);
		output3SB.append("\\nAssistant for courses:");

		multipleCoursesPatternSB = new StringBuilder();
		for (Object ds : randomCoursesList2) {
			multipleCoursesPatternSB.append("\\n");
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String designatedCoursePatternStr = "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ " \\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
					+ "cr\\)" + ", "
					+ "\"" + getCourseName.invoke(course) + "\""
					+ ". "
					+ desCourseTypeStr + ", "
					+ "period" + ": " + getPeriod.invoke(course)
					+ "." + "\\]" + ", "
					+ "year" + "=" + getYearDesignatedCourse.invoke(ds)
					+ "\\]";
			multipleCoursesPatternSB.append(designatedCoursePatternStr);
		}
		output3SB.append(multipleCoursesPatternSB.toString());
		output3 = randomAssistantTeacher2.toString();
		//findIgnoringWhitespace(output3SB.toString(), output3, "in the return value of toString");
	}
}
