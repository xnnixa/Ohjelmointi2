package com.example;

public class Student {
    
    private String firstName = "No name";
    private String lastName = "No name";
    private int id;
    private double bachelorCredits; 
    private double masterCredits;
    private String titleOfMastersThesis = "No title";
    private String titleOfBachelorThesis = "No title";
    private int startYear;
    private int graduationYear;
    private String birthDate;

    public Student() {
        this.id;
    }

    public Student(String lname, String fname) {
        this.lname = lname;
        this.fname = fname;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {

    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {

    }
    public int getId () {
        return id;
    }
    public void setId(final int id) {

    }
    public double getBachelorCredits() {
        return bachelorCredits;
    }
    public void setBachelorCredits(final double bachelorCredits) {

    }
    public double getMasterCredits() {
        return masterCredits;
    }
    public void setMaterCredits(final double masterCredits) {

    }
    public String getTitleOfMastersThesis() {
        return titleOfMastersThesis;
    }
    public void setTitleOfMastersThesis(String title) {

    }
    public String getTitleOfBachelorThesis() {
        return titleOfBachelorThesis;
    }
    public void setTitleOfBachelorThesis(String title) {

    }
    public int getStartYear() {
        return startYear;
    }
    public void setStartYear(final int startYear) {

    }
    public int getGraduationYear() {
        return graduationYear;
    }
    public String setGraduationYear(final int graduationYear) {

    }
    public boolean hasGraduated() {
        boolean graduated;
        return graduated;
    }
    public boolean canGraduate() {
        boolean gratuatebility;
        return gratuatebility;
    }
    public int getStudyYears() {
        int studyYears = 0;
        return studyYears;
    }
    private int getRandomId() {
        id = 0;
        return id;
    }
}