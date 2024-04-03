package dev.m3s.programming2.homework3;

import java.util.ArrayList;
import java.util.List;

public class Degree {

    //ATTRIBUTES
    
    private static final int MAX_COURSES = 50;
    private String degreeTitle = ConstantValues.NO_TITLE;
    private String titleOfThesis = ConstantValues.NO_TITLE;
    List<StudentCourse> myCourses = new ArrayList<>();

    //METHODS

    public List<StudentCourse> getCourses() {
        return myCourses;
    }

    public void addStudentCourses(List<StudentCourse> courses) {
        if (courses != null && courses.size() < MAX_COURSES) {
            addStudentCourse();
            //keep count
        }
    }

    public boolean addStudentCourse(StudentCourse course) {
        if (course != null) {
            //lisää kurssi
            return true;
        }
        return false;
    }

}
