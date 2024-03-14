package dev.m3s.programming2.homework2;

import dev.m3s.maeaettae.jreq.Fields;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

@TestCategories({ @TestCategory("ConstantValues") })
public class ConstantValuesTests {

	private final Fields constant = location.hasClass()
			.hasName("ConstantValues")
			.hasField()
			.isPublic()
			.isStatic()
			.isFinal();

	@Test
	@DisplayName("ConstantValues")
	void constantValuesTests() {
		// NO_NAME = "No name"
		assertMatches(constant.hasType(String.class)
						.hasName("NO_NAME")
						.read(null), NO_NAME_PATTERN,
				"constant NO_NAME has invalid value"
		);
		// NO_TITLE = "No title"
		assertMatches(constant.hasType(String.class)
						.hasName("NO_TITLE")
						.read(null), NO_TITLE_PATTERN,
				"constant NO_TITLE has invalid value"
		);
		// NO_BIRTHDATE = "Not available"
		assertMatches(constant.hasType(String.class)
						.hasName("NO_BIRTHDATE")
						.read(null), NOT_AVAILABLE_PATTERN,
				"constant NO_BIRTHDATE has invalid value"
		);
		// INVALID_BIRTHDAY = "Invalid birthday!"
		assertMatches(constant.hasType(String.class)
						.hasName("INVALID_BIRTHDAY")
						.read(null), INVALID_BIRTHDAY_PATTERN,
				"constant INVALID_BIRTHDAY has invalid value"
		);
		// INCORRECT_CHECKMARK = "Incorrect check mark!"
		assertMatches(constant.hasType(String.class)
						.hasName("INCORRECT_CHECKMARK")
						.read(null), INCORRECT_CHECK_MARK_PATTERN,
				"constant INCORRECT_CHECKMARK has invalid value"
		);
		// MIN_ID = 1
		assertEquals(constant.hasType(int.class)
				.hasName("MIN_ID")
				.read(null), 1, explain("constant MIN_ID has invalid value"));
		// MAX_ID = 100
		assertEquals(constant.hasType(int.class)
				.hasName("MAX_ID")
				.read(null), 100, explain("constant MAX_ID has invalid value"));
		// MIN_CREDITS = 0.0
		assertEquals(constant.hasType(double.class)
						.hasName("MIN_CREDITS")
						.read(null), 0.0,
				explain("constant MIN_CREDIT has invalid value")
		);
		// MAX_CREDITS = 300.0
		assertEquals(constant.hasType(double.class)
						.hasName("MAX_CREDITS")
						.read(null), 300.0,
				explain("constant MAX_CREDITS has invalid value")
		);
		// BACHELOR_CREDITS = 180.0
		assertEquals(constant.hasType(double.class)
						.hasName("BACHELOR_CREDITS")
						.read(null), 180.0,
				explain("constant BACHELOR_CREDITS has invalid value")
		);
		// MASTER_CREDITS = 120.0
		assertEquals(constant.hasType(double.class)
						.hasName("MASTER_CREDITS")
						.read(null), 120.0,
				explain("constant MASTER_CREDITS has invalid value")
		);
		// MAX_COURSE_CREDITS = 55.0
		assertEquals(constant.hasType(double.class)
						.hasName("MAX_COURSE_CREDITS")
						.read(null), 55.0,
				explain("constant MAX_COURSE_CREDITS has invalid value")
		);
		// MIN_PERIOD = 1
		assertEquals(constant.hasType(int.class)
						.hasName("MIN_PERIOD")
						.read(null), 1,
				explain("constant MIN_PERIOD has invalid value")
		);
		// MAX_PERIOD = 5
		assertEquals(constant.hasType(int.class)
						.hasName("MAX_PERIOD")
						.read(null), 5,
				explain("constant MAX_PERIOD has invalid value")
		);
		// BACHELOR_TYPE = 0
		assertEquals(constant.hasType(int.class)
						.hasName("BACHELOR_TYPE")
						.read(null), 0,
				explain("constant BACHELOR_TYPE has invalid value")
		);
		// MASTER_TYPE = 1
		assertEquals(constant.hasType(int.class)
						.hasName("MASTER_TYPE")
						.read(null), 1,
				explain("constant MASTER_TYPE has invalid value")
		);
		// OPTIONAL = 0
		assertEquals(constant.hasType(int.class)
				.hasName("OPTIONAL")
				.read(null), 0, explain("constant OPTIONAL has invalid value"));
		// MANDATORY = 1
		assertEquals(constant.hasType(int.class)
						.hasName("MANDATORY")
						.read(null), 1,
				explain("constant MANDATORY has invalid value")
		);
		// ALL = 2
		assertEquals(constant.hasType(int.class)
				.hasName("ALL")
				.read(null), 2, explain("constant ALL has invalid value"));
		// MIN_GRADE = 0
		assertEquals(constant.hasType(int.class)
						.hasName("MIN_GRADE")
						.read(null), 0,
				explain("constant MIN_GRADE has invalid value")
		);
		// MAX_GRADE = 5
		assertEquals(constant.hasType(int.class)
						.hasName("MAX_GRADE")
						.read(null), 5,
				explain("constant MAX_GRADE has invalid value")
		);
		// GRADE_FAILED = 'F'
		assertEquals(constant.hasType(char.class)
						.hasName("GRADE_FAILED")
						.read(null), 'F',
				explain("constant GRADE_FAILED has invalid value")
		);
		// GRADE_ACCEPTED = 'A'
		assertEquals(constant.hasType(char.class)
						.hasName("GRADE_ACCEPTED")
						.read(null), 'A',
				explain("constant GRADE_ACCEPTED has invalid value")
		);
	}
}
