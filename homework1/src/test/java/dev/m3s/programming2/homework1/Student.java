package dev.m3s.programming2.homework1;

import java.time.Year;

public class Student {

    private String firstName = ConstantValues.NO_NAME;
    private String lastName = ConstantValues.NO_NAME;
    private int id;
    private double bachelorCredits;
    private double masterCredits;
    private String titleOfMastersThesis = ConstantValues.NO_TITLE;
    private String titleOfBachelorThesis = ConstantValues.NO_TITLE;
    private int startYear = Year.now().getValue();
    private int graduationYear;
    private String birthDate = ConstantValues.NO_BIRTHDATE;

    public Student() {
        id = getRandomId();
    }

    public Student(String lname, String fname) {
        this.id = getRandomId();
        firstName = (fname != null) ? fname : ConstantValues.NO_NAME;
        lastName = (lname != null) ? lname : ConstantValues.NO_NAME;
    }

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

    public double getBachelorCredits() {
        return bachelorCredits;
    }

    public void setBachelorCredits(final double bachelorCredits) {
        if (bachelorCredits <= ConstantValues.MAX_CREDITS && bachelorCredits >= 0) {
            this.bachelorCredits = bachelorCredits;
        }
    }

    public double getMasterCredits() {
        return masterCredits;
    }

    public void setMasterCredits(final double masterCredits) {
        if (masterCredits <= ConstantValues.MAX_CREDITS && masterCredits >= 0) {
            this.masterCredits = masterCredits;
        }
    }

    public String getTitleOfMastersThesis() {
        return titleOfMastersThesis;
    }

    public void setTitleOfMastersThesis(String title) {
        if (title != null) {
            this.titleOfMastersThesis = title;
        }
    }

    public String getTitleOfBachelorThesis() {
        return titleOfBachelorThesis;
    }

    public void setTitleOfBachelorThesis(String title) {
        if (title != null) {
            this.titleOfBachelorThesis = title;
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(final int startYear) {
        if (startYear > 2000 && startYear <= 2024) {
            this.startYear = startYear;
        }
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public String setGraduationYear(final int graduationYear) {
        if (!canGraduate()) {
            return "Check the required studies";
        }
        if (graduationYear < startYear || graduationYear > Year.now().getValue()) {
            return "Check graduation year";
        } else {
            this.graduationYear = graduationYear;
            return "Ok";
        }
    }

    public boolean hasGraduated() {
        if (graduationYear != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean canGraduate() {
        return (bachelorCredits >= 180 && masterCredits >= 120)
                && (bachelorCredits + masterCredits) >= 300
                && (titleOfBachelorThesis != "No title")
                && (titleOfMastersThesis != "No title");
    }

    public int getStudyYears() {
        if (hasGraduated()) {
            return graduationYear - startYear;
        } else {
            return Year.now().getValue() - startYear;
        }
    }

    private int getRandomId() {
        double randomId = (Math.random() * 100) + 1;
        int id = (int) randomId;
        return id;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Student id: " + id + "\n");
        str.append("FirstName: " + firstName + ", " + "LastName: " + lastName + "\n");
        str.append("Date of birth: " + birthDate + "\n");
        String graduationStatus = hasGraduated() ? "graduated in " + graduationYear : "not graduated, yet";
        str.append("Status: " + "The student has " + graduationStatus + "\n");
        str.append("StartYear: " + startYear + " (studies have lasted for " + getStudyYears() + " years)" + "\n");
        str.append("BachelorCredits: " + bachelorCredits + (180 - bachelorCredits) + "(120.0/180.0)" + "\n");
        str.append("TitleOfBachelorThesis: " + titleOfBachelorThesis + "\n");
        str.append("MasterCredits: " + masterCredits + "(180.0/120.0)" + "\n");
        str.append("TitleOfMastersThesis: " + titleOfMastersThesis + "\n");

        return str.toString();
    }

    public String setPersonId(final String personID) {
        if (checkPersonIDNumber(personID)) {
            String birthDate = formatPersonIdToBirthDate(personID);
            if (checkBirthDate(birthDate)) {
                if (checkValidCharacter(personID)) {
                    return "Ok";
                } else {
                    return "Incorrect check mark!";
                }
            } else {
                return "Invalid birthday!";
            }
        } else {
            return "Invalid person ID format!";
        }
    }

    private boolean checkPersonIDNumber(final String personID) {
        if (personID == null) {
            return false;
        }
        if (personID.length() != 11) {
            return false;
        }
        char centuryChar = personID.charAt(6);

        if (centuryChar != '+' && centuryChar != '-' && centuryChar != 'A') {
            return false;
        }

        return checkValidCharacter(personID) && checkBirthDate(personID);
    }

    private boolean checkLeapYear(int year) {
        return ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)));
    }

    private boolean checkValidCharacter(final String personID) {
        char[] controlArray = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'H', 'J', 'K', 'L',
                'M', 'N', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'
        };
        Character suppliedChar = personID.charAt(10);
        String start = personID.substring(0, 6);
        String end = personID.substring(7, 10);
        Integer parsedId = Integer.parseInt(start + end);

        Double controlDouble = ((double) parsedId / 31) % (parsedId / 31) * 31;
        int controlLong = (int) Math.round(controlDouble);
        Character c = controlArray[controlLong];
        return suppliedChar.equals(c);
    }

    private boolean checkBirthDate(final String date) {

        String[] dateComponents = date.split("\\.");

        if (dateComponents.length != 3) {
            return false;
        }

        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        if (year > 0 && month >= 1 && month <= 12 && day >= 1 && daysInMonth(day, month, year)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean daysInMonth(int day, int month, int year) {

        if (month < 1 || month > 12 || day < 1) {
            return false;
        }

        int maxDaysInMonth;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                maxDaysInMonth = 30;
                break;
            case 2:
                maxDaysInMonth = (checkLeapYear(year)) ? 29 : 28;
                break;
            default:
                maxDaysInMonth = 31;
        }

        return day <= maxDaysInMonth;
    }

    private String formatPersonIdToBirthDate(String personId) {
        return personId.substring(0, 6) + "." + personId.substring(7, 9) + "." + "19" + personId.substring(7, 9);
    }
}