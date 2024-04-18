package dev.m3s.programming2.homework3;

import java.util.ArrayList;
import java.util.List;

public class AssistantTeacher extends Employee implements Teacher {

    // ATTRIBUTES

    private List<DesignatedCourse> courses = new ArrayList<>();

    // CONSTRUCTORS

    public AssistantTeacher(String lname, String fname) {
        super(lname, fname);
    }

    // METHODS

    @Override
    protected String getEmployeeIdString() {
        return "OY_ASSISTANT_";
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
        str.append("Teacher id: " + getEmployeeIdString() + getIdString())
        .append("\n\t")
        .append("First name: " + getFirstName() + ", Last name: " + getLastName())
        .append("\n\t")
        .append("Birthdate: " + getBirthDate())
        .append("\n\t")
        .append("Salary: " + getPayment())
        .append("\n\t")
        .append("Assistant for courses:")
        .append("\n\t")
        .append(courses);

        return str.toString();
    }

}
