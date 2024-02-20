package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.m3s.maeaettae.jreq.*;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the default getters of the following fields:
 * firstName, lastName, titleOfBachelorThesis, titleOfMasterThesis,
 * startYear, id, bachelorCredits, masterCredits, graduationYear
 */
@TestCategories(@TestCategory("Getters"))
class GetterTests {

	int currentStartYear;
	int newGraduationYear;
	Object student;

	@BeforeEach
	public void setUp() {
		student = parameterless.instantiate();
		currentStartYear = rand.nextInt(minYear, yearNow + 1);
		newGraduationYear = rand.nextInt(currentStartYear, yearNow + 1);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getFirstName")
	void getFirstNameTest() {
		compareCurrent(firstName, firstName.defaultGetter());
		compareNew(firstName, randomString(8), firstName.defaultGetter());
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getLastName")
	void getLastNameTest() {
		compareCurrent(lastName, lastName.defaultGetter());
		compareNew(lastName, randomString(8), lastName.defaultGetter());
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getId")
	void getIdTest() {
		compareCurrent(id, id.defaultGetter());
		compareNew(id, rand.nextInt(1, 101), id.defaultGetter());
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getBachelorCredits")
	void getBachelorCreditsTest() {
		compareCurrent(bachelorCredits, bachelorCredits.defaultGetter());
		compareNew(bachelorCredits, rand.nextDouble(301),
				bachelorCredits.defaultGetter()
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getMasterCredits")
	void getMasterCreditsTest() {
		compareCurrent(masterCredits, masterCredits.defaultGetter());
		compareNew(masterCredits, (double) rand.nextInt(301),
				masterCredits.defaultGetter()
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getTitleOfBachelorThesis")
	void getTitleOfBachelorThesisTest() {
		compareCurrent(titleOfBachelorThesis,
				titleOfBachelorThesis.defaultGetter()
		);
		compareNew(titleOfBachelorThesis, greatTitle(),
				titleOfBachelorThesis.defaultGetter()
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getTitleOfMastersThesis")
	void getTitleOfMastersThesisTest() {
		compareCurrent(titleOfMasterThesis,
				titleOfMasterThesis.defaultGetter()
		);
		compareNew(titleOfMasterThesis, greatTitle(),
				titleOfMasterThesis.defaultGetter()
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getStartYear")
	void getStartYearTest() {
		compareCurrent(startYear, getStartYear);
		compareNew(startYear, rand.nextInt(2001, 2020),
				getStartYear
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getGraduationYear")
	void getGraduationYearTest() {
		compareCurrent(graduationYear, graduationYear.defaultGetter());
		compareNew(graduationYear, rand.nextInt(2001, 2020),
				graduationYear.defaultGetter()
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getStudyYears")
	void getStudyYearsTest() {
		// The student has not graduated
		// Default value = current year - start year (current year) = 0
		assertEquals(getStudyYears.call(student), 0,
				explain("New student object should have 0 study years")
		);

		// New starting year
		int newStartYear = rand.nextInt(minYear + 1, yearNow);
		startYear.write(student, newStartYear);
		int expectedStudyYears = yearNow - newStartYear;

		assertEquals(getStudyYears.call(student), expectedStudyYears,
				explain(("Student should have " + expectedStudyYears + " study "
						+ "years after starting in " + newStartYear + "."))
		);

		// After graduation
		graduate(student, yearNow);
		assertEquals(getStudyYears.call(student), expectedStudyYears,
				explain("Student should have " + expectedStudyYears + " study "
						+ "years after starting in " + newStartYear + " and "
						+ "graduating in " + yearNow + ".")
		);

		// Test another graduation year
		Object student2 = parameterless.instantiate();
		startYear.write(student2, minYear);
		graduate(student2, newStartYear);
		expectedStudyYears = newStartYear - minYear;
		assertEquals(getStudyYears.call(student2), expectedStudyYears,
				explain("Student should have " + expectedStudyYears + " study "
						+ "years after starting in " + minYear + " and "
						+ "graduating in " + newStartYear + ".")
		);
	}

	@Test
	@Points(category = "Getters", failDeduct = 1)
	@DisplayName("getRandomId")
	void getRandomIdTest() throws Exception {
		Range<Integer> valid = Range.closed(1, 100);
		for (int i = 0; i < 1000; i++) {
			int value = getRandomId.invoke(student);
			assertWithin(value, valid, "random id",
			explain("getId should return a value within [1,100]"));
		}

		/* 
		Disabled as this test requires the use of two param nextInt method, which requires java version 17 or higher
		List<File> files = new ArrayList<File>();

		String path = "src/main/java/dev/m3s/programming2/homework1/";
		File file = new File(path + "Student.java");
		
		files.add(file);
		
		Object[] result = runCheck("dev.m3s.checkstyle.programming2.ConstantValuesIdCheck", files);

		assertTrue((boolean) result[0], explain(result[1].toString()));
		*/

	}


	<T> void compareCurrent(Property<T> attribute, Callable<T> getter) {
		// Read the value reflectively, and compare it to the return value
		System.out.println("Comparing the return value of the getter to the "
				+ "value\nof the attribute:");
		T expected = attribute.read(student);
		assertEquals(getter.call(student), expected, explain("Incorrect return value of getter"));
		System.out.println("OK");
	}

	<T> void compareNew(Property<T> attribute, T newValue,
			Callable<T> getter) {
		System.out.println("Comparing the return value of the getter to the "
				+ "value\nof the attribute after setting a new value:");
		// Set the value reflectively,
		// bypassing setter and visibility:
		attribute.write(student, newValue);
		assertEquals(getter.call(student), newValue, 
				explain("Incorrect return value of getter"));
		System.out.println("OK");
	}
}
