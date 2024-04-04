package dev.m3s.programming2.homework3;

import java.util.ArrayList;
import java.util.List;

public class Degree {

    // ATTRIBUTES

    private static final int MAX_COURSES = 50;
    private String degreeTitle = ConstantValues.NO_TITLE;
    private String titleOfThesis = ConstantValues.NO_TITLE;
    private List<StudentCourse> myCourses = new ArrayList<>();

    // METHODS

    public List<StudentCourse> getCourses() {
        return new ArrayList<>(myCourses); // returns a new list to avoid direct access to the original list
    }

    public void addStudentCourses(List<StudentCourse> courses) {
        if (courses != null) {
            for (StudentCourse course : courses) {
                if (myCourses.size() < MAX_COURSES)
                    addStudentCourse(course);
            }
        }
    }

    public boolean addStudentCourse(StudentCourse course) {
        if (course != null) {
            myCourses.add(course);
            return true;
        }
        return false;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public void setDegreeTitle(String degreeTitle) {
        if (degreeTitle != null && !degreeTitle.isEmpty()) {
            this.degreeTitle = degreeTitle;
        }
    }

    public String getTitleOfThesis() {
        return titleOfThesis;
    }

    public void setTitleOfThesis(String titleOfThesis) {
        if (titleOfThesis != null && !titleOfThesis.isEmpty()) {
            this.titleOfThesis = titleOfThesis;
        }
    }

    public double getCreditsByBase(Character base) {
        double baseCredits = 0.00;
        for (StudentCourse course : myCourses) {
            if (course != null && course.getCourse().getCourseBase().equals(base) && course.isPassed()) {
                baseCredits += course.getCourse().getCredits();
            }
        }
        return baseCredits;
    }

    public double getCreditsByType(final int courseType) {
        double typeCredits = 0.00;
        for (StudentCourse course : myCourses) {
            if (course != null && course.getCourse().getCourseType() == courseType && course.isPassed()) {
                typeCredits += course.getCourse().getCredits();
            }
        }
        return typeCredits;
    }

    public double getCredits() {
        double totalCredits = 0.00;
        for (StudentCourse course : myCourses) {
            if (course != null && course.isPassed()) {
                totalCredits += course.getCourse().getCredits();
            }
        }
        return totalCredits;
    }

    private boolean isCourseCompleted(StudentCourse c) {
        return c != null && c.isPassed();
    }

    public void printCourses() {
        for (StudentCourse course : myCourses) {
            if (course != null) {
                System.out.println(course);
            }
        }
    }

    public List<Double> getGPA(int type) {
        List<Double> gpa = new ArrayList<>();
        double sum = 0.0;
        double count = 0.0;
        double average = 0.0;

        for (StudentCourse studentCourse : myCourses) {
            Course course = studentCourse.getCourse();
            if (course.getCourseType() == type || type == ConstantValues.ALL) {
                if (course.isNumericGrade()) {
                    Double grade = (double) studentCourse.getGradeNum();
                    if (grade != null) {
                        sum += grade;
                        count++;
                    }
                }
            }
        }

        average = count > 0 ? Math.round((sum / count) * 100.0) / 100.0 : 0.0; // no division by zero can happen +
                                                                               // rounding to two decimals

        gpa.add(sum); // add items to arraylist
        gpa.add(count);
        gpa.add(average);

        return gpa;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
            str.append('i'); // call student.printDegrees and toString

        return str.toString();
    }

}
