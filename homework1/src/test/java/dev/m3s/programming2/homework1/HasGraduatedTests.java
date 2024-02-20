package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@TestCategories({ @TestCategory("hasGraduated") })
class HasGraduatedTests {

	Object student;

	@BeforeEach
	public void setUp() {
		student = parameterless.instantiate();
	}

	@Test // Test the default return value
	@Points(failDeduct = 1, category = "hasGraduated")
	@DisplayName("hasGraduated: all conditions unmet")
	void hasNotGraduatedTest() {
		assertReturns(hasGraduated.call(student), false, "hasGraduated",
				explain("A new student should not be already graduated.")
		);
	}

	@Test
	@Points(failDeduct = 1, category = "hasGraduated")
	@DisplayName("hasGraduated: all conditions met")
	void hasGraduatedTest() {
		int sYear = rand.nextInt(minYear, yearNow + 1);
		int gYear = rand.nextInt(sYear, yearNow + 1);

		setEnoughCredits(student, bachelorCredits);
		setEnoughCredits(student, masterCredits);
		setTitle(student, titleOfBachelorThesis);
		setTitle(student, titleOfMasterThesis);
		setStartYear.call(student, sYear);

		setGraduationYear.call(student, gYear);
		assertReturns(hasGraduated.call(student), true, "hasGraduated",
				explain("Not graduated despite setting all required "
						+ "preconditions and a valid graduation year.")
		);
	}
}
