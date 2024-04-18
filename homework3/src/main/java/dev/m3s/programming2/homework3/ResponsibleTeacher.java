package dev.m3s.programming2.homework3;

import java.util.ArrayList;
import java.util.List;

public class ResponsibleTeacher extends Employee implements Teacher {

    //ATTTRIBUTES

    private List<DesignatedCourse> courses = new ArrayList<>();

    //CONSTRUCTOR
        
    public ResponsibleTeacher(String lname, String fname) {
        super(lname, fname);
    }

    //METHODS
    
    @Override
    protected String getEmployeeIdString() {
        return "OY_TEACHER_";
    }
    
    @Override
    public String getCourses() {
        if (courses.isEmpty()) {
            return "";
        }
        return courses.toString();
    }

    public void setCourses(List<DesignatedCourse> courses) {
        if (courses != null) {
            this.courses = courses;
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("");

        return str.toString();
    }


}