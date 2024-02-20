package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.m3s.maeaettae.jreq.Fields;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@TestCategories({ @TestCategory("ConstantValues") })
class ConstantValuesTests {

	private final Fields constant = location.hasClass()
			.hasName("ConstantValues")
			.hasField()
			.isPublic()
			.isStatic()
			.isFinal();

	@Test
	@Points(failDeduct = 1, category = "ConstantValues")
	@DisplayName("ConstantValues")
	void constantValuesTests() {

		assertMatches(constant.hasType(String.class)
						.hasName("NO_NAME")
						.read(null), NO_NAME_PATTERN,
				"constant NO_NAME has invalid value"
		);

		assertMatches(constant.hasType(String.class)
						.hasName("NO_TITLE")
						.read(null), NO_TITLE_PATTERN,
				"constant NO_TITLE has invalid value"
		);

		assertMatches(constant.hasType(String.class)
						.hasName("NO_BIRTHDATE")
						.read(null), NOT_AVAILABLE_PATTERN,
				"constant NO_BIRTHDATE has invalid value"
		);

		// Use a custom matcher that does not print '<' and '>' which might
		// cause the enclosed values to not be rendered correctly in the
		// pipeline
		assertEquals(constant.hasType(int.class)
						.hasName("MIN_ID")
						.read(null),
				1, explain("constant MIN_ID has invalid value")
		);

		assertEquals(constant.hasType(int.class)
						.hasName("MAX_ID")
						.read(null),
				100, explain("constant MAX_ID has invalid value")
		);

		assertEquals(constant.hasType(double.class)
						.hasName("MIN_CREDIT", "MIN_CREDITS?")
						.read(null),
				0.0, explain("constant MIN_CREDIT has invalid value")
		);

		assertEquals(constant.hasType(double.class)
						.hasName("MAX_CREDITS")
						.read(null),
				300.0, explain("constant MAX_CREDITS has invalid value")
		);

		assertEquals(constant.hasType(double.class)
						.hasName("BACHELOR_CREDITS")
						.read(null),
				180.0, explain("constant BACHELOR_CREDITS has invalid value")
		);

		assertEquals(constant.hasType(double.class)
						.hasName("MASTER_CREDITS")
						.read(null),
				120.0, explain("constant MASTER_CREDITS has invalid value")
		);
	}

	@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
	@Test
	@Points(failDeduct = 1, category = "ConstantValues")
	@DisplayName("ConstantValues: v2")
	void constantValuesTests_V2() {

		assertMatches(constant.hasType(String.class)
						.hasName("INVALID_BIRTHDAY")
						.read(null), INVALID_BIRTHDAY_PATTERN,
				"constant INVALID_BIRTHDAY has invalid value"
		);

		assertMatches(constant.hasType(String.class)
						.hasName("INCORRECT_CHECKMARK")
						.read(null), INCORRECT_CHECK_MARK_PATTERN,
				"constant INCORRECT_CHECKMARK has invalid value"
		);
	}
}
