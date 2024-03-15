package dev.m3s.programming2.homework2;

import org.checkerframework.checker.units.qual.C;

public class Course {

    // ATTRIBUTES

    private String name;
    private String courseCode;
    private Character courseBase = 'A'; // must be A, P or S
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
        // The constructor must set the attribute values.
    }

    // METHODS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if ((courseCode > 0 && courseCode < 1000000) && (courseBase == 'A' || courseBase == 'P' || courseBase == 'S')) {
            this.courseCode = String.valueOf(courseCode) + courseBase;
        }
    }

    public Character getCourseBase() {
        return courseBase;
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
        str.append("[" + courseCode + " (" + credits + "), " + name + "." + courseType + "," + "period:" + period + "." + "]");
        return null; //The method will output the expected output for an object of type Course. See examples below ***
    }

}
