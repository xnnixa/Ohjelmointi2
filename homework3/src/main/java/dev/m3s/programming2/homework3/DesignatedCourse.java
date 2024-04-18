package dev.m3s.programming2.homework3;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Year;

public class DesignatedCourse {

    // ATTRIBUTES

    private Course course = new Course();
    private boolean responsible;
    private int year;

    // CONSTRUCTORS

    DesignatedCourse() { // default constructor

    }

    DesignatedCourse(Course course, boolean resp, int year) {
        this.course = course;
        this.responsible = resp;
        this.year = year;
    }

    // METHODS

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public boolean isResponsible() {
        if (responsible) {
            return true;
        }
        return false;
    }

    public void setResponsible(boolean responsible) {
        this.responsible = responsible;
    }

    public boolean getResponsible() {
        return responsible;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year >= 2000 && year <= (Year.now().getValue() + 1)) {
            this.year = year;
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");

        str.append("[course=[" + course.getCourseCode() + " (" + df.format(course.getCredits()) + " cr), \"")
        .append(course.getName() + "\". " + course.getCourseTypeString() + ", period:" + course.getPeriod() + ".], year=" + getYear() + "]" + "\n");

        return str.toString();
    }

}
