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
        this.startYear = startYear;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public String setGraduationYear(final int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public boolean hasGraduated() {
        boolean isOk = true;
        return isOk;
    }

    public boolean canGraduate() {
        boolean isOk = true;
        return isOk;
    }

    public int getStudyYears() {
        int studyYears = 0;
        return studyYears;
    }

    private int getRandomId() {
        double doubleId = (Math.random() * 100);
        int id = (int) Math.round(doubleId);
        return id;
    }

    public String toString() {
        return null;
    }

    public String setPersonId(final String personID) {
        return null;
    }

    private boolean checkPersonIDNumber(final String personID) {

        return;
    }

    private boolean checkLeapYear(int year) {
        return ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)));
    }

    private boolean checkValidCharacter(final String personID) {
        return (Boolean) null;
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