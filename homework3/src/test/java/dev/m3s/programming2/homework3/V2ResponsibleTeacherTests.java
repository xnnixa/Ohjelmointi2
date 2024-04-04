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


@TestCategories({ @TestCategory("ResponsibleTeacher") })
class V2ResponsibleTeacherTests {

	private Object responsibleTeacher;
	private String fName = randomString(6);;
	private String lName = randomString(8);

	private final double randSalaryValues[] = { 1000.00, 1500.50, 1200.75, 2000.80 };
	private final double randEurosPerHourValues[] = { 10, 15, 25, 1.5, 2 };
	private final double randHoursValues[] = { 1, 3, 5, 11, 20 };
	private double randSalaryValue = randSalaryValues[rand.nextInt(0, randSalaryValues.length)];
	private double randPerHourValue = randEurosPerHourValues[rand.nextInt(0, randEurosPerHourValues.length)];
	private double randHourValue = randHoursValues[rand.nextInt(0, randHoursValues.length)];

	private Object createResponsibleT(String lName, String fName, String pId, int sYear, double salary) {
		Object testResponsibleT = twoParamResponsibleTeacher.instantiate(lName, fName);
		setBirthDate.invoke(testResponsibleT, pId);
		setStartYearEmployee.invoke(testResponsibleT, sYear);
		Object monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, randSalaryValue);
		setPayment.invoke(testResponsibleT, monPayment);
		return testResponsibleT;
	}

	private Object createResponsibleT(String lName, String fName, String pId, int sYear, double eurosPerHour,
			double hours) {
		Object testResponsibleT = twoParamResponsibleTeacher.instantiate(lName, fName);
		setBirthDate.call(testResponsibleT, pId);
		setStartYearEmployee.call(testResponsibleT, sYear);
		// setSalary.invoke(testResponsibleT, salary);
		Object hourlyPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.call(hourlyPayment, eurosPerHour);
		setHours.call(hourlyPayment, hours);
		setPayment.invoke(testResponsibleT, hourlyPayment);
		return testResponsibleT;
	}

	@BeforeAll
	static void init() {
		classResponsibleTeacher.require();
		twoParamResponsibleTeacher.require();
	}

	@BeforeEach
	void setUp() {
		responsibleTeacher = twoParamResponsibleTeacher.instantiate(lName, fName);
	}

	@Test
	@DisplayName("Inheritance relations")
	void testInheritence_V2() {
		// Checking ResponsibleTeacher is inherits Employee and Person through Employee
		classResponsibleTeacher.declaresSupertype(classEmployee).require();
		classResponsibleTeacher.hasSupertype(classPerson).require();
	}

	@Test
	@DisplayName("Interface relations")
	void testInterface_V2() {
		// Checking ResponsibleTeacher implements Teacher interface
		classResponsibleTeacher.hasSupertype(interfacePayment).require();
		classResponsibleTeacher.declaresSupertype(interfaceTeacher).require();
	}

	@Test
	@DisplayName("All required attributes should be private and non-static")
	void requiredAttributesNonStaticPrivate_V2() {
		coursesResponsibleT.require();
		classResponsibleTeacher.hasField().isPrivate().require(Quantifier.FOR_ALL);
		classResponsibleTeacher.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test Constructor")
	void responsibleTConstructor_V2() {

		// Check the constructor sets lname and fname are set correctly
		System.out.println("Check the constructor sets the last name and the first name");
		System.out.println(String.format("Calling new ResponsibleTeacher( %s, %s )", lName, fName));
		assertEquals(firstName.read(responsibleTeacher), fName, explain("Incorrect firstName value"));
		assertEquals(lastName.read(responsibleTeacher), lName, explain("Incorrect lastName value"));
	}

	@Test
	@DisplayName("empId is of the responsible teacher is in the specified format and range")
	void empIdTests_V2() {

		// Check the constuctor generates a the empId within the ranges
		// public static final int MIN_EMP_ID = 2001;
		// public static final int MAX_EMP_ID = 3000;
		// empId should be of the form “OY_TEACHER_”
		System.out.println("Checking the employee id is set in the specified format");
		String idString = getIdString.call(responsibleTeacher);
		Pattern idPattern = Pattern.compile("OY_TEACHER_2\\d{3}");
		System.out.println(idString);
		assertMatches(idString, idPattern,
				String.format("Expected to find a string matching regular expression%n%s%n"
						+ "in the return value of toString:%n%s",
						idPattern, idString));
		int idNumber = Integer.valueOf(idString.substring(11));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber && idNumber <= 3000,
				String.format("Employee id should be between 2001 and 3000. It was %d.", idNumber));
	}

	@Test
	@DisplayName("Test getRandomId")
	void testEmpIdGeneratedRandomly_V2() {
		// Allowed values for employee IDs are 2001 - 3000
		Object responsibleTeacher1 = twoParamResponsibleTeacher.instantiate(lName, fName);
		String idString1 = getIdString.call(responsibleTeacher1);
		Pattern idPattern1 = Pattern.compile("OY_TEACHER_2\\d{3}");
		assertMatches(idString1, idPattern1,
				String.format("Expected to find a string matching regular expression%n%s%n"
						+ "in the return value of toString:%n%s",
						idPattern1, idString1));
		int idNumber1 = Integer.valueOf(idString1.substring(11));

		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber1 && idNumber1 <= 3000,
				String.format("Employee id should be between 2001 and 3000. It was %d.", idNumber1));

		Object responsibleTeacher2 = twoParamResponsibleTeacher.instantiate(lName, fName);
		String idString2 = getIdString.call(responsibleTeacher2);
		Pattern idPattern2 = Pattern.compile("OY_TEACHER_2\\d{3}");
		assertMatches(idString2, idPattern2,
				String.format("Expected to find a string matching regular expression%n%s%n"
						+ "in the return value of toString:%n%s",
						idPattern2, idString2));
		int idNumber2 = Integer.valueOf(idString2.substring(11));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber2 && idNumber2 <= 3000,
				String.format("Employee id should be between 2001 and 3000. It was %d.", idNumber2));
		System.out.println("Checking randomly generated id's are different");
		assertFalse(idNumber1 == idNumber2,
				String.format("Expected %s and %s to be different", idString1, idString2));


		
	}

	@Test
	@DisplayName("Test firstName")
	void firstNameTests_V2() {
		Callable<?> setter = firstName.defaultSetter();
		StringMethods getter = firstName.defaultGetter();
		responsibleTeacher = twoParamResponsibleTeacher.instantiate(null, (Object) null);
		// Default value
		assertMatches(firstName.read(responsibleTeacher), NO_NAME_PATTERN,
				"Default value of 'firstName' is not 'No name'"
		);		
		// setFirstName(null) -> firstName
		System.out.println("Checking the employee first name can not be set to null");
		assertFalse(firstName.matchesAfter(setter, responsibleTeacher, null),
				"setFirstName method should not set the 'firstName' to null");

		// setFirstName -> firstName
		System.out.println("Checking the setFirstName method sets the first name of the employee");
		String randomName = randomString(8);
		assertSetterUpdatesCurrentValue(responsibleTeacher, firstName, setter, getter, randomName, "firstName");

		// getFirstName
		assertGetterReturnsCurrentValue(responsibleTeacher, firstName, getter, "firstName");
		// firstName -> getFirstName
		assertGetterReturnsNewValue(responsibleTeacher, firstName, getter, randomString(8), "firstName");
	}

	@Test
	@DisplayName("Test lastName")
	void lastNameTests_V2() {
		Callable<?> setter = lastName.defaultSetter();
		StringMethods getter = lastName.defaultGetter();
		responsibleTeacher = twoParamResponsibleTeacher.instantiate(null, (Object) null);
		// Default value
		assertMatches(lastName.read(responsibleTeacher), NO_NAME_PATTERN,
				"Default value of 'lastName' is not 'No name'"
		);		
		// setLastName(null) -> lastName
		System.out.println("Checking the employee last name can not be set to null");
		assertFalse(lastName.matchesAfter(setter, responsibleTeacher, null),
				"setLastName method should not set the 'lastName' to null");

		// setLastName -> lastName
		System.out.println("Checking the setLastName method sets the last name of the employee");
		String randomName = randomString(8);
		assertSetterUpdatesCurrentValue(responsibleTeacher, lastName, setter, getter, randomName, "lastName");

		// getLastName
		assertGetterReturnsCurrentValue(responsibleTeacher, lastName, getter, "lastName");
		// lastName -> getLastName
		assertGetterReturnsNewValue(responsibleTeacher, lastName, getter, randomString(8), "lastName");
	}

	@Test
	@DisplayName("Test birthDate")
	void birthDateTests_V2() {
		Callable<?> setter = personBirthDate.defaultSetter();
		StringMethods getter = personBirthDate.defaultGetter();

		// Default value
		assertMatches(personBirthDate.read(responsibleTeacher), NOT_AVAILABLE_PATTERN, "Invalid default birthDate value");
		// setBirthDate(null) -> birthDate
		assertMatches(setBirthDate.call(responsibleTeacher, (Object) null),
				NO_CHANGE_PATTERN,  "Setting 'birthDate' to null shouldn't be possible."
		);

		assertFalse(personBirthDate.matches(responsibleTeacher, null),
				"Setting 'birthDate' to null shouldn't be possible.");
		// setBirthDate -> birthDate
		String randomId = makePersonID();
		String expectedBD = personIdToBirthDate(randomId);
		// note that in StringMethods.returns() expected value is handled as regex
		// (a stricter check might be needed in the future)
		assertTrue(setBirthDate.returns(responsibleTeacher, expectedBD, randomId),
				explain("Invalid birthDate value with valid personId: " + randomId).expected(expectedBD).but("was: " + personBirthDate.read(responsibleTeacher)));
		assertTrue(personBirthDate.matches(responsibleTeacher, expectedBD));
		// getBirthDate
		assertGetterReturnsCurrentValue(responsibleTeacher, personBirthDate, getter, "birthDate");

		// invalid id
		String invalidId = makePersonIDWithIncorrectChecksum();
		assertMatches(setBirthDate.call(responsibleTeacher, invalidId), NO_CHANGE_PATTERN);
		assertEquals(personBirthDate.read(responsibleTeacher), expectedBD, 
				expectedUnchanged("birthDate", expectedBD, "after trying to set it to invalid id: " + invalidId));
		// invalid id (random string)
		String randString = randomString(11);
		assertMatches(setBirthDate.call(responsibleTeacher, randString),
				NO_CHANGE_PATTERN, "Incorrect return value of setBirthDate"
		);
		assertEquals(personBirthDate.read(responsibleTeacher), expectedBD,
				"Setting 'birthDate' to " + randString + " shouldn't be possible.");
	}

	@Test
	@DisplayName("Test startYear")
	void startYearTests_V1() {
		Callable<?> setter = empStartYear.defaultSetter();
		IntMethods getter = empStartYear.defaultGetter();
		// Check default value is current year
		System.out.println("Checking the start year has the current year as the default value");
		assertEquals(empStartYear.read(responsibleTeacher), yearNow, 
				explain("Default value of startYear should be current year"));

		// setStartYear -> getStartYear
		// Check setStartYear sets the startYear attribute
		int randYear = rand.nextInt(minYear, yearNow);
		assertSetterUpdatesCurrentValue(responsibleTeacher, empStartYear, setter, getter, randYear, "startYear");

		// Check the startYear is within the specified range 2000 < startYear <
		// currentYear
		System.out.println(String.format("Checking the start year default value is between %d and %d", minYear, yearNow));
		assertTrue(empStartYear.matchesWithin(setStartYearEmployee, responsibleTeacher,
				Range.closed(minYear, yearNow)), explain(String.format("Incorrect startYear value"))
				.expected(String.format("value between %d and &d", minYear, yearNow)).but("was: " + empStartYear.read(responsibleTeacher)));

		// startYear -> getStartYear
		assertGetterReturnsNewValue(responsibleTeacher, empStartYear, getter, rand.nextInt(2001, yearNow), "startYear");
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
		assertTrue(getPayment.returnsAfter(setPayment, responsibleTeacher, monPayment),explain("getPayment should return MonthlyPayment object")
		);

		// Checking if setPayment sets the payment with MonthlyPayment with random
		// salary value
		System.out.println("Case 2:");
		salaryValue = randSalaryValue;
		System.out.println("Setting payment to monthly payment with salary " + salaryValue);
		monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		System.out.println("Checking getPayment returns the MontlyPayment object");
		assertTrue(getPayment.returnsAfter(setPayment, responsibleTeacher, monPayment),explain("getPayment should return MonthlyPayment object")
		);

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
		assertTrue(getPayment.returnsAfter(setPayment, responsibleTeacher, hourlyPayment),explain("getPayment should return MonthlyPayment object")
		);

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
		assertTrue(getPayment.returnsAfter(setPayment, responsibleTeacher, hourlyPayment),explain("getPayment should return MonthlyPayment object")
		);

		// Checking that setPayment can't set null value
		System.out.println("Case 5:");
		setPayment.call(responsibleTeacher, (Object) null);
		assertFalse(payment.matchesAfter(payment.defaultSetter(), responsibleTeacher, null),
				"Setting 'Payment' to null shouldn't be possible.");
	}

	@Test
	@DisplayName("Test calculatePayment")
	void calculatePaymentTests_V2() {

		// Checking calcualtePayment returns correct amount when responsible teacher has
		// monthly payment with example salary value
		System.out.println("Case 1:");
		double salaryValue = 756.85;
		System.out
				.println("Checking calcualtePayment returns correct amount when responsible teacher has monthly payment"
						+ " with salary value " + salaryValue);
		Object monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		setPayment.invoke(responsibleTeacher, monPayment);
		assertEquals(calculatePayment.call(responsibleTeacher), salaryValue,
				String.format(
						"Expected calculatePayment returns %.2f when responsible teacher has monthly payment with salary %.2f",
						salaryValue, salaryValue));

		// Checking calcualtePayment returns correct amount when responsible teacher has
		// monthly payment with random salary value
		System.out.println("Case 2:");
		salaryValue = randSalaryValue;
		System.out
				.println("Checking calcualtePayment returns correct amount when responsible teacher has monthly payment"
						+ " with salary value " + salaryValue);
		monPayment = paramlessMonthlyPayment.instantiate();
		setSalaryMonthlyPayment.invoke(monPayment, salaryValue);
		setPayment.invoke(responsibleTeacher, monPayment);
		assertEquals(calculatePayment.call(responsibleTeacher), salaryValue,
				String.format(
						"Expected calculatePayment returns %.2f when responsible teacher has monthly payment with salary %.2f",
						salaryValue, salaryValue));

		// Checking calcualtePayment returns correct amount when responsible teacher has
		// hour-based salary with values in the example output
		System.out.println("Case 3:");
		double eurosPerHour = 3.5;
		double hours = 11;
		System.out.println(
				"Checking calcualtePayment returns correct amount when responsible teacher has hour-based payment "
						+ "with " + eurosPerHour + " euros per hour"
						+ " and " + hours + " hours.");
		Object hbPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.invoke(hbPayment, eurosPerHour);
		setHours.invoke(hbPayment, hours);
		setPayment.invoke(responsibleTeacher, hbPayment);
		assertEquals(calculatePayment.call(responsibleTeacher), eurosPerHour * hours, 
				String.format(
						"Expected calculatePayment returns %.2f when responsible teacher has hour-based payment with %.2f euros per hour and %.2f hours",
						eurosPerHour * hours, eurosPerHour, hours));

		// Checking calcualtePayment returns correct amount when responsible teacher has
		// hour-based salary with random values
		System.out.println("Case 4:");
		eurosPerHour = randPerHourValue;
		hours = randHourValue;
		System.out.println(
				"Checking calcualtePayment returns correct amount when responsible teacher has hour-based payment "
						+ "with " + eurosPerHour + " euros per hour"
						+ " and " + hours + " hours.");
		hbPayment = paramlessHourBasedPayment.instantiate();
		setEurosPerHour.invoke(hbPayment, eurosPerHour);
		setHours.invoke(hbPayment, hours);
		setPayment.invoke(responsibleTeacher, hbPayment);
		assertEquals(calculatePayment.call(responsibleTeacher), eurosPerHour * hours,
				String.format(
						"Expected calculatePayment returns %.2f when responsible teacher has hour-based payment with %.2f euros per hour and %.2f hours",
						eurosPerHour * hours, eurosPerHour, hours));
	}

	@Test
	@DisplayName("Test getEmployeeIdString")
	void getEmployeeIdStringTest() {
		System.out.println(
				"Checking getEmployeeIdString returns the correct string for the empId for ResponsibleTeacher");
		assertEquals(getEmployeeIdString.call(responsibleTeacher), "OY_TEACHER_", 
				explain("Invalid return value of getEmployeeIdString"));
	}

	@Test
	@DisplayName("Test courses")
	@SuppressWarnings("unchecked")
	void coursesTests_V2() {

		// Default array length
		// Check the courses list is empty when the
		List<Object> returnedCourses = (List<Object>) coursesResponsibleT.read(responsibleTeacher);
		int length = 0;
		for (; length < returnedCourses.size(); length++) {
			if (returnedCourses.get(length) == null) {
				break;
			}
		}
		System.out.println("Case1:");
		System.out.println("Checking the courses list empty when the object is created.");
		assertEquals(0, length, "getCourses should return empty list when the courses list is empty.");

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
		setCoursesResponsibleT.call(responsibleTeacher, coursesList);

		// Access the array returned by getCourses
		// @formatter:off
		returnedCourses = (List<Object>) coursesResponsibleT.read(responsibleTeacher); // Array reference might have
		                                                                               // changed (defensive copying)
		// @formatter:on
		System.out.println("Accessing courses list");
		assertDesignatedCoursesEqual(desCourse1, returnedCourses.get(0));

		System.out.println("Case 2:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing 4 designated courses in the example output:");
		// Add three other courses
		coursesList.add(desCourse2);
		coursesList.add(desCourse3);
		coursesList.add(desCourse4);
		setCoursesResponsibleT.call(responsibleTeacher, coursesList);

		// Access the array returned by getCourses
		// @formatter:off
		returnedCourses = (List<Object>) coursesResponsibleT.read(responsibleTeacher); // Array reference might have
		                                                                               // changed (defensive copying)
		// @formatter:on
		System.out.println("Accessing courses list");
		assertDesignatedCoursesEqual(desCourse1, returnedCourses.get(0));
		assertDesignatedCoursesEqual(desCourse2, returnedCourses.get(1));
		assertDesignatedCoursesEqual(desCourse3, returnedCourses.get(2));
		assertDesignatedCoursesEqual(desCourse4, returnedCourses.get(3));

		System.out.println("Case 3:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing randomly generated designated courses");
		List<Object> randomCoursesList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList.add(randomDesignatedCourse());
		}
		setCoursesResponsibleT.call(responsibleTeacher, randomCoursesList);

		// Access the array returned by getCourses
		// @formatter:off
		returnedCourses = (List<Object>) coursesResponsibleT.read(responsibleTeacher); // Array reference might have
		                                                                               // changed (defensive copying)
		// @formatter:on
		System.out.println("Accessing courses list");
		System.out.println(randomCoursesList);
		for (int i = 0; i < 5; i++) {
			assertDesignatedCoursesEqual(randomCoursesList.get(i), returnedCourses.get(i));
		}
		System.out.println("Case 4:");
		System.out.println("Checking the courses list cannot be set to null");
		setCoursesResponsibleT.call(responsibleTeacher, (Object) null);
		assertNotNull(coursesResponsibleT.read(responsibleTeacher), 
				explain("Courses list should not be settable to null")
				.expected("Not null").but("was: " + coursesResponsibleT.read(responsibleTeacher)));
	}

	@Test
	@DisplayName("Test getCourses")
	@SuppressWarnings("unchecked")
	void getCoursesTests_V2() {

		// Default array length
		// Check the courses list is empty when the
		List<Object> teachersCourses = (List<Object>) coursesResponsibleT.read(responsibleTeacher);
		int length = 0;
		length = getCoursesResponsibleT.call(responsibleTeacher).length();
		System.out.println("Case1:");
		System.out.println("Checking the return value when the courses list is empty");
		assertEquals(0, length, "getCourses should return empty list when the courses list is empty.");

		List<Object> coursesList = new ArrayList<>();

		Object course1 = sevenParamsCourse.instantiate("More basic studies", 223344, 'A', MANDATORY, 1, 50.50, false);
		Object desCourse1 = allParamDesignatedCourse.instantiate(course1, true, 2023);

		Object course2 = sevenParamsCourse.instantiate("Even more basic studies", 556667, 'A', OPTIONAL, 3, 50.00,
				false);
		Object desCourse2 = allParamDesignatedCourse.instantiate(course2, false, 2023);

		Object course3 = sevenParamsCourse.instantiate("Extra master studies", 666666, 'S', OPTIONAL, 5, 8.00, false);
		Object desCourse3 = allParamDesignatedCourse.instantiate(course3, false, 2022);

		Object course4 = sevenParamsCourse.instantiate("Final master studies", 888888, 'S', MANDATORY, 5, 18.00, false);
		Object desCourse4 = allParamDesignatedCourse.instantiate(course4, true, 2023);

		System.out.println("Case 2:");
		System.out.println(
				"Checking the output of getCourses after calling setCourses with a list containing one designated course:");
		// Add one designated course
		coursesList.add(desCourse1);
		setCoursesResponsibleT.call(responsibleTeacher, coursesList);

		// Access the courses list and return String of getCourses
		String output = getCoursesResponsibleT.call(responsibleTeacher);

		// Build the expected output pattern
		StringBuilder coursesPatternSB = new StringBuilder();
		for (Object ds : coursesList) {

			Object course = getCourseDesignatedCourse.invoke(ds);

			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String responsibleStr = (isResponsible.invoke(ds)) ? "Responsible teacher: " : "Teacher: ";
			String designatedCoursePatternStr = "" + responsibleStr
					+ "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ "\\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
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
		findIgnoringWhitespace(coursesPatternSB.toString(), output, "in the return value of toString", coursesPatternSB.substring(0, 19));

		System.out.println("Case 2:");
		System.out.println(
				"Checking the output of getCourses after calling setCourses with a list containing 4 designated courses:");
		// Add three other courses
		coursesList.add(desCourse2);
		coursesList.add(desCourse3);
		coursesList.add(desCourse4);
		setCoursesResponsibleT.call(responsibleTeacher, coursesList);

		// Return String of getCourses
		String multipleCourseOutput = getCoursesResponsibleT.call(responsibleTeacher);

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
			String responsibleStr = (isResponsible.invoke(ds)) ? "Responsible teacher: " : "Teacher: ";
			String designatedCoursePatternStr = "" + responsibleStr
					+ "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ "\\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
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
				"in the return value of toString", multipleCoursesPatternSB.substring(0, 19));

		System.out.println("Case 3:");
		System.out.println(
				"Checking the courses list after calling setCourses with a list containing randomly generated designated courses");
		List<Object> randomCoursesList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList.add(randomDesignatedCourse());
		}
		setCoursesResponsibleT.call(responsibleTeacher, randomCoursesList);

		// Return String of getCourses
		String randomCoursesOutput = getCoursesResponsibleT.call(responsibleTeacher);
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
			String responsibleStr = (isResponsible.invoke(ds)) ? "Responsible teacher: " : "Teacher: ";
			String designatedCoursePatternStr = "" + responsibleStr
					+ "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ "\\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
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
		findIgnoringWhitespace(randomCoursesPatternSB.toString(), randomCoursesOutput,
				"in the return value of toString", randomCoursesPatternSB.substring(0, 20));
	}

	@Test
	@DisplayName("Test toString")
	void toStringTests_V2() {

		// Test case 1, example output from the task description
		// Employee output
		// Employee id: OY_TEACHER_2604
		// First name: Goofy, Last name: The Dog
		// Birthdate: 141200A2315
		// Start year: 2023
		// Payment: 3.5 eurosPerHour 11.0 hours
		Object testResponsibleTeacher1 = createResponsibleT("The Dog", "Goofy", "141200A2315", 2023, 3.5, 11.0);
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
		setCoursesResponsibleT.invoke(testResponsibleTeacher1, coursesList);

		// TODO: How to make the test pass with both ',' and '.' as the decimal
		// separator.
		String output1 = testResponsibleTeacher1.toString();
		System.out.println("Case 1:");
		System.out.println("Checking the output of the toString method is in the specified format for example output");
		System.out.println(output1);
		StringBuilder outputSB = new StringBuilder();
		String testResponsibleTeacher1Pattern = "^Teacherid:"
				+ getIdString.invoke(testResponsibleTeacher1) + "\\n"
				+ "\"?First name:" + getFirstName.invoke(testResponsibleTeacher1)
				+ ",Last name: " + getLastName.invoke(testResponsibleTeacher1) + "\\n"
				+ "\"?Birthdate:" + getBirthDate.invoke(testResponsibleTeacher1) + "\\n"
				+ "\"?Salary:"
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(testResponsibleTeacher1)) + "";
		outputSB.append(testResponsibleTeacher1Pattern);

		outputSB.append("\\nTeacher for courses:");

		StringBuilder multipleCoursesPatternSB = new StringBuilder();
		for (Object ds : coursesList) {
			multipleCoursesPatternSB.append("\\n");
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String responsibleStr = (isResponsible.invoke(ds)) ? "Responsible teacher: " : "Teacher: ";
			String designatedCoursePatternStr = "" + responsibleStr
					+ "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ "\\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
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
		output1 = testResponsibleTeacher1.toString();
		findIgnoringWhitespace(outputSB.toString(), output1, "in the return value of toString", outputSB.substring(0, 19));

		// Test cases, random data with hourly payment
		System.out.println("Case 2:");
		System.out.println("Checking the output of the toString method is in the specified format for random data");

		Object randomResponsibleTeacher = createResponsibleT(randomString(8), randomString(6), makePersonID(),
				rand.nextInt(minYear, yearNow), randPerHourValue, randHourValue);
		List<Object> randomCoursesList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList.add(randomDesignatedCourse());
		}
		setCoursesResponsibleT.invoke(randomResponsibleTeacher, randomCoursesList);
		String output2 = randomResponsibleTeacher.toString();
		System.out.println(output2);
		StringBuilder output2SB = new StringBuilder();
		String randomResponsibleTeacherPattern = "^Teacherid:"
				+ getIdString.invoke(randomResponsibleTeacher) + "\\n"
				+ "\"?First name:" + getFirstName.invoke(randomResponsibleTeacher)
				+ ",Last name: " + getLastName.invoke(randomResponsibleTeacher) + "\\n"
				+ "\"?Birthdate:" + getBirthDate.invoke(randomResponsibleTeacher) + "\\n"
				+ "\"?Salary:"
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(randomResponsibleTeacher)) + "";
		output2SB.append(randomResponsibleTeacherPattern);
		output2SB.append("\\nTeacher for courses:");

		multipleCoursesPatternSB = new StringBuilder();
		for (Object ds : randomCoursesList) {
			multipleCoursesPatternSB.append("\\n");
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String responsibleStr = (isResponsible.invoke(ds)) ? "Responsible teacher:"
					: "Teacher:";
			String designatedCoursePatternStr = "" + responsibleStr
					+ "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ "\\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
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
		output2SB.append(multipleCoursesPatternSB.toString());
		output2 = randomResponsibleTeacher.toString();
		findIgnoringWhitespace(output2SB.toString(), output2, "in the return value of toString", output2SB.substring(0, 19));

		// Test cases, random data with monthly payment
		System.out.println("Case 3:");
		System.out.println(
				"Checking the output of the toString method is in the specified format for random data for monthly payment");

		Object randomResponsibleTeacher2 = createResponsibleT(randomString(8), randomString(6), makePersonID(),
				rand.nextInt(minYear, yearNow), randSalaryValue);
		List<Object> randomCoursesList2 = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			randomCoursesList2.add(randomDesignatedCourse());
		}
		setCoursesResponsibleT.invoke(randomResponsibleTeacher2, randomCoursesList2);
		String output3 = randomResponsibleTeacher2.toString();
		System.out.println(output3);
		StringBuilder output3SB = new StringBuilder();
		String randomResponsibleTeacherPattern2 = "^Teacherid:"
				+ getIdString.invoke(randomResponsibleTeacher2) + "\\n"
				+ "\"?First name:" + getFirstName.invoke(randomResponsibleTeacher2)
				+ ",Last name: " + getLastName.invoke(randomResponsibleTeacher2) + "\\n"
				+ "\"?Birthdate:" + getBirthDate.invoke(randomResponsibleTeacher2) + "\\n"
				+ "\"?Salary:"
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(randomResponsibleTeacher2)) + "";
		output3SB.append(randomResponsibleTeacherPattern2);
		output3SB.append("\\nTeacher for courses:");

		multipleCoursesPatternSB = new StringBuilder();
		for (Object ds : randomCoursesList2) {
			multipleCoursesPatternSB.append("\\n");
			Object course = getCourseDesignatedCourse.invoke(ds);
			String desCourseTypeStr = (getCourseType.invoke(course) == 0) ? "Optional" : "Mandatory";
			String responsibleStr = (isResponsible.invoke(ds)) ? "Responsible teacher: " : "Teacher: ";
			String designatedCoursePatternStr = "" + responsibleStr
					+ "\\[course=\\["
					+ getCourseCode.invoke(course) + ""
					+ "\\(" + String.format(Locale.US, "%.2f", getCourseCredits.invoke(course))
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
		output3SB.append(multipleCoursesPatternSB.toString());
		output3 = randomResponsibleTeacher2.toString();
		findIgnoringWhitespace(output3SB.toString(), output3, "in the return value of toString", output3SB.substring(0, 19));
	}
}
