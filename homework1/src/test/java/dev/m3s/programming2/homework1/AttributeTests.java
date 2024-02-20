package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.m3s.maeaettae.jreq.Quantifier;
import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/**
 * These tests look for the required fields (declared in H1)
 * and test that their modifiers, types and names, as well as
 * the default values match the requirements.
 */
@TestCategories({ @TestCategory("Attributes") })
class AttributeTests {

	Object student;

	@BeforeEach
	public void setUp() {
		student = parameterless.instantiate();
	}

	@Test
	@Points(category = "Attributes", failDeduct = 1)
	@DisplayName("All private and non-static")
	void requirePrivateAttributes() {
		classStudent.hasField()
				.isPrivate()
				.require(Quantifier.FOR_ALL);
		classStudent.hasField()
				.isStatic()
				.require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("firstName")
	void firstNameDeclarationTest() {
		assertMatches(firstName.read(student), NO_NAME_PATTERN,
		"Invalid default value of firstName");
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("lastName")
	void lastNameDeclarationTest() {
		assertMatches(lastName.read(student), NO_NAME_PATTERN,
		"Invalid default value of firstName");
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("id")
	void idDeclarationTest() {
		assertWithin(id.read(student), Range.closed(1, 100), "id",
		explain("Id should be within [1,100] "));
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("bachelorCredits")
	void bachelorCreditsDeclarationTest() {
		assertEquals(bachelorCredits.read(student), 0.0,
		explain("Incorrect bachelorCredits value"));
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("masterCredits")
	void masterCreditsDeclarationTest() {
		assertEquals(masterCredits.read(student), 0.0,
		explain("Incorrect masterCredits value"));
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("titleOfBachelorThesis")
	void titleOfBachelorThesisDeclarationTest() {
		assertMatches(titleOfBachelorThesis.read(student), NO_TITLE_PATTERN,
		"Invalid default value of titleOfBachelorThesis");
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("titleOfMastersThesis")
	void titleOfMastersThesisDeclarationTest() {
		assertMatches(titleOfMasterThesis.read(student), NO_TITLE_PATTERN,
		"Invalid default value of titleOfMasterThesis");
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("startYear")
	void startYearDeclarationTest() {
		assertEquals(startYear.read(student), yearNow,
				explain("Incorrect startYear value")
		);
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("graduationYear")
	void graduationYearDeclarationTest() {
		assertWithout(graduationYear.read(student), Range.closed(minYear, yearNow),
				"Graduation year", explain("Graduation year should not be set by default")
		);
	}

	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("constantValuesUsageTest")
    public void constantValuesUsageTest() throws Exception {
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework1/";
		File file = new File(path + "Student.java");
		
		files.add(file);

		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValueCheck", files);
		assertTrue((boolean) result[0], explain(result[1].toString()));

    }

	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	@Test
	@Points(category = "Attributes", failDeduct = 2)
	@DisplayName("birthDate")
	void testBirthDate() {
		assertMatches(birthDate.read(student), NOT_AVAILABLE_PATTERN,
		"Invalid default value of birthDate");
	}

	
}