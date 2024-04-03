package dev.m3s.programming2.homework3;

public class Degree {

    //ATTRIBUTES
    
    private static final int MAX_COURSES = 50;
    private int count = 0;
    private String degreeTitle = ConstantValues.NO_TITLE;
    private String titleOfThesis = ConstantValues.NO_TITLE;
    private StudentCourse[] myCourses = new StudentCourse[MAX_COURSES];

    //CONSTRUCTORS

    public StudentCourse[] getCourses() {
        return myCourses;
        
    }

    //METHODS

    public void addStudentCourses(StudentCourse[] courses) {
        if (courses != null) {
            for (StudentCourse course : courses) {
                addStudentCourse(course);
            }
        }  
    }

    public boolean addStudentCourse(StudentCourse course) {
        if (course != null && count < MAX_COURSES) {
            myCourses[count] = course;
            count++;
            return true;
        }
        return false;
    }

    public int getCount() {
        return count;
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

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Degree [Title: \"").append(degreeTitle).append("\"")
           .append(" (courses: ").append(myCourses != null ? count : 0).append(")").append("\n")
           .append("Thesis title: \"").append(titleOfThesis).append("\"\n");
    
        int count = 1;
        for (StudentCourse studentCourse : myCourses) {
            if (studentCourse != null) {
                Course course = studentCourse.getCourse();
                str.append(count++).append(". ")
                   .append(course.toString())

                .append(" Year: ").append(studentCourse.getYear()).append(", ")
                   .append("Grade: ").append(studentCourse.getGradeNum()).append("]\n");
            }
        }
        str.append("]");
        System.out.println("sysout: \n" + str);
        
        return str.toString();
    }


}
