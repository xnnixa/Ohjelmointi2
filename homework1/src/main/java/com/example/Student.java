package com.example;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class Student {

    private String firstName = "No name";
    private String lastName = "No name";
    private int id;
    private double bachelorCredits;
    private double masterCredits;
    private String titleOfMastersThesis = "No title";
    private String titleOfBachelorThesis = "No title";
    private int startYear = 2024;
    private int graduationYear;
    private String birthDate = "Not available";

    public Student() {

    }

    public Student(String fname, String lname) {
        firstName = fname;
        lastName = lname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        } else {
            this.firstName = ConstantValues.NO_NAME;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        } else {
            this.lastName = ConstantValues.NO_NAME;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        if (id < ConstantValues.MAX_ID && id > ConstantValues.MIN_ID) {
            this.id = id;
        }
    }

    public double getBachelorCredits() {
        return bachelorCredits;
    }

    public void setBachelorCredits(final double bachelorCredits) {
        if (bachelorCredits < ConstantValues.MAX_CREDITS && bachelorCredits > 0) {
            this.bachelorCredits = bachelorCredits;
        }
    }

    public double getMasterCredits() {
        return masterCredits;
    }

    public void setMasterCredits(final double masterCredits) {
        if (masterCredits < ConstantValues.MAX_CREDITS && masterCredits > 0) {
            this.masterCredits = masterCredits;
        }
    }

    public String getTitleOfMastersThesis() {
        return titleOfMastersThesis;
    }

    public void setTitleOfMastersThesis(String title) {
        if (title != null) {
            this.titleOfMastersThesis = title;
        } else {
            this.titleOfMastersThesis = ConstantValues.NO_TITLE;
        }
    }

    public String getTitleOfBachelorThesis() {
        return titleOfBachelorThesis;
    }

    public void setTitleOfBachelorThesis(String title) {
        if (title != null) {
            this.titleOfBachelorThesis = title;
        } else {
            this.titleOfBachelorThesis = ConstantValues.NO_TITLE;
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(final int startYear) {
        if (startYear < 0 || startYear > 2024) {
            this.startYear = startYear;
        } else {
            this.startYear = 2024;
        }
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public String setGraduationYear(final int graduationYear) {
        if (canGraduate()) {
            this.graduationYear = graduationYear;
            return "Ok";
        } else if (bachelorCredits < 180 || masterCredits < 120) {
            return "Check the required studies";
        } else {
            return "Check graduation year";
        }
    }

    public boolean hasGraduated() {
        return canGraduate() && graduationYear != 0;
    }

    private boolean canGraduate() {
        return (bachelorCredits >= 180 && masterCredits >= 120)
                && (graduationYear >= startYear && graduationYear <= 2024)
                && (titleOfBachelorThesis != null && !titleOfBachelorThesis.isEmpty())
                && (titleOfMastersThesis != null && !titleOfMastersThesis.isEmpty());
    }

    public int getStudyYears() {
        if (hasGraduated()) {
            return graduationYear - startYear;
        } else {
            return -1;
        }
    }

    private int getRandomId() {
        double doubleId = (Math.random() * 100);
        int id = (int) Math.round(doubleId);
        return id;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Student id: " +id + "\n");
        str.append("FirstName: " +firstName + ", " );
        return null;
    }

    public String setPersonId(final String personID) {
        checkPersonIDNumber(personID);
        return null;
    }

    private boolean checkPersonIDNumber(final String personID) {
        if (personID.length() != 11) {
            return false;
        }
        char centuryChar = personID.charAt(6);

        if (centuryChar != '+' && centuryChar != '-' && centuryChar != 'A') {
            return false;
        }

        return true;
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

        Double controlDouble = ((double)parsedId / 31) % (parsedId / 31) * 31;
        int controlLong = (int)Math.round(controlDouble);
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
}