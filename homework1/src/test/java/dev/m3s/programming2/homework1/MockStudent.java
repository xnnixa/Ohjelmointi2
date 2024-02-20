package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

class MockStudent {

	private static class Expected<T> {

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

	static double REQUIRED_BACHELOR_CREDITS = 180.0;
	static double REQUIRED_MASTER_CREDITS = 120.0;
	static double MAX_CREDITS = 300.0;
	static int MAX_ID = 100;
	static int MIN_ID = 1;
	static String[] firstNames = { "Donald", "Mickey", "Minnie", "Unexpected" };
	static String[] lastNames =
			{ "Duck", "Mouse", "The Student", "Spanish Inquisitor" };

	Expected<String> fName = new Expected<>("the first name", "No name");
	Expected<String> lName = new Expected<>("the last name", "No name");
	Expected<Double> bCredits =
			new Expected<>("the number of bachelor's credits", 0.0);
	Expected<Double> mCredits =
			new Expected<>("the number of master's credits", 0.0);
	Expected<String> bTitle =
			new Expected<>("the title of bachelor's thesis", "No title");
	Expected<String> mTitle =
			new Expected<>("the title of master's thesis", "No title");
	Expected<Integer> sYear = new Expected<>("the starting year", yearNow);
	Expected<Integer> years = new Expected<>("the duration of studies", 0);
	Expected<String> gStatus =
			new Expected<>("the graduation status", "not graduated");

	Expected<Integer> validId;
	Expected<String> bDate =
			new Expected<>("the date of birth", "Not available");
	Object student;

	boolean enoughBCredits;
	boolean enoughMCredits;
	boolean bTitleSet;
	boolean mTitleSet;

	MockStudent(Object s) {
		student = s;
	}

	static MockStudent nameless() {
		System.out.println("Creating new Student( )");
		return new MockStudent(parameterless.instantiate());
	}

	static MockStudent named() {
		String fn = firstNames[rand.nextInt(firstNames.length)];
		String ln = lastNames[rand.nextInt(lastNames.length)];
		System.out.printf("Creating new Student( %s, %s )%n", ln, fn);
		MockStudent mock = new MockStudent(parameterized.instantiate(ln, fn));
		mock.fName.set(fn);
		mock.lName.set(ln);
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
				: rand.nextInt(MAX_ID + 1, MAX_ID + 1000
		);
		if (valid) {
			validId = new Expected<>("the student id", newId);
		}
		id.callDefaultSetter(student, newId);
		return this;
	}

	MockStudent setSomeBachelorCredits() {
		bCredits.set(randomCredits(1, REQUIRED_BACHELOR_CREDITS));
		bachelorCredits.callDefaultSetter(student, bCredits.get());
		return this;
	}

	MockStudent setSomeMasterCredits() {
		mCredits.set(randomCredits(1, REQUIRED_MASTER_CREDITS));
		masterCredits.callDefaultSetter(student, mCredits.get());
		return this;
	}

	MockStudent setEnoughBachelorCredits() {
		bCredits.set(randomCredits(REQUIRED_BACHELOR_CREDITS, MAX_CREDITS));
		bachelorCredits.callDefaultSetter(student, bCredits.get());
		enoughBCredits = true;
		return this;
	}

	MockStudent setEnoughMasterCredits() {
		mCredits.set(randomCredits(REQUIRED_MASTER_CREDITS, MAX_CREDITS));
		masterCredits.callDefaultSetter(student, mCredits.get());
		enoughMCredits = true;
		return this;
	}

	double randomCredits(double min, double max) {
		double credits = rand.nextDouble(min, max);
		// Keep only one decimal place:
		credits = (int) (credits * 10);
		return credits / 10;
	}

	MockStudent setExactBachelorCredits() {
		bCredits.set(REQUIRED_BACHELOR_CREDITS);
		bachelorCredits.callDefaultSetter(student, REQUIRED_BACHELOR_CREDITS);
		enoughBCredits = true;
		return this;
	}

	MockStudent setExactMasterCredits() {
		mCredits.set(REQUIRED_MASTER_CREDITS);
		masterCredits.callDefaultSetter(student, REQUIRED_MASTER_CREDITS);
		enoughMCredits = true;
		return this;
	}

	MockStudent setBachelorsTitle() {
		bTitle.set(greatTitle());
		titleOfBachelorThesis.callDefaultSetter(student, bTitle.get());
		bTitleSet = true;
		return this;
	}

	MockStudent setMastersTitle() {
		mTitle.set(greatTitle());
		titleOfMasterThesis.callDefaultSetter(student, mTitle.get());
		mTitleSet = true;
		return this;
	}

	MockStudent setStartYear() {
		sYear.set(rand.nextInt(2001, yearNow));
		setStartYear.call(student, sYear.get());
		years.set(yearNow - sYear.get());
		return this;
	}

	MockStudent setGraduationYear() {
		int gYear = rand.nextInt(sYear.get(), yearNow + 1);
		if (enoughBCredits && enoughMCredits && bTitleSet && mTitleSet) {
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
		setPersonID.call(student, newId);
		return this;
	}

	void verify(boolean v2) {
		String result = toString.call(student);
		for (Expected<?> e : List.of(fName, lName, bCredits, mCredits, bTitle,
				mTitle, years, sYear, gStatus
		)) {
			match(e, result);
		}
		if (validId != null) {
			match(validId, result);
		}
		if (v2) {
			// Date of birth
			match(bDate, result);
			match(new Expected<>("the required bachelor's credits",
					REQUIRED_BACHELOR_CREDITS
			), result);

			// bachelorCredits
			if (bCredits.get() < REQUIRED_BACHELOR_CREDITS) {
				// Missing bachelor credits
				match(new Expected<>("the bachelor's credits info", "missing"),
						result
				);
				match(new Expected<>("the missing bachelor's credits",
						REQUIRED_BACHELOR_CREDITS - bCredits.get()
				), result);
			} else {
				// Enough bachelor credits
				match(new Expected<>("the bachelor's credits info",
						"completed"
				), result);
			}

			// masterCredits
			match(new Expected<>("the required master's credits",
					REQUIRED_MASTER_CREDITS
			), result);
			if (mCredits.get() < REQUIRED_MASTER_CREDITS) {
				// Missing master credits
				match(new Expected<>("the master's credits info", "missing"),
						result
				);
				match(new Expected<>("the missing master's credits",
						REQUIRED_MASTER_CREDITS - mCredits.get()
				), result);
			} else {
				// Enough master credits
				match(new Expected<>("the master's credits info", "completed"),
						result
				);
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
		Pattern compiled =
				Pattern.compile(actualPattern, Pattern.CASE_INSENSITIVE);
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
