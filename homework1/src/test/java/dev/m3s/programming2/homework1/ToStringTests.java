package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories(@TestCategory(value = "toString"))
class ToStringTests {

	@Test
	@Points(failDeduct = 3, category = "toString")
	@DisplayName("toString: first test")
	void toStringTest1() {
		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setSomeBachelorCredits()
				.setSomeMasterCredits()
				.setBachelorsTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setId(true)
				.setSomeBachelorCredits()
				.setSomeMasterCredits()
				.verify(false);

		MockStudent.nameless()
				.verify(false);
	}

	@Test
	@Points(failDeduct = 3, category = "toString")
	@DisplayName("toString: second test")
	void toStringTest2() {
		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setId(false)
				.setExactBachelorCredits()
				.setEnoughMasterCredits()
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
				.setExactBachelorCredits()
				.setExactMasterCredits()
				.setBachelorsTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(false);

		MockStudent.named()
				.setNamesToNull()
				.setEnoughBachelorCredits()
				.setEnoughMasterCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setId(false)
				.verify(false);
	}

	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	@Test
	@Points(failDeduct = 5, category = "toString")
	@DisplayName("toString: v2 test")
	void toStringTest1V2() {
		MockStudent.nameless()
				.setFirstName()
				.setLastName()
				.setEnoughBachelorCredits()
				.setExactMasterCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(true);

		MockStudent.named()
				.setBDate(true)
				.setBachelorsTitle()
				.setSomeBachelorCredits()
				.setSomeMasterCredits()
				.verify(true);

		MockStudent.named()
				.setBDate(false)
				.setEnoughBachelorCredits()
				.setEnoughMasterCredits()
				.setBachelorsTitle()
				.setMastersTitle()
				.setStartYear()
				.setGraduationYear()
				.verify(true);
	}
}
