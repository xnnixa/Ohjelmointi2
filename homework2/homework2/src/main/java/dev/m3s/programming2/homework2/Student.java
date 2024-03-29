package dev.m3s.programming2.homework2;

import java.time.Year;

public class Student {

    // ATTRIBUTES

    private String firstName = ConstantValues.NO_NAME;
    private String lastName = ConstantValues.NO_NAME;
    private int id;
    private int startYear = Year.now().getValue();
    private int graduationYear = -1;
    private int degreeCount;
    private Degree[] degrees;
    private String birthDate = ConstantValues.NO_BIRTHDATE;

    // CONSTRUCTORS

    public Student() {
        id = getRandomId();
        startYear = Year.now().getValue();
        degrees = new Degree[3];
        for (int i = 0; i < degrees.length; i++) {
            degrees[i] = new Degree();
        }
        degreeCount = degrees.length;
    }

    public Student(String lname, String fname) {
        this();
        if (fname != null) {
            firstName = fname;
        }
        if (lname != null) {
            lastName = lname;
        }
    }

    // METHODS

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        if (id <= ConstantValues.MAX_ID && id >= ConstantValues.MIN_ID) {
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
        
        if (graduationYear <= startYear && graduationYear >= Year.now().getValue()) {
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
        if (dName != null && i >= 0 && i < degreeCount) {
            degrees[i].setDegreeTitle(dName);
        }
    }

    public boolean addCourse(final int i, StudentCourse course) {
        if (i >= 0 && i < degreeCount && course != null) {
            return degrees[i].addStudentCourse(course);
        }
        return false;
    }

    public int addCourses(final int i, StudentCourse[] courses) {
        if (i >= 0 && i < degreeCount && courses != null) {
            int initialCourseCount = degrees[i].getCount();
            degrees[i].addStudentCourses(courses);
            int finalCourseCount = degrees[i].getCount();
            return finalCourseCount - initialCourseCount;
        }
        return 0;
    }

    public void printCourses() {
        for (int i = 0; i < degreeCount; i++) {
            for (StudentCourse course : degrees[i].getCourses()) {
                if (course != null) {
                    System.out.println(course);
                }
            }
        }
    }

    public void printDegrees() {
        for (Degree degree : degrees) {
            if (degree != null) {
                System.out.println(degree);
            }
        }
    }

    public void setTitleOfThesis(final int i, String title) {
        if (title != null && i >= 0 && i < degreeCount) {
            degrees[i].setTitleOfThesis(title);
        }
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String setBirthDate(String personId) {
        PersonID personID = new PersonID();
        String result = personID.setPersonId(personId);
        if ("Ok".equals(result)) {
            this.birthDate = personID.getBirthDate();
            return this.birthDate;
        } else {
            return "No change";
        }
    }

    public boolean hasGraduated() {
        return graduationYear != 0 && canGraduate();
    }

    private boolean canGraduate() {
        double bachelorCredits = 0;
        double masterCredits = 0;
        boolean hasBachelorThesis = false;
        boolean hasMasterThesis = false;

        // Checking credits and theses
        for (Degree degree : degrees) {
            if (degree != null) {
                bachelorCredits += degree.getCreditsByBase('A') + degree.getCreditsByBase('P');
                masterCredits += degree.getCreditsByBase('S');
                if (degree.getTitleOfThesis() != null && !degree.getTitleOfThesis().isEmpty()) {
                    if (masterCredits >= ConstantValues.MASTER_CREDITS) {
                        hasMasterThesis = true;
                    }
                    if (bachelorCredits >= ConstantValues.BACHELOR_CREDITS) {
                        hasBachelorThesis = true;
                    }
                }

            }
        }

        return (hasBachelorThesis && hasMasterThesis)
                && (graduationYear >= startYear && graduationYear <= Year.now().getValue());
    }

    public int getStudyYears() {

        if (hasGraduated()) {
            return graduationYear - startYear;
        } else {
            return Year.now().getValue() - startYear;
        }

    }

    private int getRandomId() {
        double doubleId = (Math.random() * 100);
        int id = (int) Math.ceil(doubleId);
        return id;
    }

    public String toString() {
        double bachelorCredits = 0;
        double masterCredits = 0;

        for (Degree degree : degrees) {
            if (degree != null) {
                bachelorCredits += degree.getCreditsByBase('A') + degree.getCreditsByBase('P');
                masterCredits += degree.getCreditsByBase('S');
            }
        }

        double totalCredits = bachelorCredits + masterCredits;

        StringBuilder str = new StringBuilder();
        str.append("Student id: " + getId() + "\n")
                .append("First name: " + firstName + ", " + "Last name: " + lastName + "\n")
                .append("Date of birth: " + birthDate + "\n")
                .append("Status: " + (hasGraduated() ? "The student has graduated in " + graduationYear
                        : "The student has not graduated, yet") + "\n")
                .append("Start year: " + startYear + " (studies have lasted for " + getStudyYears() + " years)" + "\n")
                .append("Total credits: " + totalCredits + "\n")
                .append("Bachelor credits: " + bachelorCredits + "\n")
                .append((hasGraduated()
                        ? "Total bachelor credits completed (" + bachelorCredits + "/" + ConstantValues.BACHELOR_CREDITS
                                + ")"
                        : "Missing bachelor credits " + (ConstantValues.BACHELOR_CREDITS - bachelorCredits) + " ("
                                + bachelorCredits + "/" + ConstantValues.BACHELOR_CREDITS + ")")
                        + "\n")
                .append("Title of BSc Thesis: " + degrees[ConstantValues.BACHELOR_TYPE].getTitleOfThesis() + "\n")
                .append("Master credits: " + masterCredits + "\n")
                .append((hasGraduated()
                        ? "Total master's credits completed (" + masterCredits + "/" + ConstantValues.MASTER_CREDITS
                                + ")"
                        : "Missing master's credits " + (ConstantValues.MASTER_CREDITS - masterCredits) + " ("
                                + masterCredits + "/" + ConstantValues.MASTER_CREDITS + ")")
                        + "\n")
                .append("Title of MSc Thesis: " + degrees[ConstantValues.MASTER_TYPE].getTitleOfThesis() + "\n");

        return str.toString();
    }

}
