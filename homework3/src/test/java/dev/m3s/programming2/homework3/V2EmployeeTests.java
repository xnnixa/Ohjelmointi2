package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import dev.m3s.maeaettae.jreq.Quantifier;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

// This is an abstract class
// Tests only test if the required attributes and methods exist
// Functionality is not tested. It will be tested through the subclasses


@TestCategories({ @TestCategory("Employee") })
class V2EmployeeTests {

	// Check the required Employee class is implemented
	@Test
	@DisplayName("Employee class is implemented")
	void testEmployeeClassExists_V2() {
		classEmployee.require();
	}

	@Test
	@DisplayName("Employee class is abstract")
	void testEmployeeClassIsAbstract_V2() {
		classEmployee.isAbstract().require();
	}

	@Test
	@DisplayName("Inheritance relations")
	// Check Employee class inherits the Person class
	void testInheritence_V2() {
		classEmployee.declaresSupertype(classPerson).require();
	}

	@Test
	@DisplayName("Implemented interfaces")
	void testInterface_V2() {
		// Checking the Employee class implements the Payment interface
		classEmployee.declaresSupertype(interfacePayment).require();
	}

	@Test
	@DisplayName("All required attributes should be private and non-static")
	void requireNonStaticAttributes_V2() {
		empId.require();
		startYear.require();
		payment.require();
		classEmployee.hasField().isPrivate().require(Quantifier.FOR_ALL);
		classEmployee.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	// Check the constructor with params String lname, String fname is implemented
	@Test
	@DisplayName("Employee class constructor with 2 parameters")
	void testConstructor_V2() {
		twoParamEmployee.require();
	}

	// Check the Employee class contains the required set/get methods
	@Test
	@DisplayName("Required set/get methods in the Employee class")
	void requiredAccessMethods_V2() {
		getIdString.require();

		getStartYearEmployee.require();
		setStartYearEmployee.require();

		getPayment.require();
		setPayment.require();
	}

	// Check the Employee class contains the required set/get methods
	@Test
	@DisplayName("Required methods")
	void requiredMethods_V2() {
		calculatePayment.require();
		getEmployeeIdString.isAbstract().isProtected().require();
	}

}
