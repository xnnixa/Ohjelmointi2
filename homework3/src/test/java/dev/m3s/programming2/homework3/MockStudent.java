package dev.m3s.programming2.homework3;

import static dev.m3s.programming2.homework3.H3Matcher.*;
import static dev.m3s.programming2.homework3.H3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.regex.Pattern;

class MockStudent {

	private class Expected<T> {
		private final String label;
		private T value;

		Expected(String desc, T v) {
			label = desc;
			value = v;
		}

		void set(T v) {
			value = v;
		}

		T get() {
			return value;
		}

		String regex() {
			if (value instanceof Double) {
				return String.format(Locale.ROOT, "%.1f", value);
			}
			return value.toString();
		}

		@Override
		public String toString() {
			return label + ": \"" + regex() + "\"";
		}
	}

	private final static int MAX_ID = 100;
	private final static int MIN_ID = 1;

	private final static String[] firstNames = { "Donald", "Mickey", "Minnie", "Unexpected" };
	private final static String[] lastNames = { "Duck", "Mouse", "Minnie", "Spanish inquisitor" };

	private Expected<String> fName = new Expected<>("the first name", "No name");
	private Expected<String> lName = new Expected<>("the last name", "No name");
	private Expected<Integer> validID;
	private Expected<Double> bCredits = new Expected<>("the number of bachelor's credits", 0.0);
	private Expected<Double> mCredits = new Expected<>("the number of master's credits", 0.0);

	private List<Object> studentMasterCourses;
	private List<Object> studentBachelorCourses;

	private Expected<Double> totalGPA = new Expected<>("the total GPA", 0d);
	private Expected<Double> bachelorGPA = new Expected<>("the bachelor's GPA", 0d);
	private Expected<Double> masterGPA = new Expected<>("the master's GPA", 0d);

	private Expected<String> bTitle = new Expected<>("the title of bachelor's thesis", "No title");
	private Expected<String> mTitle = new Expected<>("the title of master's thesis", "No title");
	private Expected<Integer> sYear = new Expected<>("the starting year", yearNow);
	private Expected<Integer> gYear = new Expected<>("the graduation year", 0);
	private Expected<String> bDate = new Expected<>("the date of birth", "Not available");

	private Object student;
	private int valid = 0;
	private String original;
	private String output;

	private MockStudent(Object std) {
		student = std;
		studentMasterCourses = new ArrayList<>();
		studentBachelorCourses = new ArrayList<>();
	}

	static MockStudent nameless() {
		System.out.println("Creating new Student( )");
		// ? Should this use the parameterless constructor?
		return new MockStudent(twoParamStudent.instantiate(null, (Object) null));
	}

	static MockStudent named() {
		String fisrtName = firstNames[rand.nextInt(firstNames.length)];
		String lastName = lastNames[rand.nextInt(lastNames.length)];
		System.out.println(String.format("Creating new Student( %s, %s )", fisrtName, lastName));
		MockStudent mock = new MockStudent(twoParamStudent.instantiate(fisrtName, lastName));
		mock.fName.set(fisrtName);
		mock.lName.set(lastName);
		return mock;
	}

	MockStudent setFirstName() {
		fName.set(firstNames[rand.nextInt(firstNames.length)]);
		firstName.callDefaultSetter(student, fName.get());
		return this;
	}

	MockStudent setLastName() {
		lName.set(lastNames[rand.nextInt(lastNames.length)]);
		lastName.callDefaultSetter(student, lName.get());
		return this;
	}

	MockStudent setNamesToNull() {
		firstName.callDefaultSetter(student, null);
		lastName.callDefaultSetter(student, null);
		return this;
	}

	MockStudent setId(boolean valid) {
		int newId = valid ? rand.nextInt(MIN_ID, MAX_ID + 1)
				: rand.nextInt(MAX_ID + 1, MAX_ID + 1000);
		if (valid) {
			validID = new Expected<>("the student id", newId);
		}
		id.callDefaultSetter(student, newId);
		return this;
	}

	MockStudent setSomeBachelorsCredits() {
		bCredits.set((double) rand.nextInt(180));
		studentBachelorCourses.addAll(setCredits(student, 0, bCredits.get()));
		return this;
	}

	MockStudent setSomeMastersCredits() {
		mCredits.set((double) rand.nextInt(120));
		studentMasterCourses.addAll(setCredits(student, 1, mCredits.get()));
		return this;
	}

	MockStudent setEnoughBachelorsCredits() {
		bCredits.set((double) rand.nextInt(180, 300));
		studentBachelorCourses.addAll(setCreditsByType(student, 0, BACHELOR_MANDATORY, MANDATORY));
		studentBachelorCourses.addAll(setCreditsByType(student, 0, bCredits.get() - BACHELOR_MANDATORY, OPTIONAL));
		valid |= 1;
		return this;
	}

	MockStudent setEnoughMastersCredits() {
		mCredits.set((double) rand.nextInt(120, 300));
		studentMasterCourses.addAll(setCreditsByType(student, 1, MASTER_MANDATORY, MANDATORY));
		studentMasterCourses.addAll(setCreditsByType(student, 1, mCredits.get() - MASTER_MANDATORY, OPTIONAL));
		valid |= 2;
		return this;
	}

	MockStudent setExactBachelorsCredits() {
		bCredits.set(180d);
		studentBachelorCourses.addAll(setCreditsByType(student, 0, BACHELOR_MANDATORY, MANDATORY));
		studentBachelorCourses.addAll(setCredits(student, 0, BACHELOR_CREDITS - BACHELOR_MANDATORY));
		valid |= 1;
		return this;
	}

	MockStudent setExactMastersCredits() {
		mCredits.set(120d);
		studentMasterCourses.addAll(setCreditsByType(student, 1, MASTER_MANDATORY, MANDATORY));
		studentMasterCourses.addAll(setCredits(student, 1, MASTER_CREDITS - MASTER_MANDATORY));
		valid |= 2;
		return this;
	}

	MockStudent setBachelorsTitle() {
		bTitle.set(greatTitle());
		setTitleOfThesis.call(student, 0, bTitle.get());
		valid |= 4;
		return this;
	}

	MockStudent setMastersTitle() {
		mTitle.set(greatTitle());
		setTitleOfThesis.call(student, 1, mTitle.get());
		valid |= 8;
		return this;
	}

	MockStudent setStartYear() {
		sYear.set(rand.nextInt(2001, yearNow));
		setStartYear.call(student, sYear.get());
		valid |= 16;
		return this;
	}

	MockStudent setGraduationYear() {
		gYear.set(rand.nextInt(sYear.get(), yearNow + 1));
		if (valid == 0b11111) {
			valid |= 32;
		}
		setGraduationYear.call(student, gYear.get());
		return this;
	}

	MockStudent setBDate(boolean valid) {
		String newId;
		if (valid) {
			newId = makePersonID();
			bDate.set(personIdToBirthDate(newId));
		} else {
			newId = makePersonIDWithIncorrectChecksum();
		}
		setBirthDate.call(student, newId);
		return this;
	}

	void computeGPAs() {
		double sumMasterGrades = 0.0;
		double sumBachelorGrades = 0.0;
		int countBachelorCourses = 0;
		int countMasterCourses = 0;

		for (Object sc : studentBachelorCourses) {
			Object course = getCourse.invoke(sc);
			if (sc == null)
				continue;
			if (isNumericGrade.invoke(course)) {
				countBachelorCourses++;
				sumBachelorGrades += gradeNum.callDefaultGetter(sc);
			}
		}

		for (Object sc : studentMasterCourses) {
			Object course = getCourse.invoke(sc);
			if (sc == null)
				continue;
			if (isNumericGrade.invoke(course)) {
				countMasterCourses++;
				sumMasterGrades += gradeNum.callDefaultGetter(sc);
			}
		}

		// GPA is rounded to 2 decimal places
		// ? Is this required?
		bachelorGPA.set(countBachelorCourses != 0 ? roundGPA(sumBachelorGrades, countBachelorCourses) : 0.0);
		masterGPA.set(countMasterCourses != 0 ? roundGPA(sumMasterGrades, countMasterCourses) : 0.0);
		totalGPA.set((countBachelorCourses + countMasterCourses) != 0
				? roundGPA(sumBachelorGrades + sumMasterGrades, countBachelorCourses + countMasterCourses)
				: 0.0);
	}

	private double roundGPA(double grades, int courses) {
		return Math.round((grades / courses) * 100.0) / 100.0;	}

	void verify(boolean v2) {
		original = studentToString.call(student);

		computeGPAs();

		Expected<String> totalGPA_string = new Expected<>("the total GPA", totalGPA.get().toString());
		Expected<String> bachelorGPA_string = new Expected<>("the bachelor's GPA", bachelorGPA.get().toString());
		Expected<String> masterGPA_string = new Expected<>("the master's GPA", masterGPA.get().toString());
		
		System.out.println(List.of(fName, lName, bCredits, mCredits, bTitle,
		mTitle, sYear, totalGPA_string, bachelorGPA_string, masterGPA_string));

		for (Expected<?> e : List.of(fName, lName, bCredits, mCredits, bTitle,
				mTitle, sYear, totalGPA_string, bachelorGPA_string, masterGPA_string)) {
			match(e, original);
		}

		if (validID != null) {
			match(validID, original);
		}

		if (v2) {
			match(bDate, original);
		}
	}

	String failureMessage(Object expected, String context, Pattern pattern) {
		StringJoiner printed = new StringJoiner("\n");
		int i = 0;
		int stride = 80;
		while (i < output.length()) {
			int rem = output.length() - i;
			int next = Math.min(stride, rem);
			printed.add(output.substring(i, i + next));
			i += next;
		}
		String preview;
		int previewLen = 300;
		if (original.length() <= previewLen) {
			preview = "**********************************"
					+ System.lineSeparator()
					+ original
					+ System.lineSeparator()
					+ "**********************************";
		} else {
			preview = "*************(preview)*************"
					+ System.lineSeparator()
					+ original.substring(0, Math.min(previewLen, original.length()))
					+ " . . . "
					+ System.lineSeparator()
					+ "*************(preview)*************";
		}

		return String.format("The return value of toString( ) was:%n%s%nIt was expected to contain"
				+ " %s: \"%s\".%nAttempted to find \"%s\" in the following:%n"
				+ "*******************************************************************************************"
				+ "%n%s%n"
				+ "*******************************************************************************************%n"
				+ "(<== see Console output for additional context)",
				preview, context, expected, pattern, printed.toString());
	}

	static void match(Expected<?> a, String where) {
		String what = a.toString();
		String pattern = a.regex();
		String actualTarget = where.replaceAll("\\s", "");
		String shownPattern = pattern.replaceAll(Pattern.quote("\\n"), "\n")
				.replaceAll(Pattern.quote("\\p{Space}*"), "")
				.replaceAll(Pattern.quote("\\p{Space}+"), " ")
				.replaceAll(Pattern.quote("\\p{Space}"), " ");
		String actualPattern = shownPattern.replaceAll("\\s", "");
		Pattern compiled = Pattern.compile(actualPattern, Pattern.CASE_INSENSITIVE);
		String failureMsg = String.format("%nAttempted to find %s%nin the "
				+ "return value of toString:%n"
				+ "* * * * * ( spaces, tabs, newlines and "
				+ "case are ignored ) * * * * *%n"
				+ "%s%n* * * * * * * * * * * * * * * * * * * * * * * * * * * "
				+ "* * * * * * *", what, where);
		assertEquals(compiled.matcher(actualTarget).find(), true,
				explain(failureMsg).asIs());
	}
}
