package dev.m3s.programming2.homework1;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.AuditEventDefaultFormatter;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import dev.m3s.maeaettae.jreq.BooleanMethods;
import dev.m3s.maeaettae.jreq.Classes;
import dev.m3s.maeaettae.jreq.Constructors;
import dev.m3s.maeaettae.jreq.DoubleFields;
import dev.m3s.maeaettae.jreq.DoubleMethods;
import dev.m3s.maeaettae.jreq.IntFields;
import dev.m3s.maeaettae.jreq.IntMethods;
import dev.m3s.maeaettae.jreq.Methods;
import dev.m3s.maeaettae.jreq.Packages;
import dev.m3s.maeaettae.jreq.StringFields;
import dev.m3s.maeaettae.jreq.StringMethods;

class H1 {

	static final String ENV_VAR = "CI_COMMIT_TAG";
	static final String V2 = "[Hh]omework[.\\-_]?1[.\\-_][Vv]2.*";

	// Required package
	static final Packages location =
			Packages.requirePackage(H1.class.getPackageName());

	// Required class
	static final Classes classStudent = location
			.hasClass()
			.hasName("Student");

	// Required constructors
	static final Constructors parameterless = classStudent
			.hasConstructor();

	static final Constructors parameterized = classStudent
			.hasConstructor()
			.hasParameters(String.class, String.class);

	// Shared requirements
	static final StringFields STRING_FIELD = classStudent
			.stringField()
			.isPrivate();

	static final IntFields INT_FIELD = classStudent
			.intField()
			.isPrivate();

	static final DoubleFields DOUBLE_FIELD = classStudent
			.doubleField()
			.isPrivate();

	// Required fields
	static final StringFields firstName = STRING_FIELD
			.hasName("firstName");

	static final StringFields lastName = STRING_FIELD
			.hasName("lastName");

	static final IntFields id = INT_FIELD
			.hasName("id");

	static final DoubleFields bachelorCredits = DOUBLE_FIELD
			.hasName("bachelorCredits",
					"bachelors?Credits");

	static final DoubleFields masterCredits = DOUBLE_FIELD
			.hasName("masterCredits",
					"masters?Credits");

	static final StringFields titleOfBachelorThesis = STRING_FIELD
			.hasName("titleOfBachelorThesis",
					"titleOfBachelors?Thesis");

	static final StringFields titleOfMasterThesis = STRING_FIELD
			.hasName("titleOfMastersThesis",
					"titleOfMasters?Thesis");

	static final IntFields startYear = INT_FIELD
			.hasName("startYear");

	static final IntFields graduationYear = INT_FIELD
			.hasName("graduationYear");

	// Required special setter (has a return value)
	static final StringMethods setGraduationYear = classStudent
			.stringMethod()
			.hasName("setGraduationYear")
			.hasParameters(int.class)
			.isPublic();

	// getstartYear <- typo in requirements, but should be allowed
	static final IntMethods getStartYear = classStudent
			.intMethod()
			.hasName("getStartYear", "get[Ss]tartYear")
			.isPublic();

	// setstartYear <- typo in requirements, but should be allowed
	static final Methods setStartYear = classStudent
			.hasMethod()
			.hasName("setStartYear", "set[Ss]tartYear")
			.hasParameters(int.class)
			.hasReturnType(void.class)
			.isPublic();

	// Required methods
	static final BooleanMethods hasGraduated = classStudent
			.booleanMethod()
			.hasName("hasGraduated")
			.isPublic();

	static final BooleanMethods canGraduate = classStudent
			.booleanMethod()
			.isPrivate()
			.hasName("canGraduate");

	static final IntMethods getStudyYears = classStudent
			.intMethod()
			.hasName("getStudyYears")
			.isPublic();

	static final IntMethods getRandomId = classStudent
			.intMethod()
			.isPrivate()
			.hasName("getRandomId", "getRandomI[Dd]");

	// ************************** FOR V2 ******************************
	// Reflected field
	static final StringFields birthDate = classStudent
			.stringField()
			.hasName("birthDate", "birth[Dd]ate")
			.isPrivate();

	// Helper builder
	static final BooleanMethods PRIVATE_BOOLEAN = classStudent
			.booleanMethod()
			.isPrivate();

	// Reflected setter
	static final StringMethods setPersonID = classStudent
			.stringMethod()
			.hasName("setPersonID", "setPersonI[Dd]")
			.hasParameter(String.class)
			.isPublic();

	// Reflected methods
	static final StringMethods toString = classStudent
			.stringMethod()
			.hasName("toString");

	static final BooleanMethods checkPersonIDNumber = PRIVATE_BOOLEAN
			.hasName("checkPersonIDNumber",
					"checkPersonI[Dd]Number")
			.hasParameter(String.class);

	static final BooleanMethods checkLeapYear = PRIVATE_BOOLEAN
			.hasName("checkLeapYear")
			.hasParameter(int.class);

	static final BooleanMethods checkValidCharacter = PRIVATE_BOOLEAN
			.hasName("checkValidCharacter")
			.hasParameter(String.class);

	static final BooleanMethods checkBirthdate = PRIVATE_BOOLEAN
			.hasName("checkBirthdate", "checkBirth[Dd]ate")
			.hasParameter(String.class);

	static final Pattern NO_NAME_PATTERN = Pattern.compile(
			"\"?No\\s+name\"?", Pattern.CASE_INSENSITIVE);

	static final Pattern NO_TITLE_PATTERN = Pattern.compile(
			"\"?No\\s+title\"?", Pattern.CASE_INSENSITIVE);

	static final Pattern NOT_AVAILABLE_PATTERN = Pattern.compile(
			"\"?Not\\s+available[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	static final Pattern CHECK_REQUIRED_STUDIES_PATTERN = Pattern.compile(
			"\"?Check\\s+(the\\s+)?required\\s+studies[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	static final Pattern CHECK_GRADUATION_YEAR_PATTERN = Pattern.compile(
			"\"?Check\\s+(the\\s+)?graduation\\s+year[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	static final Pattern INVALID_BIRTHDAY_PATTERN = Pattern.compile(
			"\"?Invalid\\s+birthday[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	static final Pattern INCORRECT_CHECK_MARK_PATTERN = Pattern.compile(
			"\"?Incorrect\\s+check\\s*mark[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	static final Pattern OK_PATTERN = Pattern.compile(
			"\"?Ok[.!\"]*", Pattern.CASE_INSENSITIVE);

	static final String CHECK_REQUIRED_STUDIES = "Check the required studies";
	static final String CHECK_GRADUATION_YEAR = "Check graduation year";
	static final String OK = "Ok";

	static final int yearNow = LocalDate.now()
			.getYear();
	static final int minYear = 2001;

	static final Random rand = new Random();
	static final String seeConsoleOutput =
			"(<== see Console output for context)";

	static String randomDate() {
		int dd = rand.nextInt(28) + 1;
		int MM = rand.nextInt(12) + 1;
		int yyyy = rand.nextInt(1970, 2070);
		return String.format("%02d.%02d.%d", dd, MM, yyyy);
	}

	static char randomLetter() {
		return (char) (new Random().nextInt(26) + 'a');
	}

	static void graduate(Object student, int year) {
		System.out.println("Graduating...");
		bachelorCredits.callDefaultSetter(student, 180.);
		masterCredits.callDefaultSetter(student, 120.);
		titleOfBachelorThesis.callDefaultSetter(student,
				"Bachelor's thesis title"
		);
		titleOfMasterThesis.callDefaultSetter(student,
				"Master's thesis " + "title"
		);
		System.out.println(setGraduationYear.call(student, year));
	}

	static boolean canGraduate(Object student) {
		int graduationYearNow = graduationYear.read(student);
		return bachelorCredits.read(student) >= 180
				&& masterCredits.read(student) >= 120
				&& graduationYearNow > startYear.read(student)
				&& graduationYearNow <= yearNow;
	}

	static boolean isValidGraduationYear(int year) {
		return minYear <= year && year <= yearNow;
	}

	static void setEnoughCredits(Object forStudent, DoubleFields attribute) {
		attribute.callDefaultSetter(forStudent,
				(double) rand.nextInt(180, 301)
		);
	}

	// Set just enough credits for a bachelor's degree
	static void setEnoughEdgeCreditsBachelor(Object forStudent, DoubleFields attribute) {
		attribute.callDefaultSetter(forStudent, (double)180);
	}

	// Set just enough credits for a master's degree
	static void setEnoughEdgeCreditsMaster(Object forStudent, DoubleFields attribute) {
		attribute.callDefaultSetter(forStudent, (double)120);
	}
	// Set just too little credits for a bachelor's degree
	static void setEdgeFailCreditsBachelor(Object forStudent, DoubleFields attribute) {
		attribute.callDefaultSetter(forStudent, (double)179);
	}

	// Set just too little credits for a master's degree
	static void setEdgeFailCreditsMaster(Object forStudent, DoubleFields attribute) {
		attribute.callDefaultSetter(forStudent, (double)119);
	}

	static void setTitle(Object forStudent, StringFields attribute) {
		attribute.callDefaultSetter(forStudent, greatTitle());
	}

	static String randomString(int length) {
		return rand.ints(length, 'a', 'a' + 26)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint,
						StringBuilder::append
				)
				.toString();
	}

	static String greatTitle() {
		// Create a mutable ArrayList for shuffling
		List<String> words = new ArrayList<>(
				List.of("The", "Bachelor", "Masters", "Thesis", "Title", "How",
						"To", "Survive", "Happy", "Ending", "New", "Exciting",
						"Purpose", "Of", "Life", "Christmas", "Most",
						"Wonderful", "Time", "Year", "Dreaming"
				));
		Collections.shuffle(words);
		return words.stream()
				.limit(rand.nextInt(3) + 3)
				.reduce("", (a, b) -> a + ' ' + b)
				.stripLeading();
	}

	static String makePersonIDWithIncorrectChecksum() {
		String personId = makePersonID();
		char correctCC = personId.charAt(10);
		String validCCs = "0123456789ABCDEFHJKLMNPRSTUVWXY";
		char incorrectCC = validCCs.charAt(
				(validCCs.indexOf(correctCC) + rand.nextInt(1, 31)) % 31);
		return personId.substring(0, 10) + incorrectCC;
	}

	static String makePersonID() {
		return makePersonID("-+A".charAt(rand.nextInt(3)));
	}

	static String makePersonID(char centurySign) {
		String dd, mm, yy;
		char checksum;

		dd = pad(rand.nextInt(28) + 1, 2);
		mm = pad(rand.nextInt(12) + 1, 2);

		if (centurySign == 'A') {
			int yyNow = (LocalDate.now()
					.getYear() % 1000 % 100);
			yy = pad(rand.nextInt(yyNow), 2);
		} else {
			yy = pad(rand.nextInt(100), 2);
		}

		String individualNumber = pad(rand.nextInt(898) + 2, 3);
		int remainder = Integer.parseInt(dd + mm + yy + individualNumber) % 31;
		checksum = "0123456789ABCDEFHJKLMNPRSTUVWXY".charAt(remainder);
		return dd + mm + yy + centurySign + individualNumber + checksum;
	}

	static String pad(int paddee, int amount) {
		StringBuilder padded = new StringBuilder(Integer.toString(paddee));
		for (int i = padded.length(); i < amount; i++) {
			padded.insert(0, '0');
		}
		return padded.toString();
	}

	static String personIdToBirthDate(String id) {
		String yy = switch (id.charAt(6)) {
			case '+' -> "18";
			case '-' -> "19";
			default -> "20";
		};
		return id.substring(0, 2) + '.' + id.substring(2, 4) + '.' + yy
				+ id.substring(4, 6);
	}

	
	// Run singular checkstyle check
	static Object[] runCheck(String checkName, List<File> files) throws Exception {

        for (File currentFile : files) {
            if (!currentFile.exists()) {
                fail(String.format("File %s not found", currentFile.toString()));
            }
        }
		
		boolean checkPassed = true;
        String errorMessage = "";

		ByteArrayOutputStream sos = new ByteArrayOutputStream();
		AuditEventDefaultFormatter formatter = new AuditEventDefaultFormatter();
        AuditListener listener = new DefaultLogger(sos, OutputStreamOptions.CLOSE, sos, OutputStreamOptions.CLOSE, formatter);

		DefaultConfiguration configuration = new DefaultConfiguration("CustomConfig");
		DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
		configuration.addChild(treeWalker);
		treeWalker.addChild(new DefaultConfiguration(checkName));

		Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(configuration);
        checker.addListener(listener);

		if (checker.process(files) > 0){
			checkPassed = false;
			errorMessage = extractErrorMessage(sos.toString());
		}

        checker.destroy();

		return new Object[]{checkPassed, errorMessage};
    }

	// Extract the error message from the output string using regex
	static String extractErrorMessage(String output) {
        Pattern startPattern = Pattern.compile("\\[ERROR\\]");
        Pattern endPattern = Pattern.compile("\\[([^\\]]+)\\]");

		Matcher matcher = startPattern.matcher(output);
		String modifiedText = matcher.replaceAll("");
		matcher = endPattern.matcher(output);
		modifiedText = matcher.replaceAll("");

		String errorMessage = "";
		
        String[] lines = modifiedText.split("\n");

		boolean multiple_errors = lines.length > 3;

		if (multiple_errors) {
			errorMessage += "Multiple errors found:";
		}

		for (int i = 1; i < lines.length - 1; i++) {
			String line = lines[i];
			String[] parts = line.split(": ", 2);
			if (parts.length > 1) {
				errorMessage += "\n" + parts[1];
			}
		}

        return errorMessage;
    }
}
