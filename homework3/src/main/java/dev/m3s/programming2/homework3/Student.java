package dev.m3s.programming2.homework3;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    // ATTRIBUTES

    private int id;
    private int startYear = Year.now().getValue();
    private int graduationYear;
    private List<Degree> degrees = new ArrayList<>();

    // CONSTRUCTOR

    public Student(String lname, String fname) {
        super(lname, fname);
        this.id = getRandomId(ConstantValues.MIN_STUDENT_ID, ConstantValues.MAX_STUDENT_ID);
    }

    // METHODS

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        if (id <= ConstantValues.MAX_STUDENT_ID && id >= ConstantValues.MIN_STUDENT_ID) {
            this.id = id;
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        if (startYear > 2000 && startYear <= Year.now().getValue()) {
            this.startYear = startYear;
        }
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public String setGraduationYear(final int graduationYear) {

        if (graduationYear < startYear || graduationYear > Year.now().getValue() || graduationYear <= 2000) {
            return "Check graduation year";
        }

        if (canGraduate()) {
            this.graduationYear = graduationYear;
            return "Ok";
        } else {
            return "Check amount of required credits";
        }
    }

    public void setDegreeTitle(final int i, String dName) {
        if ((dName != null && !degrees.isEmpty()) && (i >= 0 && i < degrees.size())) {
            degrees.get(i).setDegreeTitle(dName);
        }
    }

    public boolean addCourse(final int i, StudentCourse course) {
        if (i >= 0 && i < degrees.size() && course != null) {
            return degrees.get(i).addStudentCourse(course);
        }
        return false;
    }

    public int addCourses(final int i, List<StudentCourse> courses) {
        int addedCoursesCount = 0;
        if (i >= 0 && i < degrees.size() && courses != null) {
            Degree degree = degrees.get(i);

            for (StudentCourse course : courses) {
                if (course != null && degree.addStudentCourse(course)) {
                    addedCoursesCount++;
                }
            }
        }
        return addedCoursesCount;

    }

    public void printCourses() {
        if (!degrees.isEmpty()) {
            for (Degree degree : degrees) {
                List<StudentCourse> courses = degree.getCourses();
                if (courses != null && !courses.isEmpty()) {
                    for (StudentCourse course : courses) {
                        System.out.println(course.toString());
                    }
                }
            }
        }
    }

    public void printDegrees() {
        if (!degrees.isEmpty() && degrees != null) {
            System.out.println(degrees);
        }
    }

    public void setTitleOfThesis(final int i, String title) {
        if ((title != null && !degrees.isEmpty()) && (i >= 0 && i < degrees.size())) {
            degrees.get(i).setTitleOfThesis(title);
        }
    }

    public boolean hasGraduated() {
        return (graduationYear <= Year.now().getValue());
    }

    private boolean canGraduate() {
        boolean hasBachelorThesis = false;
        boolean hasMasterThesis = false;
    
        Degree bachelor = degrees.get(0);
        Degree master = degrees.get(1);
    
        // check for bachelor thesis title, mandatory, and total credits
        if (!bachelor.getTitleOfThesis().equals(ConstantValues.NO_TITLE) &&
            bachelor.getCreditsByType(ConstantValues.MANDATORY) >= ConstantValues.BACHELOR_MANDATORY &&
            bachelor.getCredits() >= ConstantValues.BACHELOR_CREDITS) {
            hasBachelorThesis = true;
        }
    
        // check for master thesis title, mandatory, and total credits
        if (!master.getTitleOfThesis().equals(ConstantValues.NO_TITLE) &&
            master.getCreditsByType(ConstantValues.MANDATORY) >= ConstantValues.MASTER_MANDATORY &&
            master.getCredits() >= ConstantValues.MASTER_CREDITS) {
            hasMasterThesis = true;
        }
    
        // graduation year is set and before the current year
        boolean isGraduationYearValid = graduationYear > 0 && graduationYear <= Year.now().getValue();
    
        // true only if all conditions are met
        return hasBachelorThesis && hasMasterThesis && isGraduationYearValid;
    }

    public int getStudyYears() {
        if (hasGraduated()) {
            return graduationYear - startYear;
        } else {
            return Year.now().getValue() - startYear;
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        //TODO toString
        return str.toString();
    }

    @Override
    public String getIdString(){
        return "Student id: " + id;
    }

}
