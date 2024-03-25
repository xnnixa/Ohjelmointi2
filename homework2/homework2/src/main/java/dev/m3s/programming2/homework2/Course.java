package dev.m3s.programming2.homework2;

public class Course {

    // ATTRIBUTES

    public static char[] toString;
	private String name;
    private String courseCode;
    private Character courseBase; // must be A, P or S
    private int courseType; // 0 = optional, 1 = mandatory
    private int period; // 1-5
    private double credits;
    private boolean numericGrade; // 1 = has numeric grading, 0 = doesn't

    // CONSTRUCTORS

    public Course() {
    }

    public Course(String name, final int code, Character courseBase, final int type, final int period,
            final double credits, boolean numericGrade) {
        setName(name);
        setCourseCode(code, courseBase);
        setCourseType(type);
        setPeriod(period);
        setCredits(credits);
        setNumericGrade(numericGrade);
    }

    public Course(Course course) {
        if (course != null) {
            this.name = course.name;
            this.courseCode = course.courseCode;
            this.courseType = course.courseType;
            this.period = course.period;
            this.credits = course.credits;
            this.numericGrade = course.numericGrade;
        }
    }

    // METHODS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && name != "") {
            this.name = name;
        }
    }

    public String getCourseTypeString() {
        return getCourseType() == 1 ? "Mandatory" : "Optional";
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(final int type) {
        if (type == 1 || type == 0) {
            this.courseType = type;
        }
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(final int courseCode, Character courseBase) {
        if ((courseCode > 0 && courseCode < 1000000)
            && (courseBase != null)
            && ((courseBase == 'A' || courseBase == 'P' || courseBase == 'S')
            || (courseBase == 'a' || courseBase == 'p' || courseBase == 's'))) {
            this.courseBase = Character.toUpperCase(courseBase);
            this.courseCode = String.valueOf(courseCode) + this.courseBase;
        }
    }

    public Character getCourseBase() {
        return this.courseBase;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(final int period) {
        if (period >= ConstantValues.MIN_PERIOD && period <= ConstantValues.MAX_PERIOD) {
            this.period = period;
        }
    }

    public double getCredits() {
        return credits;
    }

    private void setCredits(final double credits) {
        if (credits >= ConstantValues.MIN_CREDITS && credits <= ConstantValues.MAX_COURSE_CREDITS)
        this.credits = credits;
    }

    public boolean isNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(boolean numericGrade) {
        this.numericGrade = numericGrade;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[" + courseCode + " (" + String.format("%.2f", credits) + " cr), \"" + name + "\". " + getCourseTypeString() + ", period: " + period + "." + "]");
        return str.toString();
    }

}
