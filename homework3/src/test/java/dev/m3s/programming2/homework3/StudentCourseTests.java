package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import dev.m3s.maeaettae.jreq.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories({ @TestCategory("StudentCourse") })
class StudentCourseTests {

	private Object studentCourse;

	@BeforeEach
	void setUp() {
		studentCourse = randomStudentCourse();
	}

	@Test
	@DisplayName("All attributes should be non-static")
	void requireNonStaticAttributes() {
		classStudentCourse.hasField().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("All methods should be non-static")
	void requireNonStaticMethods() {
		classStudentCourse.hasMethod().isStatic().require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Test parameterless constructor")
	void parameterlessConstructorTest() {
		paramlessStudentCourse.instantiate();
	}

	@Test
	@DisplayName("Test course")
	void courseTests() {
		Callable<?> getter = course.defaultGetter();
		Callable<?> setter = course.defaultSetter();
		Object newCourse = randomCourse();

		// setCourse -> getCourse & course
		setter.call(studentCourse, newCourse);
		assertCoursesEqual(newCourse, getter.call(studentCourse));
		assertCoursesEqual(newCourse, course.read(studentCourse));
	}

	@Test
	@DisplayName("Test gradeNum (numeric)")
	void gradeNumNumericTests() {
		IntMethods getter = gradeNum.defaultGetter();

		studentCourse = threeParamsStudentCourse.instantiate(
				randomCourse(true, null),
				-1, -1);
		// setGrade([0,5]) -> gradeNum
		assertTrue(gradeNum.matchesWithin(setGrade, studentCourse,
				Range.closed(0, 5)),
				explain("Incorrect gradeNum value").expected("value within [0,5]")
						.but("was: " + gradeNum.read(studentCourse)));

		// yearCompleted after setGrade
		assertSetterUpdatesCurrentValue(studentCourse, yearCompleted, setGrade, getter, yearNow, "gradeNum");

		// random grade
		int newGrade = rand.nextInt(6);
		// gradeNum -> getGradeNum
		assertGetterReturnsCurrentValue(studentCourse, gradeNum, getter, "gradeNum");

		assertGetterReturnsNewValue(studentCourse, gradeNum, getter, newGrade, "gradeNum");

		// different grade
		newGrade = (newGrade + rand.nextInt(1, 6)) % 6;
		// setGrade -> getGradeNum
		assertGetterReturnsSetValue(studentCourse, setGrade, getter, newGrade, "gradeNum");

		// setGrade(invalid) -> getGradeNum
		assertFalse(getter.returnsAfter(setGrade, studentCourse, (int) 'A'),
				explain("Invalid return value of getGradeNum").expected(Character.toString('A'))
						.but("was: " + Character.toString(getter.invoke(studentCourse))));
		assertFalse(getter.returnsAfter(setGrade, studentCourse, (int) 'F'),
				expectedUnchanged("setGrade", Character.toString('F'), "after trying to setGrade to invalid values")
						.expected(Character.toString('F'))
						.but("was: " + Character.toString(getter.invoke(studentCourse))));

		// Expecting unchanged gradeNum
		assertEquals(gradeNum.read(studentCourse), newGrade,
				expectedUnchanged("gradeNum", newGrade, "after trying to set gradeNum to invalid values"));
	}

	@Test
	@DisplayName("Test gradeNum (character)")
	void gradeNumLetterTests() {
		IntMethods getter = gradeNum.defaultGetter();
		studentCourse = threeParamsStudentCourse.instantiate(
				randomCourse(false, null),
				0, 0);
		// setGrade(F) -> gradeNum
		assertSetterUpdatesCurrentValue(studentCourse, gradeNum, setGrade, getter, (int) 'F', "gradeNum");

		// yearCompleted after setGrade
		assertEquals(yearCompleted.read(studentCourse), yearNow,
				explain("Expected year to be set to the current year."));

		// gradeNum -> getGradeNum
		assertGetterReturnsNewValue(studentCourse, gradeNum, getter, (int) 'A', "gradeNum");

		// setGrade -> getGradeNum
		for (int i = 0; i < 6; i++) {
			assertFalse(getter.returnsAfter(setGrade, studentCourse, i),
					explain("Incorrect return value of getGradeNum").expected(Character.toString(i))
							.but("was: " + Character.toString(getter.call(studentCourse))));
		}
		// Expecting unchanged gradeNum
		assertTrue(gradeNum.matches(studentCourse, (int) 'A'),
				expectedUnchanged("gradeNum", 'A', "after trying to set gradeNum to invalid values")
						.expected(Character.toString('A')).but("was: " + gradeNum.read(studentCourse)));
	}

	@Test
	@DisplayName("Test yearCompleted, getYear, setYear")
	void yearCompletedTests() {
		Object randCourse = randomCourse(false, null);
		int invalidYear = rand.nextInt(2000);
		int invalidGrade = rand.nextInt(6, 60);
		System.out.format("Creating new StudentCourse( %s, %d, %d ) (letter graded) %n", randCourse, invalidYear,
				invalidGrade);
		studentCourse = threeParamsStudentCourse.instantiate(randCourse, invalidGrade, invalidYear);
		setGrade.call(studentCourse, 'A');
		// yearCompleted after setGrade
		assertTrue(yearCompleted.matches(studentCourse, yearNow),
				expectedUnchanged("yearCompleted", yearNow, "after trying to set yearCompleted to invalid value")
						.expected(Integer.toString(yearNow)).but("was: " + yearCompleted.read(studentCourse)));

		assertTrue(getYear.returns(studentCourse, yearNow),
				expectedUnchanged("return value of getYear", yearNow,
						"after trying to set yearCompleted to invalid value")
						.expected(Integer.toString(yearNow)).but("was: " + getYear.call(studentCourse)));

		yearCompleted.matchesWithin(setYear, studentCourse, Range.leftOpen(2000, yearNow));
		int newYear = rand.nextInt(minYear, yearNow);
		assertTrue(getYear.returnsAfter(setYear, studentCourse, newYear),
				explain("Incorrect getYear value").expected(Integer.toString(newYear))
						.but("was: " + yearCompleted.read(studentCourse)));
		setYear.call(studentCourse, -1);
		// Expecting unchanged yearCompleted
		assertTrue(yearCompleted.matches(studentCourse, newYear),
				expectedUnchanged("yearCompleted", newYear, "after trying to set yearCompleted to invalid value")
						.expected(Integer.toString(yearNow)).but("was: " + yearCompleted.read(studentCourse)));
	}

	@Test
	@DisplayName("Test checkGradeValidity")
	void checkGradeValidityTests() {
		Object newStudentCourse = randomStudentCourse(false, null);
		for (int i = 0; i < 'F' + 1; i++) {
			if (i == 'A' || i == 'F') {
				assertTrue(
						checkGradeValidity.returns(newStudentCourse, true, i),
						explain("Incorrect return value of checkGradeValidity"));

				assertTrue(checkGradeValidity.returns(newStudentCourse, true,
						Character.toLowerCase(i),
						explain("Incorrect return value of checkGradeValidity")));
				continue;
			}
			assertTrue(checkGradeValidity.returns(newStudentCourse, false, i),
					explain("Incorrect return value of checkGradeValidity"));
		}
		newStudentCourse = randomStudentCourse(true, null);
		for (int i = 0; i < 'F' + 1; i++) {
			if (i >= 0 && i <= 5) {
				assertTrue(
						checkGradeValidity.returns(newStudentCourse, true, i),
						explain("Incorrect return value of checkGradeValidity"));
				continue;
			}
			assertTrue(checkGradeValidity.returns(newStudentCourse, false, i),
					explain("Incorrect return value of checkGradeValidity"));
		}
	}

	@Test
	@DisplayName("Test isPassed (numeric)")
	void isPassedNumericTests() {
		studentCourse = randomStudentCourse(true, null);
		// Numeric grading
		setGrade.call(studentCourse, 0);
		// After calling setGrade(0)
		assertFalse(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));
		// setGrade(1 to 5)
		for (int i = 1; i < 6; i++) {
			setGrade.call(studentCourse, i);
			assertTrue(isPassed.call(studentCourse), explain("Incorrect return value of isPassed"));
		}
		// setGrade(0) again to make sure it affects isPassed
		setGrade.call(studentCourse, 0);
		assertFalse(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));

		// setGrade(invalid)
		setGrade.call(studentCourse, 'A');
		// isPassed still false
		assertFalse(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", false, "after trying to set gradeNum to invalid value"));

		// isPassed to true
		setGrade.call(studentCourse, 1);
		setGrade.call(studentCourse, 'F');
		// isPassed still true
		assertTrue(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", true, "after trying to set isPassed to invalid value"));
	}

	@Test
	@DisplayName("Test isPassed (character)")
	void isPassedLetterTests() {
		studentCourse = randomStudentCourse(false, null);
		// Numeric grading
		setGrade.call(studentCourse, 'F');
		// After calling setGrade('F')
		assertFalse(isPassed.call(studentCourse), "Incorrect return value of isPassed");
		// setGrade('A')
		setGrade.call(studentCourse, 'A');
		assertTrue(isPassed.call(studentCourse), "Incorrect return value of isPassed");
		// setGrade('F') again to make sure it affects isPassed
		setGrade.call(studentCourse, 'F');
		assertFalse(isPassed.call(studentCourse), "Incorrect return value of isPassed");

		// setGrade(invalid)
		setGrade.call(studentCourse, rand.nextInt(6));
		// isPassed still false
		assertFalse(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", false, "after trying to set isPassed to invalid value"));

		// isPassed to true
		setGrade.call(studentCourse, 'A');
		setGrade.call(studentCourse, rand.nextInt(6));
		// isPassed still true
		assertTrue(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", true, "after trying to set gradeNum to invalid value"));
	}

	@Test
	@DisplayName("Test toString")
	void toStringTests() {
		StringBuilder expectedString;
		String returned;
		// [0, 5]
		for (int i = 0; i < 6; i++) {
			expectedString = new StringBuilder();
			studentCourse = randomStudentCourse(i, expectedString);
			returned = studentCourseToString.call(studentCourse);
			findIgnoringWhitespace(expectedString.toString(), returned, "in the return value of toString", expectedString.toString().substring(0, 7));
		}

		// A
		expectedString = new StringBuilder();
		studentCourse = randomStudentCourse('A', expectedString);
		returned = studentCourseToString.call(studentCourse);
		findIgnoringWhitespace(expectedString.toString(), returned, "in the return value of toString", expectedString.toString().substring(0, 9));

		// F
		expectedString = new StringBuilder();
		studentCourse = randomStudentCourse('A', expectedString);
		returned = studentCourseToString.call(studentCourse);
		findIgnoringWhitespace(expectedString.toString(), returned, "in the return value of toString", expectedString.toString().substring(0, 9));
	}
}