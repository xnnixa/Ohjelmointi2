package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the return values of the method canGraduate().
 * The test cases are as follows:
 * - Default return value (all conditions unmet)
 * - All preconditions have been set correctly
 * - The other title has not been set
 * - The other credits have not been set
 */
@TestCategories({ @TestCategory("canGraduate") })
class CanGraduateTests {

	Object student1;
	Object student2;

	@BeforeEach
	public void setUp() {
		student1 = parameterless.instantiate();
	}

	@Test
	@Points(failDeduct = 1, category = "canGraduate")
	@DisplayName("canGraduate: all conditions unmet")
	void conditionsUnmetTest() {
		// Test the default return value
		assertReturns(canGraduate.call(student1), false, "canGraduate",
				explain("A new student should not be ready to graduate.")
		);
	}

	@Test
	@Points(failDeduct = 1, category = "canGraduate")
	@DisplayName("canGraduate: all conditions met")
	void conditionsMetTest() {
		setEnoughCredits(student1, bachelorCredits);
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);

		assertReturns(canGraduate.call(student1), true, "canGraduate",
				explain("Unable to graduate despite sufficient credits and set "
						+ "thesis titles.")
		);
	}

	@Test
	@Points(failDeduct = 1, category = "CanGraduate")
	@DisplayName("canGraduate: thesis title missing")
	void missingTitleTests() {
		setEnoughCredits(student1, bachelorCredits);
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfMasterThesis);

		assertReturns(canGraduate.call(student1), false, "canGraduate",
				explain("Able to graduate without bachelor's thesis title.")
		);

		System.out.println();
		student2 = parameterless.create();

		setEnoughCredits(student2, bachelorCredits);
		setEnoughCredits(student2, masterCredits);
		setTitle(student2, titleOfBachelorThesis);

		assertReturns(canGraduate.call(student2), false, "canGraduate",
				explain("Able to graduate without master's thesis title.")
		);
	}

	@Test
	@Points(failDeduct = 1, category = "CanGraduate")
	@DisplayName("canGraduate: insufficient credits")
	void missingCreditsTests() {
		setEnoughCredits(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);

		assertReturns(canGraduate.call(student1), false, "canGraduate",
				explain("Able to graduate with insufficient bachelor's credits")
		);

		System.out.println();
		student2 = parameterless.create();

		setEnoughCredits(student2, bachelorCredits);
		setTitle(student2, titleOfBachelorThesis);
		setTitle(student2, titleOfMasterThesis);

		assertReturns(canGraduate.call(student2), false, "canGraduate",
				explain("Able to graduate with insufficient master's credits")
		);
	}

	@Test
	@Points(failDeduct = 1, category = "CanGraduate")
	@DisplayName("canGraduate: Pass edge values")
	void EdgeValuesTest() {
		student1 = parameterless.create();

		setEnoughEdgeCreditsBachelor(student1, bachelorCredits);
		setEnoughEdgeCreditsMaster(student1, masterCredits);
		setTitle(student1, titleOfBachelorThesis);
		setTitle(student1, titleOfMasterThesis);

		assertReturns(canGraduate.call(student1), true, "canGraduate",
				explain("Unable to graduate despite sufficient credits")
		);
	
		student2 = parameterless.create();

		System.out.println();

		setEdgeFailCreditsBachelor(student2, bachelorCredits);
		setEdgeFailCreditsMaster(student2, masterCredits);
		setTitle(student2, titleOfMasterThesis);
		setTitle(student2, titleOfBachelorThesis);

		assertReturns(canGraduate.call(student2), false, "canGraduate",
				explain("Able to graduate with insufficient credits")
		);
	}
}
