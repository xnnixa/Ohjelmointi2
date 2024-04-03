package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.m3s.maeaettae.jreq.Quantifier;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

// This is an abstract class
// Tests only test if the required attributes and methods exist
// Functionality is not tested. It will be tested through the subclasses
@TestCategories({ @TestCategory("Person") })
class PersonTests {

	// Check the required Person class is implemented
	@Test
	@DisplayName("Person class is implemented")
	void testPersonClassExists() {
		classPerson.require();
	}

	@Test
	@DisplayName("Person class is abstract")
	void testPersonClassIsAbstract() {
		// Checking the Person class is an abstract class
		classPerson.isAbstract().require();
	}

		@Test
	@DisplayName("constantValuesUsageTest")
	public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework3/";
		File file = new File(path + "Person.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }

	// Check the constructor with params String lname, String fname is implemented
	@Test
	@DisplayName("Person class constructor")
	void testConstructor() {
		twoParamsPerson.require();
	}

	// Check that Person class contains the required attribtues
	@Test
	@DisplayName("Person class should contain the required attributes")
	void requiredAttributes() {
		firstName.require();
		lastName.require();
		birthDate.require();
	}

	// Check the Person class contains the required set/get methods
	@Test
	@DisplayName("Required set/get methods in the Person class")
	void requiredAccessMethods() {
		firstName.defaultGetter().require();
		lastName.defaultSetter().require();

		lastName.defaultGetter().require();
		lastName.defaultSetter().require();

		setBirthDate.require();
		birthDate.defaultGetter().require();
	}

	// Check the Person class contains the required getRandomId method
	@Test
	@DisplayName("Required getRandomId method in the Person class")
	void requiredGetRandomIdMethod() {
		getRandomId.require();
	}

	// Check the Person class contains the required getIdString method
	@Test
	@DisplayName("Required getIdString method in the Person class")
	void requiredGetIdStringMethod() {
		getIdString.require();
	}

	@Test
	@DisplayName("All attributes should be private")
	void requirePrivateAttributes() {
		classPerson.hasField().isPrivate().require(Quantifier.FOR_ALL);
	}
}
