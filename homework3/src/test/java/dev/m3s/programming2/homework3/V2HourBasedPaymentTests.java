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

@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V2)
@TestCategories({ @TestCategory("HourBasedPayment") })
class V2HourBasedPaymentTests {

	private Object hourBasedPayment;
	private final double randPerHourValues[] = { 3.5, 10.00, 15.75, 60.50, 100.20 };
	private double randPerHourValue = randPerHourValues[rand.nextInt(0, randPerHourValues.length)];
	private final double randHourValues[] = { 11.0, 165.25, 20.25, 142.75, 135.50 };
	private double randHourValue = randHourValues[rand.nextInt(0, randHourValues.length)];

	// Check the required HourBasedPayment class is implemented
	@BeforeAll
	static void init() {
		classHourBasedPayment.require();
	}

	@BeforeEach
	void setUp() {
		hourBasedPayment = constructorHourBasedPayment.instantiate();
	}

	@Test
	@DisplayName("Implemented interfaces")
	void interfaceTest_V2() {
		// Checking HourBasedPayment implements the Payment interface
		classHourBasedPayment.declaresSupertype(interfacePayment).require();
	}

	@Test
	@DisplayName("HourBasedPayment class attributes")
	void testRequiredAttributes_V2() {
		eurosPerHour.require();
		hours.require();
		classMonthlyPayment.hasField().isPrivate().require(Quantifier.FOR_ALL);
		classMonthlyPayment.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	// TODO: Should the required method calculatePayment from the Payment interface
	// or a separate Method definitione
	@Test
	@DisplayName("Required methods in the HourBasedPayment class")
	void requiredMethods_V2() {
		getEurosPerHour.require();
		setEurosPerHour.require();
		getHours.require();
		setHours.require();
		calculatePaymentHourBasedPayment.require();
	}

	@Test
	@DisplayName("Test eurosPerHour")
	void eurosPerHourTests_V2() {
		Callable<?> setter = eurosPerHour.defaultSetter();
		DoubleMethods getter = eurosPerHour.defaultGetter();

		double eurosValue = 0.0;
		// Check setEurosPerHour sets the salary attribute
		System.out.println("Checking setEurosPerHour method sets the eurosPerHour attribute");
		assertSetterUpdatesCurrentValue(hourBasedPayment, eurosPerHour, setter, getter, randPerHourValue, "hourBasedPayment");
		// TODO: Can we use a Range which has only lowerbound
		// TODO: Check if the Error messages is Assert statements are shown in the
		// pipeline output.
		// Check the eurosPerHour is within the specified range, > 0.0
		System.out.println("Checking the eurosPerHour can take only positive values");
		eurosValue = getEurosPerHour.invoke(hourBasedPayment);
		double invalidValue = -randPerHourValue;
		assertFalse(getEurosPerHour.returnsAfter(setEurosPerHour, hourBasedPayment,invalidValue),
				explain("Invalid return value of getEurosPerHour").expected(Double.toString(eurosValue)).but("was: " + invalidValue));
		assertEquals(getEurosPerHour.call(hourBasedPayment), eurosValue,
				expectedUnchanged("eurosPerHour", eurosValue, "trying to set it to a negative value"));

		// Check the salary cannot be 0.0
		System.out.println("Checking the eurosPerHour cannot be 0.0");
		eurosValue = getEurosPerHour.invoke(hourBasedPayment);
		assertFalse(getEurosPerHour.returnsAfter(setEurosPerHour, hourBasedPayment, 0.0),
			expectedUnchanged("eurosPerHour", eurosValue, "after trying to set it to 0.0")
				.expected(String.valueOf(eurosValue)).but("was: " + getEurosPerHour.invoke(hourBasedPayment)));
		assertEquals(getEurosPerHour.call(hourBasedPayment), eurosValue,
				expectedUnchanged("eurosPerHour", eurosValue, "after tring to set it to a negative value"));
	}

	@Test
	@DisplayName("Test hours")
	void hoursTests_V2() {

		Callable<?> setter = hours.defaultSetter();
		DoubleMethods getter = hours.defaultGetter();

		double hoursValue = 0.0;
		// Check hours sets the salary attribute
		System.out.println("Checking setHours method sets the hours attribute");
		assertSetterUpdatesCurrentValue(hourBasedPayment, hours, setter, getter, randHourValue, "hour");

		// TODO: Can we use a Range which has only lowerbound
		// TODO: Check if the Error messages is Assert statements are shown in the
		// pipeline output.
		// Check the eurosPerHour is within the specified range, > 0.0
		System.out.println("Checking the hours can take only positive values");
		hoursValue = getHours.invoke(hourBasedPayment);
		assertFalse(getHours.returnsAfter(setHours, hourBasedPayment, -randHourValue), 
				expectedUnchanged("hours", hoursValue, "after trying to set it to a negative value"));
		assertTrue(getHours.returns(hourBasedPayment, hoursValue),
				expectedUnchanged("hours", hoursValue, "after trying to set it to a negative value"));
				
		// Check the salary cannot be 0.0
		System.out.println("Checking the hours cannot be 0.0");
		hoursValue = getHours.invoke(hourBasedPayment);
		assertFalse(getHours.returnsAfter(setHours, hourBasedPayment, 0.0),
			expectedUnchanged("eurosPerHour", hoursValue, "after trying to set it to 0.0")
				.expected(String.valueOf(hoursValue)).but("was: " + getEurosPerHour.invoke(hourBasedPayment)));
		assertEquals(getHours.call(hourBasedPayment), hoursValue,
				expectedUnchanged("hours", hoursValue, "after trying to set it to a negative value"));
	}

	@Test
	@DisplayName("Test calculatePayment")
	void calculatePaymentTests_V2() {

		System.out.println("Cheking calculatePayment returns the amount of payment correctly");
		setEurosPerHour.call(hourBasedPayment, randPerHourValue);
		setHours.call(hourBasedPayment, randHourValue);
		assertEquals(calculatePaymentHourBasedPayment.call(hourBasedPayment), randPerHourValue * randHourValue,
				explain("Expected the method returns hours * eurosPerHours = %.2f"));

	}
}
