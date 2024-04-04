package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import dev.m3s.maeaettae.jreq.Callable;
import dev.m3s.maeaettae.jreq.DoubleMethods;
import dev.m3s.maeaettae.jreq.Quantifier;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;


@TestCategories({ @TestCategory("MonthlyPayment") })
class V2MonthlyPaymentTests {

	private Object monPayment;
	private final double randSalaryValues[] = { 1000.00, 1500.50, 1200.75, 2000.80 };
	private double randSalaryValue;

	// Check the required MonthlyPayment class is implemented
	@BeforeAll
	static void init() {
		classMonthlyPayment.require();
	}

	@BeforeEach
	void setUp() {
		monPayment = constructorMonthlyPayment.instantiate();
		int i = rand.nextInt(0, randSalaryValues.length);
		randSalaryValue = randSalaryValues[i];
	}

	@Test
	@DisplayName("Implemented interfaces")
	void interfaceTest_V2() {
		// Checking MonthlyPayment implements the Payment interface
		classMonthlyPayment.declaresSupertype(interfacePayment).require();
	}

	@Test
	@DisplayName("MonthlyPayment class attributes")
	void testRequiredAttributes_V2() {
		salaryMonthlyPayment.require();
		classMonthlyPayment.hasField().isPrivate().require(Quantifier.FOR_ALL);
		classMonthlyPayment.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	// TODO: Should the required method calculatePayment from the Payment interface
	// or a separate Method definitione
	@Test
	@DisplayName("Required methods in the MonthlyPayment class")
	void requiredMethods_V2() {
		getSalaryMonthlyPayment.require();
		setSalaryMonthlyPayment.require();
		calculatePayment.require();
	}

	@Test
	@DisplayName("Test salary")
	void salaryTests_V2() {
		Callable<?> setter = salaryMonthlyPayment.defaultSetter();
		DoubleMethods getter = salaryMonthlyPayment.defaultGetter();

		double salaryValue = 0.0;
		// Check setSalary sets the salary attribute
		System.out.println("Checking setSalary method sets the salary attribute");
		assertSetterUpdatesCurrentValue(monPayment, salaryMonthlyPayment, setter, getter, randSalaryValue, "salary");
		
		// TODO: Can we use a Range which has only lowerbound
		// TODO: Check if the Error messages is Assert statements are shown in the
		// pipeline output.
		// Check the salary is within the specified range, > 0.0
		System.out.println("Checking the salary can take only positive values");
		salaryValue = getSalaryMonthlyPayment.invoke(monPayment);
		assertFalse(getSalaryMonthlyPayment.returnsAfter(setSalaryMonthlyPayment, monPayment, -randSalaryValue),
				explain("Invalid salary value")
				.expected(Double.toString(salaryValue)).but("was: " + -randSalaryValue));

		assertEquals(getSalaryMonthlyPayment.call(monPayment), salaryValue,
				expectedUnchanged("return value of getSalary", salaryValue, "after trying to set it to a negative value"));

		// Check the salary cannot be 0.0
		System.out.println("Checking the salary cannot be 0.0");
		salaryValue = getSalaryMonthlyPayment.invoke(monPayment);
		assertFalse(getSalaryMonthlyPayment.returnsAfter(setSalaryMonthlyPayment, monPayment, 0.0),
				explain("Invalid salary value")
				.expected(Double.toString(salaryValue)).but("was: " + salaryMonthlyPayment.read(monPayment)));

		assertTrue(getSalaryMonthlyPayment.returns(monPayment, salaryValue),
				expectedUnchanged("return value of getSalaryMonthlyPayment", salaryValue, "after trying to set it to a negative value"));

	}

	@Test
	@DisplayName("Test calculatePayment")
	void calculatePaymentTests_V2() {
		double salaryValue = 0.0;

		System.out.println("Checking calculatePayment returns the amount of payment correctly");
		setSalaryMonthlyPayment.call(monPayment, randSalaryValue);
		salaryValue = getSalaryMonthlyPayment.call(monPayment);
		assertEquals(calculatePayment.call(monPayment), salaryValue,
				explain("Invalid payment value"));
	}
}
