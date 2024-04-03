package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import dev.m3s.maeaettae.jreq.Callable;
import dev.m3s.maeaettae.jreq.IntMethods;
import dev.m3s.maeaettae.jreq.DoubleMethods;
import dev.m3s.maeaettae.jreq.Quantifier;
import dev.m3s.maeaettae.jreq.Range;
import dev.m3s.maeaettae.jreq.StringMethods;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V1)
@TestCategories({ @TestCategory("Employee") })
class V1EmployeeTests {

	private Object employee;
	private String fName = randomString(6);
	private String lName = randomString(8);

	private final double randSalaryValues[] = { 1000.00, 1500.50, 1200.75, 2000.80 };
	private double randSalaryValue = randSalaryValues[rand.nextInt(0, randSalaryValues.length)];

	private Object createEmpoyee(String lName, String fName, String pId, int sYear, double salary) {
		Object testEmployee = twoParamEmployee.instantiate(lName, fName);
		setBirthDate.invoke(testEmployee, pId);
		setStartYearEmployee.invoke(testEmployee, sYear);
		setSalary.invoke(testEmployee, salary);
		return testEmployee;
	}

	@BeforeAll
	static void init() {
		// ? Should the se be moved to a separate @Test method?
		classEmployee.require();
		twoParamEmployee.require();
	}

	@BeforeEach
	void setUp() {
		employee = twoParamEmployee.instantiate(lName, fName);
	}

	@Test
	@DisplayName("Inheritance relations")
	// Check Employee class inherits the Person class
	void inheritenceTest_V1() {
		classEmployee.declaresSupertype(classPerson).require();
	}

	@Test
	@DisplayName("Implemented interfaces")
	void interfaceTest_V1() {
		// Checking the Employee class implements the Payment interface
		classEmployee.declaresSupertype(interfacePayment);
	}

	@Test
	@DisplayName("All required attributes should be private and non-static")
	void requireNonStaticAttributes_V1() {
		empId.require();
		startYear.require();
		salary.require();
		classEmployee.hasField().isPrivate().require(Quantifier.FOR_ALL);
		classEmployee.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test Constructor")
	void employeeConstructorSetsNamesTest_V1() {

		// Check the constructor sets lname and fname are set correctly
		System.out.println("Check the constructor sets the last name and the first name");
		System.out.println(String.format("Calling new Employee( %s, %s )", lName, fName));

		assertEquals(firstName.read(employee), fName, explain("Incorrect firstName value"));
		assertEquals(lastName.read(employee), lName, explain("Incorrect lastName value"));

		System.out.println("Check birthdate has default value");
		assertMatches(personBirthDate.read(employee), NOT_AVAILABLE_PATTERN,
				"Date of birth has invalid default value"
		);
	}

	@Test
	@DisplayName("empId is generated randomly in the specified format and ranges")
	void empIdTests_V1() {

		// Check the constuctor generates a the empId within the ranges
		// public static final int MIN_EMP_ID = 2001;
		// public static final int MAX_EMP_ID = 3000;
		System.out.println("Checking the constructor sets emplyee Id");
		String idString = getIdString.call(employee);
		System.out.println("Checking the employee id is in the specified format");

		String empIdPattern = "OY_2\\d{3}";
		System.out.println(idString);
		findIgnoringWhitespace(empIdPattern, idString, "in the return value of toString", empIdPattern);

		int idNumber = Integer.valueOf(idString.substring(3));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber && idNumber <= 3000, explain(
				String.format("Employee id should be within [2001,3000]. It was %d.", idNumber)));

		// Generate random employees and check if id is the same
		System.out.println("Checking the employee id is generated randomly");
		Object employee1 = twoParamEmployee.instantiate(null, (Object) null);
		String idString1 = getIdString.call(employee1);

		findIgnoringWhitespace(empIdPattern, idString1, "in the return value of toString", empIdPattern);

		System.out.println("Checking the employee id within the specified range");
		Object employee2 = twoParamEmployee.instantiate(null, (Object) null);
		String idString2 = getIdString.call(employee2);

		findIgnoringWhitespace(empIdPattern, idString2, "in the return value of toString", empIdPattern);

		assertNotEquals(idString, idString2, explain("getIdString should always return the current idString value"));

		// TODO: How do we test if it is indeed randomly generated
		// Regular expression for 2001 - 3000. "OY_2\\d{3}" includes also OY_2000
	}

	@Test
	@DisplayName("Test getRandomId")
	void getRandomIdTest_V1() {
		// Allowed values for employee IDs are 2001 - 3000
		Range<Integer> valid = Range.closed(2001, 3000);
		for (int i = 0; i < 1000; i++) {
			int value = getRandomId.invoke(employee, 2001, 3000);
			assertTrue(valid.includes(value), explain("Expected random id within " + valid.toString() + ", got " + value));
		}
	}

	@Test
	@DisplayName("Test getEmployeeIdString")
	void getEmployeeIdStringTests_V1() {
		System.out.println("Checking getEmployeeIdString returns the correct string for the empId for Employee");
		assertEquals(getEmployeeIdString.call(employee), "OY_", explain("Incorrect return value of getEmployeeIdString"));
	}

	@Test
	@DisplayName("Test getIdString")
	void getIdStringTests_V1() {

		String idString = getIdString.call(employee);
		findIgnoringWhitespace("OY_2\\d{3}", idString, "in the return value of toString", "OY_2\\d{3}");

		int idNumber = Integer.valueOf(idString.substring(3));
		System.out.println("Checking the employee id within the specified range");
		assertTrue(2001 <= idNumber && idNumber <= 3000, explain(
				String.format("Employee id should be between 2001 and 3000. It was %d.", idNumber)));
		// TODO: Should it be tested that getEmployeeIdString and getIdString return
		// the same Id?
	}

	@Test
	@DisplayName("Test firstName")
	void firstNameTests_V1() {
		Object testEmployee = twoParamEmployee.instantiate(null, (Object) null);
		// Default value
		assertMatches(firstName.read(testEmployee), NO_NAME_PATTERN,
				"Default value of 'firstName' is not 'No name'"
		);

		// setFirstName -> firstName
		System.out.println("Checking the setFirstName method sets the first name of the employee");
		String randomName = randomString(8);
		assertTrue(firstName.matchesAfter(firstName.defaultSetter(), testEmployee,
				randomName), explain("Invalid firstName value"));
		// getFirstName
		assertTrue(firstName.defaultGetter().returns(testEmployee, randomName), 
				explain("Invalid return value of getFirstName"));
		// firstName -> getFirstName
		randomName = randomString(8);
		assertTrue(firstName.defaultGetter().returns(testEmployee,
				firstName.write(testEmployee, randomName)), 
				explain("Invalid return value of getFirstName"));

		// setFirstName(null) -> firstName
		System.out.println("Checking the employee first name can not be set to null");
		assertFalse(firstName.matchesAfter(firstName.defaultSetter(), testEmployee, null),
				expectedUnchanged("firstName", randomName, "after calling trying to set firstName to null"));
	}

	@Test
	@DisplayName("Test lastName")
	void lastNameTests_V1() {
		Object testEmployee = twoParamEmployee.instantiate(null, (Object) null);
		// Default value
		assertMatches(lastName.read(testEmployee), NO_NAME_PATTERN,
				"Default value of 'lastName' is not 'No name'"
		);		

		// lastName -> lastName
		System.out.println("Checking the setFirstName method sets the first name of the employee");
		String randomName = randomString(8);
		assertTrue(lastName.matchesAfter(lastName.defaultSetter(), testEmployee,
				randomName), explain("Invalid lastName"));
		// setLastName
		assertTrue(lastName.defaultGetter().returns(testEmployee, randomName),
				explain("Invalid return value of getLastName"));
		// lastName -> lastName
		randomName = randomString(8);
		assertTrue(lastName.defaultGetter().returns(testEmployee,
				lastName.write(testEmployee, randomName)), 
				explain("Invalid return value of getLastName"));

		// setLastName(null) -> lastName
		System.out.println("Checking the employee last name can not be set to null");
		assertFalse(lastName.matchesAfter(lastName.defaultSetter(), testEmployee, null),
				expectedUnchanged("lastName", randomName, "after calling trying to set lastName to null"));

	}

	@Test
	@DisplayName("Test birthDate")
	void birthDateTests_V1() {
		StringMethods getter =personBirthDate.defaultGetter();
		// Default value
		assertMatches(personBirthDate.read(employee), NOT_AVAILABLE_PATTERN,
				"Invalid default value of birthDate");
		// setBirthDate(null) -> birthDate
		assertMatches(setBirthDate.call(employee, (Object) null),
				NO_CHANGE_PATTERN,
				"Invalid birthDate value"
		);

		// setBirthDate -> birthDate
		String randomId = makePersonID();
		String expectedBD = personIdToBirthDate(randomId);
		// note that in StringMethods.returns() expected value is handled as regex
		// (a stricter check might be needed in the future)
		assertEquals(setBirthDate.call(employee, randomId), expectedBD, 
				explain("Invalid return value of setBirthDate"));
		assertEquals(personBirthDate.read(employee), expectedBD, 
				explain("Invalid birthDate value"));
		// getBirthDate
		assertGetterReturnsCurrentValue(employee, personBirthDate, getter, "birthDate");


		// invalid id
		String invalidId = makePersonIDWithIncorrectChecksum();
		assertMatches(setBirthDate.call(employee, invalidId), NO_CHANGE_PATTERN);
		assertEquals(personBirthDate.read(employee), expectedBD, 
				explain("Invalid birthDate value"));

		// invalid id (random string)
		String randString = randomString(11);
		assertMatches(setBirthDate.call(employee, randString),
				NO_CHANGE_PATTERN, "Invalid return value of setBirthDate"
		);
		assertFalse(personBirthDate.matches(employee, randString),
				explain("Setting 'birthDate' to " + randString + " shouldn't be possible."));

		//invalid id (null)
		setBirthDate.call(employee, randomId);
		setBirthDate.call(employee, null);
		assertEquals(personBirthDate.read(employee), expectedBD,
				expectedUnchanged("personBirthDate", expectedBD, "after trying to set birthDate to null"));
	}

	@Test
	@DisplayName("Test startYear")
	void startYearTests_V1() {
		Callable<?> setter = empStartYear.defaultSetter();
		IntMethods getter = empStartYear.defaultGetter();
		// Check default value is current year
		System.out.println("Checking the start year has the current year as the default value");
		assertEquals(empStartYear.read(employee), yearNow, 
				explain("Invalid default value of startYear"));

		// setStartYear -> getStartYear
		// Check setStartYear sets the startYear attribute
		int randYear = rand.nextInt(minYear, yearNow);
		assertGetterReturnsSetValue(employee, setter, getter, randYear, "startYear");

		// Check the startYear is within the specified range 2000 < startYear <
		// currentYear
		System.out.println("Checking the start year default value is the current year");
		assertTrue(empStartYear.matchesWithin(setStartYearEmployee, employee, Range.closed(minYear, yearNow)),
						explain("Invalid startYear value")
						.expected(String.format("value betweem [%d, %d]", minYear, yearNow))
						.but("was: " + empStartYear.read(employee)));

		// startYear -> getStartYear
		assertGetterReturnsNewValue(employee, empStartYear, getter, rand.nextInt(2001, yearNow), "startYear");
	}

	@Test
	@DisplayName("Test salary")
	void salaryTests_V1() {
		Callable<?> setter = salary.defaultSetter();
		DoubleMethods getter = salary.defaultGetter();

		double salaryValue = 0.0;
		// Check setSalary sets the salary attribute
		System.out.println("Cheking setSalary method sets the salary attribute");
		assertSetterUpdatesCurrentValue(employee, salary, setter, getter, randSalaryValue, "salary");
		// TODO: Can we use a Range which has only lowerbound
		// TODO: Check if the Error messages is Assert statements are shown in the
		// pipeline output.
		// Check the salary is within the specified range, > 0.0
		System.out.println("Checking the salary can take only positive values");
		salaryValue = getSalary.invoke(employee);
		assertFalse(getSalary.returnsAfter(setSalary, employee, -randSalaryValue), 
				explain("Invalid salary value")
				.expected(Double.toString(salaryValue)).but("was: " + -randSalaryValue));

		assertEquals(getSalary.call(employee), salaryValue,
				expectedUnchanged("return value of getSalary", salaryValue, "after trying to set it to a negative value"));

		// Check the salary cannot be 0.0
		System.out.println("Checking the salary cannot be 0.0");
		salaryValue = getSalary.invoke(employee);
		assertFalse(getSalary.returnsAfter(setSalary, employee, 0.0),
				explain("Invalid salary value")
				.expected(Double.toString(salaryValue)).but("was: " + salary.read(employee)));
		
		assertEquals(getSalary.call(employee), salaryValue,
				expectedUnchanged("return value of getSalary", salaryValue, "after trying to set it to a negative value"));
	}

	@Test
	@DisplayName("Test calculatePayment")
	void calculatePaymentTests_V1() {
		double salaryValue = 0.0;

		// Check setSalary sets the salary attribute
		System.out.println("Cheking calculatePayment returns the amount of payment correctly");
		setSalary.call(employee, randSalaryValue);
		salaryValue = getSalary.call(employee);
		double expectedPaymentAmount = salaryValue * 1.5;
		assertTrue(calculatePayment.returns(employee, expectedPaymentAmount),
				explain("Invalid payment value")
				.expected(String.format("%.2f = 1.5 * %.2f", expectedPaymentAmount, salaryValue))
				.but("was: " + calculatePayment.invoke(employee)));

	}

	@Test
	@DisplayName("Test toString")
	void toStringTests() {

		// Test case 1, example output from the task description
		// Employee output
		// Employee id: OY_2563
		// First name: Mickey, Last name: Mouse
		// Birthdate: 23.04.1998
		// Start year: 2023
		// Salary: 1135.28
		Object testEmployee1 = createEmpoyee("Mouse", "Mickey", "230498-045T", 2023, 756.85);

		// TODO: How to make the test pass with both ',' and '.' as the decimal
		// separator.
		String output1 = employeeToString.invoke(testEmployee1);
		System.out.println("Checking the output of the toString method is in the specified format for sample output 1");
		System.out.println(output1);
		String employee1Pattern = "^Employee id:\"?" + getIdString.invoke(testEmployee1)
				+ "\"?"
				+ "First name:" + getFirstName.invoke(testEmployee1)
				+ ",Last name:" + getLastName.invoke(testEmployee1)
				+ "Birthdate:" + getBirthDate.invoke(testEmployee1)
				+ "Start year:" + getStartYearEmployee.invoke(testEmployee1)
				+ "Salary:"
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(testEmployee1));

		System.out.println(employee1Pattern);
		findIgnoringWhitespace(employee1Pattern, output1, "in the return value of toString", employee1Pattern.substring(0, 22));

		// Test case 2, example output from the task description
		// Employee id: OY_2791
		// First name: Goofy, Last name: The Dog
		// Birthdate: 14.12.2000
		// Start year: 2023
		// Salary: 225.00

		Object testEmployee2 = createEmpoyee("The Dog", "Goofy", ": 141200A2315", 2023, 150);

		// TODO: How to make the test pass with both ',' and '.' as the decimal
		// separator.
		String output2 = employeeToString.invoke(testEmployee2);
		System.out.println("Checking the output of the toString method is in the specified format for sample output 2");
		System.out.println(output2);
		String employee2Pattern = "^Employee id:\"?" + getIdString.invoke(testEmployee2)
				+ "\"?"
				+ "First name:" + getFirstName.invoke(testEmployee2)
				+ ",Last name:" + getLastName.invoke(testEmployee2)
				+ "Birthdate:" + getBirthDate.invoke(testEmployee2)
				+ "Start year:" + getStartYearEmployee.invoke(testEmployee2)
				+ "Salary:"
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(testEmployee2));

		findIgnoringWhitespace(employee2Pattern, output2, "in the return value of toString", employee2Pattern.substring(0, 22));

		int testCases = 2;
		for (int i = 0; i < testCases; i++) {
			Object testEmployee = createEmpoyee(randomString(8),
					randomString(6),
					makePersonID(),
					rand.nextInt(minYear, yearNow), randSalaryValues[rand.nextInt(0, randSalaryValues.length)]);

			String output = employeeToString.invoke(testEmployee);
			System.out.println(
					"Checking the output of the toString method is in the specified format for random employee data");
			System.out.println(output);
		String employeePattern = "^Employee id:\"?" + getIdString.invoke(testEmployee)
				+ "\"?"
				+ "First name:" + getFirstName.invoke(testEmployee)
				+ ",Last name:" + getLastName.invoke(testEmployee)
				+ "Birthdate:" + getBirthDate.invoke(testEmployee)
				+ "Start year:" + getStartYearEmployee.invoke(testEmployee)
				+ "Salary:"
				+ String.format(Locale.US, "%.2f", calculatePayment.invoke(testEmployee));
			findIgnoringWhitespace(employeePattern, output, "in the return value of toString", employeePattern.substring(0, 22));
		}
	}
}
