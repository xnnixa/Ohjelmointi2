package dev.m3s.programming2.homework3;

public class ResponsibleTeacher extends Employee implements Teacher {
        
    public ResponsibleTeacher(String lname, String fname) {
        super(lname, fname);
    }


    
    
    @Override
public String getCourses() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCourses'");
}

@Override
protected String getEmployeeIdString() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEmployeeIdString'");
}

}