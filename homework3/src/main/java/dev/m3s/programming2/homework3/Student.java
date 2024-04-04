package dev.m3s.programming2.homework3;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person  {

    private int id;
    private int startYear = Year.now().getValue();
    private int graduationYear;
    private List<Degree> degrees = new ArrayList<>();

    public Student(String lname, String fname) {
        super(lname, fname);
        this.id = getRandomId(ConstantValues.MIN_STUDENT_ID, ConstantValues.MAX_STUDENT_ID);
    }

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
        if (dName != null && i <= 0 && i < degrees.size()) {
            //set title for given degree (see class Degree))
        }
    }



    private boolean canGraduate() { // implementation missing
        return true;
    }

    @Override
    public String getIdString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdString'");
    }
    
}
