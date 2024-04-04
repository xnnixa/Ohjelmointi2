package dev.m3s.programming2.homework3;

import java.util.ArrayList;
import java.util.List;

public class Degree {

    //ATTRIBUTES
    
    private static final int MAX_COURSES = 50;
    private String degreeTitle = ConstantValues.NO_TITLE;
    private String titleOfThesis = ConstantValues.NO_TITLE;
    private List<StudentCourse> myCourses = new ArrayList<>();

    //METHODS

    public List<StudentCourse> getCourses() {
        return new ArrayList<>(myCourses);       //returns a new list to avoid direct access to the original list
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
        if (degreeTitle != null) {
            this.degreeTitle = degreeTitle;
        }
    }



}
