package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

// Tests only test if the required attributes and methods exist
// Functionality is not tested. It will be tested through the subclasses
@TestCategories({ @TestCategory("Payment") })
class PaymentTests {

	// Check the required Payment interface is implemented
	@Test
	@DisplayName("Test Payment interface")
	void testPaymentInterfaceExists() {
		interfacePayment.require();
	}

	// Check the Payment interface contains the required abstract method
	@Test
	@DisplayName("Require calculatePayment method in the Payment interface")
	void requiredMethod() {
		calculatePayment.isAbstract().require();
	}
}
