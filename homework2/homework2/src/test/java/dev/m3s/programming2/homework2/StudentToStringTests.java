package dev.m3s.programming2.homework2;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static dev.m3s.programming2.homework2.H2.*;

@TestCategories({ @TestCategory("Student") })
class StudentToStringTests {
	@Test
	@DisplayName("Test toString")
	 
	void toStringTestsV1() {

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setSomeCredits()
				.setTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setId(true)
				.setSomeCredits()
				.verify(false);

		MockStudent.nameless()
				.verify(false);

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setExactCredits()
				.setTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.verify(false);

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setExactCredits()
				.setTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setNamesToNull()
				.setEnoughCredits()
				.setTitle()
				.setId(false)
				.verify(false);

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setEnoughCredits()
				.setTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setBDate(true)
				.setTitle()
				.setSomeCredits()
				.verify(false);

		MockStudent.named()
				.setBDate(false)
				.setEnoughCredits()
				.setTitle()
				.setTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);
	}

	@Test
	@DisplayName("Test toString (Version 2)")

	void toStringTestsV2() {

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setSomeBachelorsCredits()
				.setSomeMastersCredits()
				.setBachelorsTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setId(true)
				.setSomeBachelorsCredits()
				.setSomeMastersCredits()
				.verify(false);

		MockStudent.nameless()
				.verify(false);

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setExactBachelorsCredits()
				.setEnoughMastersCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.verify(false);

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setExactBachelorsCredits()
				.setExactMastersCredits()
				.setBachelorsTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setNamesToNull()
				.setEnoughBachelorsCredits()
				.setEnoughMastersCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setId(false)
				.verify(false);

		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setEnoughBachelorsCredits()
				.setExactMastersCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(true);

		MockStudent.named()
				.setBDate(true)
				.setBachelorsTitle()
				.setSomeBachelorsCredits()
				.setSomeMastersCredits()
				.verify(true);

		MockStudent.named()
				.setBDate(false)
				.setEnoughBachelorsCredits()
				.setEnoughMastersCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(true);
	}
}
