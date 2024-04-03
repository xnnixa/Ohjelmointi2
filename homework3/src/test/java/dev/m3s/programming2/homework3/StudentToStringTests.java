package dev.m3s.programming2.homework3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories({ @TestCategory("Student toString tests") })
class StudentToStringTests {

	@Test
	@DisplayName("Test toString")
	void toStringTests() {

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
