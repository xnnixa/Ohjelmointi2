package dev.m3s.programming2.homework2;

import dev.m3s.maeaettae.jreq.Callable;
import dev.m3s.maeaettae.jreq.IntMethods;
import dev.m3s.maeaettae.jreq.Range;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

@TestCategories({ @TestCategory("StudentCourse") })
public class StudentCourseTests {

	private Object studentCourse;

	@BeforeEach
	public void setUp() {
		studentCourse = randomStudentCourse(false);
	}

	@Test
	@DisplayName("Parameterless constructor")
	void parameterlessConstructorTest() {
		// Parameterized constructor is used (= tested) in setUp()
		paramlessStudentCourse.create();
	}

	@Test
	@DisplayName("course")
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
	@DisplayName("grade (numeric)")
	void gradeNumNumericTests() {
		IntMethods getter = gradeNum.defaultGetter();
		studentCourse = threeParamsStudentCourse.create(
				randomCourse(true, null, false), -1, -1);
		// setGrade([0,5]) -> gradeNum
		assertTrue(gradeNum.matchesWithin(setGrade, studentCourse,
				Range.closed(0, 5)), 
				explain("Incorrect gradeNum value").expected("value within [0,5]").but("was: " + gradeNum.read(studentCourse)));

		// yearCompleted after setGrade
		assertEquals(yearCompleted.read(studentCourse), yearNow,
				explain("Expected year to be set to the current year.")
		);

		// random grade
		int newGrade = rand.nextInt(6);
		// gradeNum -> getGradeNum
		gradeNum.write(studentCourse, newGrade);
		assertEquals(getter.call(studentCourse),newGrade,
				explain("The return value of getGradeNum was incorrect"));

		// different grade
		newGrade = (newGrade + rand.nextInt(1, 6)) % 6;
		// setGrade -> getGradeNum
		assertTrue(getter.returnsAfter(setGrade, studentCourse, newGrade), 
				explain("Incorrect return value of getGradeNum").expected(Character.toString(newGrade)).but("was: " + Character.toString(getter.invoke(studentCourse))));

		// setGrade(invalid) -> getGradeNum
		assertFalse(getter.returnsAfter(setGrade, studentCourse, (int) 'A'), 
				explain("Invalid return value of getGradeNum").expected(Character.toString('A')).but("was: " + Character.toString(getter.invoke(studentCourse))));
		assertFalse(getter.returnsAfter(setGrade, studentCourse, (int) 'F'),
				expectedUnchanged("setGrade", Character.toString('F'), "after trying to setGrade to invalid values")
				.expected(Character.toString('F')).but("was: " + Character.toString(getter.invoke(studentCourse))));

		// Expecting unchanged gradeNum
		assertEquals(gradeNum.read(studentCourse), newGrade,
				expectedUnchanged("gradeNum", newGrade, "after trying to set gradeNum to invalid values")
		);
	}

	@Test
	@DisplayName("grade (letter)")
	void gradeNumLetterTests() {
		IntMethods getter = gradeNum.defaultGetter();
		studentCourse = threeParamsStudentCourse.create(
				randomCourse(false, null, false), 0, 0);
		// setGrade(F) -> gradeNum
		assertTrue(gradeNum.matchesAfter(setGrade, studentCourse, (int) 'F'),
				explain("Incorrect gradeNum value").expected(Character.toString('F')).but("was: " + Character.toString(gradeNum.read(studentCourse))));

		// yearCompleted after setGrade
		assertEquals(yearCompleted.read(studentCourse), yearNow,
				explain("Expected year to be set to the current year.")
		);

		// gradeNum -> getGradeNum
		assertTrue(getter.returns(studentCourse,
				gradeNum.write(studentCourse, (int) 'A')), 
				explain("Incorrect return value of getGradeNum").expected(Character.toString('A')).but("was: " + Character.toString(getter.call(studentCourse))
		));

		// setGrade -> getGradeNum
		for (int i = 0; i < 6; i++) {
			assertFalse(getter.returnsAfter(setGrade, studentCourse, i),
					explain("Incorrect return value of getGradeNum").expected(Character.toString(i)).but("was: " + Character.toString(getter.call(studentCourse))));
		}
		// Expecting unchanged gradeNum
		assertTrue(gradeNum.matches(studentCourse, (int) 'A'),
				expectedUnchanged("gradeNum", 'A', "after trying to set gradeNum to invalid values")
				.expected(Character.toString('A')).but("was: " + gradeNum.read(studentCourse)));

	}

	@Test
	@DisplayName("yearCompleted")
	void yearCompletedTests() {
		Object randCourse = randomCourse(false, null, false);
		int invalidYear = rand.nextInt(2000);
		int invalidGrade = rand.nextInt(6, 60);
		System.out.format(
				"Creating new StudentCourse( %s, %d, %d ) (letter graded) %n",
				randCourse, invalidYear, invalidGrade
		);
		studentCourse = threeParamsStudentCourse.create(randCourse,
				invalidGrade, invalidYear
		);

		setGrade.call(studentCourse, 'A');
		// yearCompleted after setGrade
		assertTrue(yearCompleted.matches(studentCourse, yearNow),
				expectedUnchanged("yearCompleted", yearNow, "after trying to set yearCompleted to invalid value")
				.expected(Integer.toString(yearNow)).but("was: " + yearCompleted.read(studentCourse)));

		assertTrue(getYear.returns(studentCourse, yearNow),
				expectedUnchanged("return value of getYear", yearNow, "after trying to set yearCompleted to invalid value")
				.expected(Integer.toString(yearNow)).but("was: " + getYear.call(studentCourse)));
		
		yearCompleted.matchesWithin(setYear, studentCourse,
				Range.leftOpen(2000, yearNow)
		);
		int newYear = rand.nextInt(minYear, yearNow);
		assertTrue(getYear.returnsAfter(setYear, studentCourse, newYear),
				explain("Incorrect getYear value").expected(Integer.toString(newYear)).but("was: " + yearCompleted.read(studentCourse)));
		setYear.call(studentCourse, -1);
		// Expecting unchanged yearCompleted
		assertTrue(yearCompleted.matches(studentCourse, newYear),
				expectedUnchanged("yearCompleted", newYear, "after trying to set yearCompleted to invalid value")
				.expected(Integer.toString(yearNow)).but("was: " + yearCompleted.read(studentCourse)
		));

		
	}

	@Test
	@DisplayName("checkGradeValidity")
	void checkGradeValidityTests() {
		Object newStudentCourse = randomStudentCourse(false, false);
		for (int i = 0; i < 'F' + 1; i++) {
			if (i == 'A' || i == 'F') {
				// Upper case
				assertTrue(
						checkGradeValidity.returns(newStudentCourse, true, i),
						explain("Incorrect return value of checkGradeValidity"));
				// Lower case
				assertTrue(checkGradeValidity.returns(newStudentCourse, true,
						Character.toLowerCase(i),
						explain("Incorrect return value of checkGradeValidity")
				));
				continue;
			}
			assertTrue(checkGradeValidity.returns(newStudentCourse, false, i),
					explain("Incorrect return value of checkGradeValidity"));
		}
		newStudentCourse = randomStudentCourse(true, false);
		for (int i = 0; i < 'F' + 1; i++) {
			if (i <= 5) {
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
	@DisplayName("isPassed (numeric)")
	void isPassedNumericTests() {
		studentCourse = randomStudentCourse(true, false);
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

		// setGrade('A')
		setGrade.call(studentCourse, 'A');
		// isPassed still false
		assertFalse(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", false, "after trying to set gradeNum to invalid value")
		);
		// setGrade('a')
		setGrade.call(studentCourse, 'a');
		// isPassed still false
		assertFalse(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", false, "after trying to set gradeNum to invalid value")
		);

		// isPassed to true
		setGrade.call(studentCourse, 1);
		// setGrade('F')
		setGrade.call(studentCourse, 'F');
		// isPassed still true
		assertTrue(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", true, "after trying to set gradeNum to invalid value")
		);
		// setGrade('f')
		setGrade.call(studentCourse, 'f');
		// isPassed still true
		assertTrue(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", true, "after trying to set gradeNum to invalid value")
		);
	}

	@Test
	@DisplayName("isPassed (letter)")
	void isPassedLetterTests() {
		studentCourse = randomStudentCourse(false, false);
		// Numeric grading
		setGrade.call(studentCourse, 'F');
		// After calling setGrade('F')
		assertFalse(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));
		// setGrade('A')
		setGrade.call(studentCourse, 'A');
		assertTrue(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));
		// setGrade('f')
		setGrade.call(studentCourse, 'f');
		assertFalse(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));
		// setGrade('a')
		setGrade.call(studentCourse, 'a');
		assertTrue(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));
		// setGrade('F') again to make sure it affects isPassed
		setGrade.call(studentCourse, 'F');
		assertFalse(isPassed.call(studentCourse),
				explain("Incorrect return value of isPassed"));

		// setGrade(invalid)
		setGrade.call(studentCourse, rand.nextInt(6));
		// isPassed still false
		assertFalse(isPassed.call(studentCourse),
				explain("Expected isPassed to stay false after trying to set "
						+ "gradeNum to an invalid value")
		);

		// isPassed to true
		setGrade.call(studentCourse, 'A');
		setGrade.call(studentCourse, rand.nextInt(6));
		// isPassed still true
		assertTrue(isPassed.call(studentCourse),
				expectedUnchanged("isPassed", true, "after trying to set gradeNum to invalid value")
		);
	}

	@Test
	@DisplayName("toString")
	void toStringTests() {
		StringBuilder expectedString;
		String result;
		// [0, 5]
		for (int i = 0; i < 6; i++) {
			expectedString = new StringBuilder();
			studentCourse = randomStudentCourse(i, expectedString, false);
			result = studentCourseToString.call(studentCourse);
			findIgnoringWhitespace(expectedString.toString(), result,
					"in the return value of toString()", expectedString.substring(0, 9)
			);
		}
		// A
		expectedString = new StringBuilder();
		studentCourse = randomStudentCourse('A', expectedString, false);
		result = studentCourseToString.call(studentCourse);
		findIgnoringWhitespace(expectedString.toString(), result,
				"in the return value of toString()", expectedString.substring(0, 9)
		);
		// F
		expectedString = new StringBuilder();
		studentCourse = randomStudentCourse('A', expectedString, false);
		result = studentCourseToString.call(studentCourse);
		findIgnoringWhitespace(expectedString.toString(), result,
				"in the return value of toString()", expectedString.substring(0, 9)
		);
	}

}
