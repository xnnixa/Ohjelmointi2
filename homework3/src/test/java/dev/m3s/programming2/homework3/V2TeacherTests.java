package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

// This is an interface
// Tests only test if the required attributes and methods exist
// Functionality is not tested. It will be tested through the classes implementing the interface
@EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V2)
@TestCategories({ @TestCategory("Teacher") })
class V2TeacherTests {

	// Check the required Teacher interface is implemented
	@Test
	@DisplayName("Teacher interface")
	void testTeacherInterfaceExists() {
		interfaceTeacher.require();
	}

	// Check the Payment interface contains the required abstract method
	@Test
	@DisplayName("Required getIdString method in the Person class")
	void requiredMethod() {
		getCoursesTeacher.isAbstract().require();
	}
}
