package dev.m3s.programming2.homework3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import com.puppycrawl.tools.checkstyle.*;

import dev.m3s.maeaettae.jreq.*;
import static dev.m3s.programming2.homework3.H3Matcher.*;
import static org.junit.jupiter.api.Assertions.fail;


final class H3 {

	static final String ENV_VAR = "CI_COMMIT_TAG";
	static final String V1 = "[Hh]omework[.\\-_]?3[.\\-_][Vv]1.*";
	static final String V2 = "[Hh]omework[.\\-_]?3[.\\-_][Vv]2.*";

	// *** REQUIRED PACKAGE ***
	static final Packages location = Packages.requirePackage(H3.class.getPackageName());

	// *** REQUIRED CLASSES ***
	static final Classes classPersonID = location.hasClass().hasName("PersonID");
	static final Classes classCourse = location.hasClass().hasName("Course");
	static final Classes classStudentCourse = location.hasClass().hasName("StudentCourse");
	static final Classes classDegree = location.hasClass().hasName("Degree");
	static final Classes classPerson = location.hasClass().hasName("Person");
	static final Classes classStudent = location.hasClass().hasName("Student");
	static final Classes classEmployee = location.hasClass().hasName("Employee");
	static final Interfaces interfacePayment = location.hasInterface().hasName("Payment");
	// V2 Class and Interfaces
	static final Classes classMonthlyPayment = location.hasClass().hasName("MonthlyPayment");
	static final Classes classHourBasedPayment = location.hasClass().hasName("HourBasedPayment");
	static final Interfaces interfaceTeacher = location.hasInterface().hasName("Teacher");
	static final Classes classAssistantTeacher = location.hasClass().hasName("AssistantTeacher");
	static final Classes classResponsibleTeacher = location.hasClass().hasName("ResponsibleTeacher");
	static final Classes classDesignatedCourse = location.hasClass().hasName("DesignatedCourse");

	// *** REQUIRED CONSTRUCTORS ***
	// PersonID default constructor
	static final Constructors paramlessPersonID = classPersonID.hasConstructor();

	// Course constructors
	static final Constructors paramlessCourse = classCourse.hasConstructor();
	static final Constructors sevenParamsCourse = classCourse.hasConstructor()
			.hasParameters(
					String.class,
					int.class)
			.hasParameter(List.of(Character.class, char.class))
			.hasParameters(
					int.class,
					int.class,
					double.class,
					boolean.class);
	static final Constructors copyCourse = classCourse.hasConstructor()
			.hasParameter(classCourse);

	// StudentCourse constructors
	static final Constructors paramlessStudentCourse = classStudentCourse.hasConstructor();
	static final Constructors threeParamsStudentCourse = classStudentCourse.hasConstructor()
			.hasParameters(
					classCourse,
					int.class,
					int.class);

	// Degree default constructor
	static final Constructors paramlessDegree = classDegree.hasConstructor();

	// Person default constructor
	static final Constructors twoParamsPerson = classPerson.hasConstructor()
			.hasParameters(
					String.class,
					String.class);

	// Student two parameter constructor
	static final Constructors twoParamStudent = classStudent.hasConstructor()
			.hasParameters(String.class, String.class);

	// Employee two parameter constructor
	static final Constructors twoParamEmployee = classEmployee
			.hasConstructor()
			.hasParameters(String.class, String.class);

	// V2
	// MonthlyPayment default constructor
	static final Constructors constructorMonthlyPayment = classMonthlyPayment.hasConstructor();
	// V2
	// HourBasedPayment – default constructor
	static final Constructors constructorHourBasedPayment = classHourBasedPayment.hasConstructor();

	// V2
	// DesignatedCourse constructors
	// default constructor
	static final Constructors paramlessDesignatedCourse = classDesignatedCourse.hasConstructor();
	// ParameterConstructor
	static final Constructors allParamDesignatedCourse = classDesignatedCourse
			.hasConstructor()
			.hasParameters(classCourse, boolean.class, int.class);

	// V2
	// AsistantTeacher Constructors
	static final Constructors twoParamAssistantTeacher = classAssistantTeacher
			.hasConstructor()
			.hasParameters(String.class, String.class);
	// V2
	// ResponsibleTeacher Constructors
	static final Constructors twoParamResponsibleTeacher = classResponsibleTeacher
			.hasConstructor()
			.hasParameters(String.class, String.class);
	// V2
	// MonthlyPayment constructor
	static final Constructors paramlessMonthlyPayment = classMonthlyPayment.hasConstructor();
	// V2
	// HourBasedPayment constructor
	static final Constructors paramlessHourBasedPayment = classHourBasedPayment.hasConstructor();

	// *** REQUIRED ATTRIBUTES ***
	// PersonID attribute
	static final StringFields birthDate = classPersonID
			.stringField()
			.hasName("birthDate", "birth[Dd]ate")
			.isPrivate();

	// Course attributes
	static final StringFields name = classCourse
			.stringField()
			.hasName("name")
			.isPrivate();

	static final StringFields courseCode = classCourse
			.stringField()
			.hasName("courseCode")
			.isPrivate();

	static final Fields courseBase = classCourse
			.hasField()
			.hasName("courseBase")
			.hasType(List.of(Character.class, char.class))
			.isPrivate();

	static final IntFields courseType = classCourse
			.intField()
			.hasName("courseType")
			.isPrivate();

	static final IntFields period = classCourse
			.intField()
			.hasName("period")
			.isPrivate();

	static final DoubleFields credits = classCourse
			.doubleField()
			.hasName("credits")
			.isPrivate();

	static final BooleanFields numericGrade = classCourse
			.booleanField()
			.hasName("numericGrade")
			.isPrivate();

	// StudentCourse attributes
	static final Fields course = classStudentCourse
			.hasField()
			.hasName("course")
			.hasType(classCourse)
			.isPrivate();

	static final IntFields gradeNum = classStudentCourse
			.intField()
			.hasName("gradeNum")
			.isPrivate();

	static final IntFields yearCompleted = classStudentCourse
			.intField()
			.hasName("yearCompleted")
			.isPrivate();

	// Degree attributes
	static final IntFields MAX_COURSES = classDegree
			.intField()
			.isStatic()
			.isFinal()
			.hasName("MAX_COURSES")
			.isPrivate();

	// NOTE: count is only in version 1
	static final IntFields count = classDegree
			.intField()
			.hasName("count")
			.isPrivate();

	static final StringFields degreeTitle = classDegree
			.stringField()
			.hasName("degreeTitle")
			.isPrivate();

	static final StringFields titleOfThesis = classDegree
			.stringField()
			.hasName("titleOfThesis")
			.isPrivate();

	static final Fields myCourses = classDegree
			.hasField()
			.hasName("myCourses")
			.hasType(Types.parameterized(List.class, classStudentCourse))
			.isPrivate();

	// Person attributes
	static final StringFields firstName = classPerson
			.stringField()
			.hasName("firstName")
			.isPrivate();

	static final StringFields lastName = classPerson
			.stringField()
			.hasName("lastName")
			.isPrivate();
	static final StringFields personBirthDate = classPerson
			.stringField()
			.hasName("birthDate", "birth[Dd]ate")
			.isPrivate();

	// Student attributes
	static final IntFields id = classStudent
			.intField()
			.hasName("id")
			.isPrivate();

	static final IntFields startYear = classStudent
			.intField()
			.hasName("startYear")
			.isPrivate();

	static final IntFields graduationYear = classStudent
			.intField()
			.hasName("graduationYear")
			.isPrivate();

	static final Fields degrees = classStudent
			.hasField()
			.hasName("degrees")
			.hasType(Types.parameterized(List.class, classDegree))
			.isPrivate();

	// Employee attributes
	static final StringFields empId = classEmployee
			.stringField()
			.hasName("empId")
			.isPrivate();

	static final IntFields empStartYear = classEmployee
			.intField()
			.hasName("startYear")
			.isPrivate();

	// V1
	static final DoubleFields salary = classEmployee
			.doubleField()
			.hasName("salary")
			.isPrivate();

	// V2
	static final Fields payment = classEmployee
			.hasField()
			.hasName("payment")
			.hasType(interfacePayment)
			.isPrivate();

	// V2
	// MonthlyPayment attributes
	static final DoubleFields salaryMonthlyPayment = classMonthlyPayment
			.doubleField()
			.hasName("salary")
			.isPrivate();

	// V2
	// HourBasedPayment attributes
	static final DoubleFields eurosPerHour = classHourBasedPayment
			.doubleField()
			.hasName("eurosPerHour")
			.isPrivate();

	static final DoubleFields hours = classHourBasedPayment
			.doubleField()
			.hasName("hours")
			.isPrivate();

	// V2
	// DesignatedCourse attributes
	static final Fields courseDesignatedCourse = classDesignatedCourse
			.hasField()
			.hasName("course")
			.hasType(classCourse)
			.isPrivate();

	static final BooleanFields responsible = classDesignatedCourse
			.booleanField()
			.hasName("responsible")
			.isPrivate();

	static final IntFields year = classDesignatedCourse
			.intField()
			.hasName("year")
			.isPrivate();

	// V2
	// AssistantTeacher attributes
	static final Fields coursesAssistanT = classAssistantTeacher
			.hasField()
			.hasName("courses")
			.hasType(Types.parameterized(List.class, classDesignatedCourse))
			.isPrivate();

	// V2
	// ResponsibleTeacher attributes
	static final Fields coursesResponsibleT = classResponsibleTeacher
			.hasField()
			.hasName("courses")
			.hasType(Types.parameterized(List.class, classDesignatedCourse))
			.isPrivate();

	// *** REQUIRED METHODS ***
	// PersonID methods
	static final StringMethods setPersonID = classPersonID
			.stringMethod()
			.hasName("setPersonID", "setPersonI[Dd]")
			.hasParameter(String.class);

	static final BooleanMethods checkPersonIDNumber = classPersonID
			.booleanMethod()
			.isPrivate()
			.hasName("checkPersonIDNumber", "checkPersonI[Dd]Number")
			.hasParameter(String.class);

	static final BooleanMethods checkLeapYear = classPersonID
			.booleanMethod()
			.isPrivate()
			.hasName("checkLeapYear")
			.hasParameter(int.class);

	static final BooleanMethods checkValidCharacter = classPersonID
			.booleanMethod()
			.isPrivate()
			.hasName("checkValidCharacter")
			.hasParameter(String.class);

	static final BooleanMethods checkBirthdate = classPersonID
			.booleanMethod()
			.isPrivate()
			.hasName("checkBirthdate", "checkBirth[Dd]ate")
			.hasParameter(String.class);

	// Course methods
	static final StringMethods getCourseTypeString = classCourse
			.stringMethod()
			.hasName("getCourseTypeString");

	static final Methods setCourseCode = classCourse
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("setCourseCode")
			.hasParameter(int.class)
			.hasParameter(List.of(Character.class, char.class));

	static final StringMethods getCourseCode = classCourse
			.stringMethod()
			.hasName("getCourseCode");

	static final StringMethods getCourseName = classCourse
			.stringMethod()
			.hasName("getName");
	static final IntMethods getPeriod = classCourse
			.intMethod()
			.hasName("getPeriod");

	static final Methods setCourseCredits = classCourse
			.hasMethod()
			.isPrivate()
			.hasReturnType(void.class)
			.hasName("setCredits")
			.hasParameter(double.class);

	static final DoubleMethods getCourseCredits = classCourse
			.doubleMethod()
			.hasName("getCredits");

	static final StringMethods courseToString = classCourse
			.stringMethod()
			.hasName("toString");
	static final BooleanMethods isNumericGrade = classCourse
			.booleanMethod()
			.hasName("isNumericGrade");

	// StudentCourse methods
	static final Methods setGrade = classStudentCourse
			.hasMethod()
			.isProtected()
			.hasName("setGrade")
			.hasParameter(int.class);

	static final BooleanMethods checkGradeValidity = classStudentCourse
			.booleanMethod()
			.isPrivate()
			.hasName("checkGradeValidity")
			.hasParameter(int.class);

	static final BooleanMethods isPassed = classStudentCourse
			.booleanMethod()
			.hasName("isPassed");

	static final IntMethods getYear = classStudentCourse
			.intMethod()
			.hasName("getYear");

	static final Methods setYear = classStudentCourse
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("setYear")
			.hasParameter(int.class);

	static final IntMethods getCourseType = classCourse
			.intMethod()
			.hasName("getCourseType");

	static final Methods getCourse = classStudentCourse
			.hasMethod()
			.hasName("getCourse")
			.hasReturnType(classCourse);

	static final StringMethods studentCourseToString = classStudentCourse
			.stringMethod()
			.hasName("toString");

	// Degree methods
	static final Methods getCourses = classDegree
			.hasMethod()
			.hasReturnType(Types.parameterized(List.class, classStudentCourse))
			.hasName("getCourses");

	static final Methods addStudentCourses = classDegree
			.hasMethod()
			.hasName("addStudentCourses")
			.hasParameter(Types.parameterized(List.class, classStudentCourse));

	static final BooleanMethods addStudentCourse = classDegree
			.booleanMethod()
			.hasName("addStudentCourse")
			.hasParameter(classStudentCourse);

	static final DoubleMethods getCreditsByBase = classDegree
			.doubleMethod()
			.hasName("getCreditsByBase")
			.hasParameter(List.of(Character.class, char.class));

	static final DoubleMethods getCreditsByType = classDegree
			.doubleMethod()
			.hasName("getCreditsByType")
			.hasParameter(int.class);

	static final DoubleMethods getCredits = classDegree
			.doubleMethod()
			.hasName("getCredits");

	static final BooleanMethods isCourseCompleted = classDegree
			.booleanMethod()
			.isPrivate()
			.hasName("isCourseCompleted")
			.hasParameter(classStudentCourse);

	static final Methods printCourses = classDegree
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("printCourses");

	static final Methods getGPA = classDegree
			.hasMethod()
			.hasReturnType(Types.parameterized(List.class, Double.class))
			.hasParameter(int.class)
			.hasName("getGPA");

	static final StringMethods degreeToString = classDegree
			.stringMethod()
			.hasName("toString");

	// Person Methods
	static final IntMethods getRandomId = classPerson
			.intMethod()
			.isProtected()
			.hasName("getRandomId")
			.hasParameter(int.class)
			.hasParameter(int.class);

	static final StringMethods getIdString = classPerson
			.stringMethod()
			.isAbstract()
			.hasName("getIdString");

	static final Methods setFirstName = classPerson
			.hasMethod()
			.hasName("setFirstName")
			.hasReturnType(String.class)
			.hasParameter(String.class);

	static final StringMethods getFirstName = classPerson
			.stringMethod()
			.hasName("getFirstName");

	static final Methods setLastName = classPerson
			.hasMethod()
			.hasName("setLastName")
			.hasReturnType(String.class)
			.hasParameter(String.class);

	static final StringMethods getLastName = classPerson
			.stringMethod()
			.hasName("getLastName");

	static final StringMethods setBirthDate = classPerson
			.stringMethod()
			.hasName("setBirthDate")
			.hasParameter(String.class);

	static final StringMethods getBirthDate = classPerson
			.stringMethod()
			.hasName("getBirthDate");

	// Student methods
	static final IntMethods getId = classStudent
			.intMethod()
			.hasName("getId");

	static final Methods setId = classStudent
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("setId")
			.hasParameter(int.class);

	// Non-default setters/getters
	static final IntMethods getStartYear = classStudent
			.intMethod()
			.hasName("getStartYear", "get[Ss]tartYear");

	static final Methods setStartYear = classStudent
			.hasMethod()
			.hasName("setStartYear", "set[Ss]tartYear")
			.hasParameters(int.class);

	static final IntMethods getGraduationYear = classStudent
			.intMethod()
			.hasName("getGraduationYear");

	static final StringMethods setGraduationYear = classStudent
			.stringMethod()
			.hasName("setGraduationYear")
			.hasParameters(int.class);

	static final Methods setDegreeTitle = classStudent.hasMethod()
			.hasReturnType(void.class)
			.hasName("setDegreeTitle")
			.hasParameter(int.class)
			.hasParameter(String.class);

	static final BooleanMethods addCourse = classStudent
			.booleanMethod()
			.hasName("addCourse")
			.hasParameter(int.class)
			.hasParameter(classStudentCourse);

	static final IntMethods addCourses = classStudent
			.intMethod()
			.hasName("addCourses")
			.hasParameter(int.class)
			.hasParameter(Types.parameterized(List.class, classStudentCourse));

	static final Methods studentPrintCourses = classStudent
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("printCourses");

	static final Methods printDegrees = classStudent
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("printDegrees");

	static final Methods setTitleOfThesis = classStudent
			.hasMethod()
			.hasReturnType(void.class)
			.hasName("setTitleOfThesis")
			.hasParameter(int.class)
			.hasParameter(String.class);

	static final BooleanMethods hasGraduated = classStudent
			.booleanMethod()
			.hasName("hasGraduated");

	static final BooleanMethods canGraduate = classStudent
			.booleanMethod()
			.isPrivate()
			.hasName("canGraduate");

	static final IntMethods getStudyYears = classStudent
			.intMethod()
			.hasName("getStudyYears");

	static final StringMethods studentToString = classStudent
			.stringMethod()
			.hasName("toString");

	// Employee methods
	static final IntMethods getStartYearEmployee = classEmployee
			.intMethod()
			.hasName("getStartYear", "get[Ss]tartYear");

	static final Methods setStartYearEmployee = classEmployee
			.hasMethod()
			.hasName("setStartYear", "set[Ss]tartYear")
			.hasParameters(int.class);

	// V1
	static final DoubleMethods getSalary = classEmployee
			.doubleMethod()
			.hasName("getSalary");
	// V1
	static final Methods setSalary = classEmployee
			.hasMethod()
			.hasName("setSalary")
			.hasParameter(double.class);

	static final StringMethods getEmployeeIdString = classEmployee
			.stringMethod()
			.hasName("getEmployeeIdString");

	static final StringMethods employeeToString = classEmployee
			.stringMethod()
			.hasName("toString");
	// V2
	static final Methods setPayment = classEmployee
			.hasMethod()
			.hasName("setPayment")
			.hasParameter(interfacePayment);

	// V2
	static final Methods getPayment = classEmployee
			.hasMethod()
			.hasName("getPayment")
			.hasReturnType(interfacePayment);

	// Payment methods
	// TODO: back to DoubleMethods when JReq updated
	static final Methods calculatePayment = interfacePayment
			.hasMethod()
			.hasName("calculatePayment")
			.hasReturnType(double.class);

	// V2
	// MonthlyPayment Methods
	static final DoubleMethods getSalaryMonthlyPayment = classMonthlyPayment
			.doubleMethod()
			.hasName("getSalary");

	// V2
	static final Methods setSalaryMonthlyPayment = classMonthlyPayment
			.hasMethod()
			.hasName("setSalary")
			.hasParameter(double.class);

	// HourBasedPayment Methods
	// V2
	static final DoubleMethods getEurosPerHour = classHourBasedPayment
			.doubleMethod()
			.hasName("getEurosPerHour");

	// V2
	static final Methods setEurosPerHour = classHourBasedPayment
			.hasMethod()
			.hasName("setEurosPerHour")
			.hasParameter(double.class);

	// V2
	static final DoubleMethods getHours = classHourBasedPayment
			.doubleMethod()
			.hasName("getHours");

	// V2
	static final Methods setHours = classHourBasedPayment
			.hasMethod()
			.hasName("setHours")
			.hasParameter(double.class);

	// V2
	static final DoubleMethods calculatePaymentHourBasedPayment = classHourBasedPayment
			.doubleMethod()
			.hasName("calculatePayment");

	// V2
	// Teacher Methods
	// TODO: back to StringMethods when JReq updated
	static final Methods getCoursesTeacher = interfaceTeacher
			.hasMethod()
			.hasName("getCourses")
			.hasReturnType(String.class);

	// V2
	// DesignatedCourse methods
	static final Methods getCourseDesignatedCourse = classDesignatedCourse
			.hasMethod()
			.hasName("getCourse")
			.hasReturnType(classCourse);

	static final Methods setCourseDesignatedCourse = classDesignatedCourse
			.hasMethod()
			.hasName("setCourse")
			.hasParameter(classCourse);

	static final BooleanMethods isResponsible = classDesignatedCourse
			.booleanMethod()
			.hasName("isResponsible");

	static final Methods setResponsible = classDesignatedCourse
			.hasMethod()
			.hasName("setResponsible")
			.hasParameter(boolean.class);

	static final BooleanMethods getResponsible = classDesignatedCourse
			.booleanMethod()
			.hasName("getResponsible");

	static final IntMethods getYearDesignatedCourse = classDesignatedCourse
			.intMethod()
			.hasName("getYear");

	static final Methods setYearDesignatedCourse = classDesignatedCourse
			.hasMethod()
			.hasName("setYear")
			.hasParameters(int.class);

	static final StringMethods toStringDesignatedCourse = classDesignatedCourse
			.stringMethod()
			.hasName("toString");

	// V2
	// AssistantTeacher methods
	static final StringMethods getCoursesAssistantT = classAssistantTeacher
			.stringMethod()
			.hasName("getCourses");

	static final Methods setCoursesAssistantT = classAssistantTeacher
			.hasMethod()
			.hasName("setCourses")
			.hasParameter(Types.parameterized(List.class, classDesignatedCourse));

	// V2
	// ResponsibleTeacher methods
	static final StringMethods getCoursesResponsibleT = classResponsibleTeacher
			.stringMethod()
			.hasName("getCourses");

	static final Methods setCoursesResponsibleT = classResponsibleTeacher
			.hasMethod()
			.hasName("setCourses")
			.hasParameter(Types.parameterized(List.class, classDesignatedCourse));

	static final Pattern NO_NAME_PATTERN = Pattern.compile("\"?No\\s+name\"?",
			Pattern.CASE_INSENSITIVE);
	static final Pattern NO_TITLE_PATTERN = Pattern.compile("\"?No\\s+title\"?",
			Pattern.CASE_INSENSITIVE);
	static final Pattern NOT_AVAILABLE_PATTERN = Pattern.compile(
			"\"?Not\\s+available[.!\"]*", Pattern.CASE_INSENSITIVE);
	static Pattern NO_CHANGE_PATTERN = Pattern.compile("\"?No\\s+change[.!\"]*",
			Pattern.CASE_INSENSITIVE);
	static final Pattern INVALID_BIRTHDAY_PATTERN = Pattern.compile(
			"\"?Invalid\\s+birthday[.!\"]*", Pattern.CASE_INSENSITIVE);
	static final Pattern INCORRECT_CHECK_MARK_PATTERN = Pattern.compile(
			"\"?Incorrect\\s+check\\s*mark[.!\"]*", Pattern.CASE_INSENSITIVE);
	static Pattern CHECK_REQUIRED_CREDITS_PATTERN = Pattern.compile(
			"\"?Check\\s+(the\\s+)?amount\\s+of\\s+required\\s+credits[.!\"]*",
			Pattern.CASE_INSENSITIVE);
	static final Pattern CHECK_GRADUATION_YEAR_PATTERN = Pattern.compile(
			"\"?Check\\s+(the\\s+)?graduation\\s+year[.!\"]*",
			Pattern.CASE_INSENSITIVE);
	static final Pattern OK_PATTERN = Pattern.compile("\"?Ok[.!\"]*",
			Pattern.CASE_INSENSITIVE);

	// Constant Values given in the task description
	static final int MIN_STUDENT_ID = 1;
	static final int MAX_STUDENT_ID = 100;
	static final int MIN_EMP_ID = 2001;
	static final int MAX_EMP_ID = 3000;

	static final double MIN_CREDITS = 0.0;
	static final double MAX_CREDITS = 300.0;
	static final double BACHELOR_CREDITS = 180.0;
	static final double MASTER_CREDITS = 120.0;
	static final double BACHELOR_OPTIONAL = 30.0;
	static final double MASTER_OPTIONAL = 70.0;
	static final double BACHELOR_MANDATORY = 150.0;
	static final double MASTER_MANDATORY = 50.0;

	// static final double MIN_COURSE_CREDIT = 0.0;
	static final double MAX_COURSE_CREDITS = 55.0;
	static final int MIN_PERIOD = 1;
	static final int MAX_PERIOD = 5;
	static final int BACHELOR_TYPE = 0;
	static final int MASTER_TYPE = 1;
	static final int OPTIONAL = 0;
	static final int MANDATORY = 1;
	static final int ALL = 2;

	static final int MIN_GRADE = 0;
	static final int MAX_GRADE = 5;
	static final char GRADE_FAILED = 'F';
	static final char GRADE_ACCEPTED = 'A';
	static final String VALID_BASES = "APS";

	// TODO: Add the regex expressions for degree strings as the above examples
	static final String BACHELOR_STR_REGEX = "Bachelor Degree";
	static final String MASTER_STR_REGEX = "Masters Degree";
	static final String DOCTORAL_STR_REGEX = "Doctoral Degree";

	static final String seeConsoleOutput = "(<== see Console output for context)";

	static final int minYear = 2001;
	static final int minDesignatedCourseYear = 2000;
	static final int yearNow = LocalDate.now().getYear();

	static final Random rand = new Random();

	// Used in CourseTests
	static Object randomCourse(char base, int type, double credits,
			boolean numericGrading, StringBuilder expectedString) {
		String randName = randomString(10);
		int randCode = rand.nextInt(1, 1000000);
		int randPeriod = rand.nextInt(1, 6);

		if (expectedString != null) {
			// Example:
			// [811104P ( 5.00 cr), "Programming 1". Mandatory, period: 1.]
			String regex = String.format(Locale.ROOT, "\\[%s \\(%.2f cr\\), \"%s\"\\.? %s, period: %d\\.?]",
					"" + randCode + base,
					credits,
					randName,
					type == 0 ? "Optional" : "Mandatory",
					randPeriod);
			regex = regex.replaceFirst("\\.", "[.,]");
			expectedString.append(regex);
		}

		return sevenParamsCourse.instantiate(
				randName,
				randCode,
				base,
				type,
				randPeriod,
				credits,
				numericGrading);
	}

	static Object randomCourse(boolean numericGrading, StringBuilder expectedString) {
		Character randBase = "APS".charAt(rand.nextInt(3));
		int randType = rand.nextInt(2);
		double randCredits = rand.nextInt(56);
		return randomCourse(randBase, randType, randCredits, numericGrading, expectedString);
	}

	static Object randomCourse(double credits) {
		Character randBase = "APS".charAt(rand.nextInt(3));
		int randType = rand.nextInt(2);
		return randomCourse(randBase, randType, credits, true, null);
	}

	static Object randomCourse(double credits, int type) {
		Character randBase = "APS".charAt(rand.nextInt(3));
		return randomCourse(randBase, type, credits, true, null);
	}

	static Object randomCourse() {
		return randomCourse(rand.nextBoolean(), null);
	}

	static Object randomStudentCourse(int grade, Object course) {
		return randomStudentCourse(grade, course, null, null);
	}

	static Object randomStudentCourse(int grade, StringBuilder expectedString) {
		boolean numericGrading = (grade >= 0 && grade <= 5) ? true : false;
		StringBuilder expectedCourseString = new StringBuilder();
		Object newCourse = randomCourse(numericGrading, expectedCourseString);
		return randomStudentCourse(grade, newCourse, expectedCourseString, expectedString);
	}

	static Object randomStudentCourse(boolean numericGrading, StringBuilder expectedString) {
		char randGrade = numericGrading
				? (char) rand.nextInt(6)
				: rand.nextBoolean()
						? 'A'
						: 'F';
		return randomStudentCourse(randGrade, expectedString);
	}

	static Object randomStudentCourse() {
		return randomStudentCourse(rand.nextBoolean(), null);
	}

	static Object randomDesignatedCourse() {
		Object course = randomCourse();
		return allParamDesignatedCourse.instantiate(course, rand.nextBoolean(),
				rand.nextInt(minDesignatedCourseYear, yearNow + 1));
	}

	private static Object randomStudentCourse(int grade, Object course,
			StringBuilder expectedCourseString, StringBuilder expectedString) {
		int randYear = rand.nextInt(minYear, yearNow + 1);
		if (expectedString != null && expectedCourseString != null) {
			// Example:
			// {Course.toString()} Year: 2021, Grade: "Not graded".]
			expectedString.append("\\[?");
			expectedString.append(expectedCourseString.toString());
			String regex = String.format(" Year: %d, Grade: %s\\.?\\] ",
					randYear, grade == 0 ? "\"?Not graded\"?"
							: (char) (grade < 6
									? '0' + grade
									: grade));
			expectedString.append(regex);
		}
		return threeParamsStudentCourse.instantiate(
				course,
				grade,
				randYear);
	}

	static String randomString(int length) {
		return rand.ints(length, 'a', 'a' + 26)
				.collect(StringBuilder::new,
						StringBuilder::appendCodePoint,
						StringBuilder::append)
				.toString();
	}

	static void assertCoursesEqual(Object course1, Object course2) {
		System.out.println("Checking whether the two course objects are equal:");

		System.out.print(" => Course 2: ");
		String name2 = name.read(course2);
		System.out.print(" => Course 1: ");
		String name1 = name.read(course1);
		assertTrue(name.matches(course1, name2), explain("Course names \"%s\" and \"%s\" should match", name1, name2));

		System.out.print(" => Course 2: ");
		String code2 = courseCode.read(course2);
		System.out.print(" => Course 1: ");
		String code1 = courseCode.read(course1);
		assertTrue(courseCode.matches(course1, code2), explain("CourseCodes \"%s\" and \"%s\" should match", code1, code2));

		System.out.print(" => Course 2: ");
		Character base2 = (Character) courseBase.read(course2);
		System.out.print(" => Course 1: ");
		Character base1 = (Character) courseBase.read(course1);
		assertTrue(courseBase.matches(course1, base2), explain("CourseBases \'%c\' and \'%c\' should match", base1, base2));

		System.out.print(" => Course 2: ");
		int period2 = period.read(course2);
		System.out.print(" => Course 1: ");
		int period1 = period.read(course1);
		assertTrue(period.matches(course1, period2), explain("Course periods %d and %d should match", period1, period2));

		System.out.print(" => Course 2: ");
		double credits2 = credits.read(course2);
		System.out.print(" => Course 1: ");
		double credits1 = credits.read(course1);
		assertTrue(credits.matches(course1, credits2), explain("Course periods %.2f and %.2f should match", credits1, credits2));

		System.out.print(" => Course 2: ");
		boolean numericGrade2 = numericGrade.read(course2);
		System.out.print(" => Course 1: ");
		boolean numericGrade1 = numericGrade.read(course1);
		assertTrue(numericGrade.matches(course1, numericGrade2), explain("Course periods %b and %b should match", numericGrade1, numericGrade2));

		System.out.println("=> The course objects are equal.\n");
	}

	static void assertStudentCoursesEqual(Object studentCourse1, Object studentCourse2) {
		System.out.println("Checking whether the two student course objects are equal:");

		System.out.print("=> StudentCourse 2: ");
		Object course2 = course.read(studentCourse2);
		System.out.print("=> StudentCourse 1: ");
		assertCoursesEqual(course.read(studentCourse1), course2);

		System.out.print("=> StudentCourse 2: ");
		int gradeNum2 = gradeNum.read(studentCourse2);
		System.out.print("=> StudentCourse 1: ");
		int gradeNum1 = gradeNum.read(studentCourse1);
		assertTrue(gradeNum.matches(studentCourse1, gradeNum2), explain("StudentCourse gradeNums %d and %d should match", gradeNum1, gradeNum2));

		System.out.print("=> StudentCourse 2: ");
		int year2 = yearCompleted.read(studentCourse2);
		System.out.print("=> StudentCourse 1: ");
		int year1 = yearCompleted.read(studentCourse1);
		assertTrue(yearCompleted.matches(studentCourse1, year2), explain("StudentCourse yearCompleted values %d and %d should match", year1, year2));

		System.out.println("=> The student course objects are equal.\n");
	}

	static void assertDesignatedCoursesEqual(Object desCourse1, Object desCourse2) {
		System.out.println("Checking whether the two designated course objects are equal:");
		assertCoursesEqual(courseDesignatedCourse.read(desCourse1), courseDesignatedCourse.read(desCourse2));
		boolean responsible1 = responsible.read(desCourse1);
		boolean responsible2 =  responsible.read(desCourse2);
		assertEquals(responsible1, responsible2, explain("Designated course responsible values %b and %b should match", responsible1, responsible2));
		int year1 = year.read(desCourse1);
		int year2 = year.read(desCourse2);
		assertEquals(year1, year2, explain("Designated course year values %d and %d should match", year1, year2));

		System.out.println("=> The designated course objects are equal.\n");
	}

	static void setCredits(Object student, double targetCredits) {
		int target = (int) targetCredits;
		int accumulatedCredits = 0;
		while (accumulatedCredits < target) {
			int creditsNow = Math.min(target - accumulatedCredits, rand.nextInt(35, 56)); // ? Where do these values come
																																										// from?
			addCourse.call(student, randomStudentCourse(rand.nextInt(1, 6),
					randomCourse(creditsNow)));
			accumulatedCredits += creditsNow;
		}
	}

	static List<Object> setCredits(Object student, int degreeNo, double targetCredits) {
		int target = (int) targetCredits;
		int accumulatedCredits = 0;
		List<Object> studentCourses = new ArrayList<>();
		while (accumulatedCredits < target) {
			int creditsNow = Math.min(target - accumulatedCredits, rand.nextInt(35, 56));
			Object sc = randomStudentCourse(rand.nextInt(1, 6), randomCourse(creditsNow));
			studentCourses.add(sc);
			addCourse.call(student, degreeNo, sc);
			accumulatedCredits += creditsNow;
		}
		return studentCourses;
	}

	static List<Object> setCreditsByType(Object student, int degreeNo, double targetCredits, int type) {
		int target = (int) targetCredits;
		int accumulatedCredits = 0;
		List<Object> studentCourses = new ArrayList<>();
		while (accumulatedCredits < target) {
			int creditsNow = Math.min(target - accumulatedCredits, rand.nextInt(35, 56));
			Object sc = randomStudentCourse(rand.nextInt(1, 6), randomCourse(creditsNow, type));

			studentCourses.add(sc);
			addCourse.call(student, degreeNo, sc);
			accumulatedCredits += creditsNow;
		}
		return studentCourses;
	}

	static void setBachelorsThesis(Object student) {
		setTitleOfThesis.call(student, greatTitle());
	}

	static void setThesis(Object student, int degreeNo) {
		setTitleOfThesis.call(student, degreeNo, greatTitle());
	}

	static void graduate(Object student, int sYear, int gYear) {
		setStartYear.call(student, sYear);
		setCredits(student, 180);
		setBachelorsThesis(student);
		setGraduationYear.call(student, gYear);
	}

	static void graduateV2(Object student, int sYear, int gYear) {
		setStartYear.call(student, sYear);
		setCredits(student, 0, 180);
		setCredits(student, 1, 120);
		setThesis(student, 0);
		setThesis(student, 1);
		setGraduationYear.call(student, gYear);
	}

	static void graduate_H3(Object student, int sYear, int gYear) {
		setStartYear.call(student, sYear);
		setCreditsByType(student, 0, BACHELOR_MANDATORY, MANDATORY);
		setCreditsByType(student, 0, BACHELOR_OPTIONAL, OPTIONAL);

		setCreditsByType(student, 1, MASTER_MANDATORY, MANDATORY);
		setCreditsByType(student, 1, MASTER_OPTIONAL, OPTIONAL);
		setThesis(student, 0);
		setThesis(student, 1);
		setGraduationYear.call(student, gYear);
	}

	// Used in PersonIDTests
	static String makePersonIDWithIncorrectChecksum() {
		String personId = makePersonID();
		char correctCC = personId.charAt(10);
		String validCCs = "0123456789ABCDEFHJKLMNPRSTUVWXY";
		char incorrectCC = validCCs.charAt(
				(validCCs.indexOf(correctCC) + rand.nextInt(1, 31))
						% 31);
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
			int yyNow = (LocalDate.now().getYear() % 1000 % 100);
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
		String padded = Integer.toString(paddee);
		for (int i = padded.length(); i < amount; i++) {
			padded = '0' + padded;
		}
		return padded;
	}

	static String personIdToBirthDate(String id) {
		String yy;
		switch (id.charAt(6)) {
			case '+':
				yy = "18";
				break;
			case '-':
				yy = "19";
				break;
			default:
				yy = "20";
				break;
		}
		return id.substring(0, 2)
				+ '.' + id.substring(2, 4)
				+ '.' + yy + id.substring(4, 6);
	}

	static String[] thesisTitleWords = { "The", "Bachelor", "Masters", "Thesis", "Title",
			"How", "To", "Survive", "Happy", "Ending", "New", "Exciting", "Purpose", "Of",
			"Life", "Christmas", "Most", "Wonderful", "Time", "Year", "Dreaming", /* "White" */ };

	static String greatTitle() {
		List<String> words = new ArrayList<>(List.of(thesisTitleWords));
		Collections.shuffle(words);
		return words.stream().limit(rand.nextInt(3) + 3)
				.reduce("", (a, b) -> a + ' ' + b).stripLeading();
	}

	// Makes the message blocks stand out
	static String addBorders(String message) {
		return "-----------------------------------------------------\n"
				+ message + "\n"
				+ "-----------------------------------------------------";
	}

	static String captureOutput(Callable<?> method, Object instance) {
		// start capture
		PrintStream stdOut = System.out;
		ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(newOut));
		method.invoke(instance);
		// end capture
		System.setOut(stdOut);
		return newOut.toString();
	}


	static <T> void assertGetterReturnsCurrentValue(Object receiver,
			Property<T> attribute,
			Callable<T> getter,
			String variableName) {
		System.out.println(addBorders(
				"Comparing the return value of the getter to the "
						+ "value\nof the attribute (ignoring the visibility modifier):"));
		// Read the value reflectively, bypassing visibility,
		// and compare it to the getter's return value
		T expected = attribute.read(receiver);
		assertTrue(getter.returns(receiver, expected), 
				explain("Expected the return value of getter method of %s to match value of the attribute", variableName)
					.expected(expected.toString()).but(getter.invoke(receiver).toString()));
	}

	static <T> FailureMessage expectedUnchanged(String name, T expected, String after) {
	// If T is Double, format the numbers to 1 decimal place
	if (expected instanceof Double) {
		return explain(String.format("Expected %s to stay ⟨%.1f⟩ %s", name, expected, after));
	}
		return explain(String.format("Expected %s to stay ⟨%s⟩ %s", name, expected, after));
	}

	static <T> void assertGetterReturnsNewValue(Object receiver,
		Property<T> attribute,
		Callable<T> getter,
		T newValue,
		String variableName) {
		System.out.println(addBorders("""
				Comparing the return value of the getter to the value
				of the attribute after setting a new value directly
				(ignoring the visibility modifier):"""));
		// Set the value reflectively,
		// bypassing setter and visibility,
		// and compare it to the getter's return value
		String expected = getter.invoke(receiver).toString();
		attribute.write(receiver, newValue);
		if (newValue instanceof String) {
				assertTrue(getter.returns(receiver, newValue),
			explain("Expected the return value of getter method of %s to match value of the attribute", variableName)
					.expected("\"" + newValue.toString() + "\"").but("was: \"" + expected + "\""));
		}else{
			assertTrue(getter.returns(receiver, newValue),
			explain("Expected the return value of getter method of %s to match value of the attribute", variableName)
					.expected(newValue.toString()).but("was: " + expected));
		}
			
	}

	static <T> void assertSetterUpdatesCurrentValue(Object receiver,
			Property<T> attribute,
			Callable<?> setter,
			Callable<?> getter,
			T newValue,
			String variableName) {
		System.out.println(addBorders("""
				Comparing the value of the attribute before and after
				setting a new value using the setter (ignoring the
				attribute's visibility modifier)"""));
		T currentValue = attribute.read(receiver);
		if (currentValue.equals(newValue)) {
			System.out.println("""
					The current value and the new value are the same.
					The setter should update the current value.
					~> The test will be inconclusive...""");
		}
		// call setter and read the value reflectively
		String getterValue = getter.invoke(receiver).toString();
		setter.call(receiver, newValue);

		// different error feedback based on variable datatype
		if (newValue instanceof String) {
			assertTrue(attribute.matches(receiver, newValue),
					explain("Expected the setter method of %s to set the value of the attribute.", variableName)
						.expected("\"" + newValue.toString() + "\"").but("was: \"" + getterValue + "\""));
		}
		else{
			assertTrue(attribute.matches(receiver, newValue),
					explain("Expected the setter method of %s to set the value of the attribute.", variableName)
						.expected(newValue.toString()).but("was: " + getterValue));
		}
	}

	static <T> void assertGetterReturnsSetValue(Object receiver,
			Callable<?> setter,
			Callable<T> getter,
			T newValue,
			String variableName) {
		System.out.println(addBorders(
				"Comparing the return value of the getter to the "
						+ "value\nset by the setter:"));
		T currentValue = getter.call(receiver);
		if (currentValue.equals(newValue)) {
			System.out.println("""
					The current value and the new value are the same.
					The setter should update the current value.
					~> The test will be inconclusive...""");
		}
		// set the value via setter and compare it to the getter's return value
		setter.call(receiver, newValue);
		String getterValue = getter.invoke(receiver).toString();
		
		if (newValue instanceof String) {
			assertTrue(getter.returns(receiver, newValue),
				explain("Expected the getter method of %s to return the value set by the setter.", variableName)
					.expected("\"" + newValue.toString() + "\"").but("was: \"" + getterValue + "\""));
		}else{
			assertTrue(getter.returns(receiver, newValue),
					explain("Expected the getter method of %s to return the value set by the setter.", variableName)
						.expected(newValue.toString()).but("was: " + getterValue));
		}
	}

	static void findIgnoringWhitespace(String expectedPattern, String actual,
			String location, String identifyPattern) {
		String shownPattern = expectedPattern.replaceAll(Pattern.quote("\\n"),
				"\n")
				.replaceAll(Pattern.quote(
						"\\p{Space}*"), "")
				.replaceAll(Pattern.quote(
						"\\p{Space}+"), " ")
				.replaceAll(Pattern.quote(
						"\\p{Space}"), " ");
		
		String actualPattern = shownPattern.replaceAll("\\s", "");
		Pattern compiled = Pattern.compile(actualPattern,
				Pattern.CASE_INSENSITIVE);
		String actualActual = actual.replaceAll("\\s", "");
		String regexExplanation = RegexMatcher.regexExplanation(compiled);
		
		identifyPattern = identifyPattern.replaceAll("\\s", "");
		Pattern identifyCompiled = Pattern.compile(identifyPattern, Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = identifyCompiled.matcher(actualActual);

		// identify the part of the string that is being matced to the pattern
		// this is required for the findErrorIndex() method to work
		assertTrue(matcher.find(), explain(String.format("Invalid output string. Expected a string matching the pattern %s to be found in the output\n"
									+ "( * * * * * spaces, tabs, newlines and case are ignored * * * * * )"
								+ "%n⮱%n\"%s\"%n⮳%n%s:%n*%n* * * * * *%n\"%s\"%n"
								+ "* * * * * *%n*" + (regexExplanation.isBlank()
										? ""
										: ("%nRegular expression explanation:%n"
												+ regexExplanation)),
						identifyPattern, identifyPattern, location, actual)).asIs());
		
		actualActual = actualActual.substring(matcher.start());
		matcher = compiled.matcher(actualActual);
		boolean matches = matcher.find();
		String errorString = "";

		if (!matches){ // if not matching, iterate the output string one char at time in order to find out the error point
			int errorIndex = findErrorIndex(compiled, actualActual);

			// move the index by the amount of removed whitespace before the error index
			// for getting the error location in the original string with whitespaces
			int startIndex = 0;
			int index;
			while (startIndex < actual.length()) {
				index = actual.indexOf(" ", startIndex);
				int newIndex = actual.indexOf("\n", startIndex);
				if (newIndex != -1 && (index == -1 || newIndex < index)) {
					index = newIndex;
				}
				if (index != -1) {
					startIndex = index + 1;
					if (index < errorIndex) {
						errorIndex++;
					}
				} else {
					break;
				}
			}

			errorString = actual.substring(0, errorIndex);
			System.out.println("Error is caused by the next character after: " + "\"" + errorString + "\"\n");
		}

		assertTrue(matches,
				explain(String.format(
				"Invalid output string. The string was correct until the character after: \"" + errorString + "\"\n" +
						"%nExpected to find a string matching the following regular expression%n"
								+ "( * * * * * spaces, tabs, newlines and case are ignored * * * * * )"
								+ "%n⮱%n\"%s\"%n⮳%n%s:%n*%n* * * * * *%n\"%s\"%n"
								+ "* * * * * *%n*" + (regexExplanation.isBlank()
										? ""
										: ("%nRegular expression explanation:%n"
												+ regexExplanation)),
						shownPattern, location, actual)).asIs());
	}

	private static int findErrorIndex(Pattern pattern, String input) {
		System.out.println("Error found in the printed output string. Locating the error by reading the output string one character at a time");
        Matcher matcher = pattern.matcher(input);
		// iterate through the input one character at a time to find the point where string no longer matching pattern
        for (int i = 0; i < input.length(); i++) {
            matcher = matcher.region(0, i);
            if (matcher.matches() || !matcher.hitEnd()) {
				return i-1;
            }
        }

        return 0;
    }

		// Run singular checkstyle check
	static Object[] runCheck(String checkName, List<File> files) throws Exception {

        for (File currentFile : files) {
            if (!currentFile.exists()) {
                fail(String.format("File %s not found", currentFile.getPath()));
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
