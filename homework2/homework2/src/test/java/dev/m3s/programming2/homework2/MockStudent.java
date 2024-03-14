package dev.m3s.programming2.homework2;

import static dev.m3s.programming2.homework2.H2Matcher.*;
import static dev.m3s.programming2.homework2.H2.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

	private Expected<String> bTitle = new Expected<>("the title of bachelor's thesis", "No title");
	private Expected<String> mTitle = new Expected<>("the title of master's thesis", "No title");
	private Expected<Integer> sYear = new Expected<>("the starting year", yearNow);
	private Expected<Integer> years = new Expected<>("the duration of studies", 0);
	private Expected<String> gStatus = new Expected<>("the graduation status", "not graduated");
	private Expected<String> bDate = new Expected<>("the date of birth", "Not available");

	private Object student;
	private int valid = 0;

	private MockStudent(Object std) {
		student = std;
		studentMasterCourses = new ArrayList<>();
		studentBachelorCourses = new ArrayList<>();
	}

	static MockStudent nameless() {
		System.out.println("Creating new Student( )");
		return new MockStudent(paramlessStudent.instantiate());
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

	MockStudent setSomeCredits() {
		bCredits.set((double) rand.nextInt((int) BACHELOR_CREDITS));
		setCredits(student, bCredits.get());
		return this;
	}

	MockStudent setSomeBachelorsCredits() {
		bCredits.set((double) rand.nextInt((int) BACHELOR_CREDITS));
		studentBachelorCourses.addAll(setCredits(student, 0, bCredits.get()));
		return this;
	}

	MockStudent setSomeMastersCredits() {
		mCredits.set((double) rand.nextInt((int) MASTER_CREDITS));
		studentMasterCourses.addAll(setCredits(student, 1, mCredits.get()));
		return this;
	}

	MockStudent setEnoughCredits() {
		bCredits.set((double) rand.nextInt((int) BACHELOR_CREDITS, (int) MAX_CREDITS));
		studentBachelorCourses.addAll(setCredits(student, bCredits.get()));
		valid |= 3;
		return this;
	}

	MockStudent setEnoughBachelorsCredits() {
		bCredits.set((double) rand.nextInt((int) BACHELOR_CREDITS, (int) MAX_CREDITS));
		studentBachelorCourses.addAll(setCredits(student, 0, BACHELOR_MANDATORY));
		studentBachelorCourses.addAll(setCredits(student, 0, bCredits.get() - BACHELOR_MANDATORY));
		valid |= 1;
		return this;
	}

	MockStudent setEnoughMastersCredits() {
		mCredits.set((double) rand.nextInt((int) MASTER_CREDITS, (int) MAX_CREDITS));
		studentMasterCourses.addAll(setCredits(student, 1, MASTER_MANDATORY));
		studentMasterCourses.addAll(setCredits(student, 1, mCredits.get() - MASTER_MANDATORY));
		valid |= 2;
		return this;
	}

	MockStudent setExactCredits() {
		bCredits.set(BACHELOR_CREDITS);
		studentBachelorCourses.addAll(setCredits(student, MAX_CREDITS));
		valid |= 3;
		return this;
	}

	MockStudent setExactBachelorsCredits() {
		bCredits.set(BACHELOR_CREDITS);
		studentBachelorCourses.addAll(setCredits(student, 0, BACHELOR_CREDITS));
		valid |= 1;
		return this;
	}

	MockStudent setExactMastersCredits() {
		mCredits.set(MASTER_CREDITS);
		studentMasterCourses.addAll(setCredits(student, 1, MASTER_CREDITS));
		valid |= 2;
		return this;
	}

	MockStudent setTitle() {
		bTitle.set(greatTitle());
		setTitleOfThesis.call(student, bTitle.get());
		valid |= 12;
		return this;
	}

	MockStudent setBachelorsTitle() {
		bTitle.set(greatTitle());
		V2StudentTests.setTitleOfThesis.call(student, 0, bTitle.get());
		valid |= 4;
		return this;
	}

	MockStudent setMastersTitle() {
		mTitle.set(greatTitle());
		V2StudentTests.setTitleOfThesis.call(student, 1, mTitle.get());
		valid |= 8;
		return this;
	}

	MockStudent setStartYear() {
		sYear.set(rand.nextInt(2001, yearNow));
		setStartYear.call(student, sYear.get());
		years.set(yearNow - sYear.get());
		valid |= 16;
		return this;
	}

	MockStudent setGraduationYear() {
		int gYear =rand.nextInt(sYear.get(), yearNow + 1);
		if (valid == 0b11111) {
			valid |= 32;
			gStatus.set("has graduated in " + gYear);
			years.set(gYear - sYear.get());
		}
		setGraduationYear.call(student, gYear);
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

	void verify(boolean v2) {
		String result = studentToString.call(student);

		for (Expected<?> e : List.of(fName, lName, bCredits, bTitle, sYear,
				years, gStatus)) {
			match(e, result);
		}

		if (validID != null) {
			match(validID, result);
		}

		// TODO: check V2 stuff
		if (v2) {
			for (Expected<?> e : List.of(bDate, mTitle, mCredits)) {
				match(e, result);
			}
		}
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
