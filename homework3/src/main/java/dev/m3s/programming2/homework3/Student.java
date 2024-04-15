package dev.m3s.programming2.homework3;

import java.text.NumberFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        degrees.add(0, new Degree());
        degrees.add(1, new Degree());
        degrees.add(2, new Degree());

        
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
        for (Degree degree : degrees) {
            System.out.println(degree.toString());
        }
    }

    public void setTitleOfThesis(final int i, String title) {
        if ((title != null && !degrees.isEmpty()) && (i >= 0 && i < degrees.size())) {
            degrees.get(i).setTitleOfThesis(title);
        }
    }

    public boolean hasGraduated() {
        return graduationYear == 0 ? false : true;
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
    
        // true only if all conditions are met
        return hasBachelorThesis && hasMasterThesis;
    }

    public int getStudyYears() {
        if (!hasGraduated()) {
            int currentYear = Year.now().getValue();
            return currentYear - startYear;
        }
        return graduationYear - startYear;
    }

    @Override
    public String toString(){

        double bachelorCredits = 0;
        double masterCredits = 0;

        // for (Degree degree : degrees) {
        //     if (degree != null) { // && on läpäisty??
        //         bachelorCredits += degree.getCreditsByBase('A') + degree.getCreditsByBase('P');
        //         System.out.println(bachelorCredits);
        //         masterCredits += degree.getCreditsByBase('S');
        //     }
        // }

            for (int i = 0; i < degrees.size(); i++) {
                if (i == 0) {
                    bachelorCredits = degrees.get(i).getCredits();
                }

                if (i == 1) {
                    masterCredits = degrees.get(i).getCredits();
                }
            }
        
        double totalCredits = bachelorCredits + masterCredits;
        double totalGPA = (degrees.get(ConstantValues.BACHELOR_TYPE).getGPA(ConstantValues.ALL).get(2)+degrees.get(ConstantValues.MASTER_TYPE).getGPA(ConstantValues.ALL).get(2)) / 2;
        StringBuilder str = new StringBuilder();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMANY);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

            str.append("Student id: " + id)
                .append("\n\t")
                .append("First name: " + getFirstName() + ", Last name: " + getLastName())
                .append("\n\t")
                .append("Date of birth: " + getBirthDate())
                .append("\n\t")
                .append("Status: "+ (hasGraduated() ? "The student has graduated in " + graduationYear : "The student has not graduated, yet"))
                .append("\n\t")
                .append("Start year: " + startYear + " (studies have lasted for " + getStudyYears() + " years)")
                .append("\n\t")
                .append("Total credits: " + totalCredits + " (GPA = " + nf.format(totalGPA) + ")")
                .append("\n\t")
                .append("Bachelor credits: " + bachelorCredits)
                .append("\n\t\t")
                .append("Total bachelor credits completed (" + bachelorCredits + "/" + ConstantValues.BACHELOR_CREDITS + ")")
                .append("\n\t\t")
                .append("All mandatory bachelor credits completed (" + degrees.get(ConstantValues.BACHELOR_TYPE).getCreditsByType(ConstantValues.MANDATORY) + "/" + ConstantValues.BACHELOR_MANDATORY + ")")
                .append("\n\t\t")
                .append("GPA of Bachelor studies: " + nf.format(degrees.get(ConstantValues.BACHELOR_TYPE).getGPA(ConstantValues.ALL).get(2)))
                .append("\n\t\t")
                .append("Title of BSc Thesis: \"" + degrees.get(ConstantValues.BACHELOR_TYPE).getTitleOfThesis() + "\"")
                .append("\n\t")
                .append("Master credits: " + masterCredits)
                .append("\n\t\t")
                .append("Missing master's credits " + (ConstantValues.MASTER_CREDITS - masterCredits) + " (" + masterCredits + "/" + ConstantValues.MASTER_CREDITS + ")")
                .append("\n\t\t")
                .append("All mandatory master credits completed (" + degrees.get(ConstantValues.MASTER_TYPE).getCreditsByType(ConstantValues.MANDATORY) + "/" + ConstantValues.MASTER_MANDATORY + ")")
                .append("\n\t\t")
                .append("GPA of Master studies: " + nf.format(degrees.get(ConstantValues.MASTER_TYPE).getGPA(ConstantValues.MASTER_TYPE).get(2)))
                .append("\n\t\t")
                .append("Title of MSc Thesis: \"" + degrees.get(ConstantValues.MASTER_TYPE).getTitleOfThesis() + "\"");
        return str.toString();
    }

    public String getIdString(){
        return "Student id: " + String.valueOf(id);
    }

}
