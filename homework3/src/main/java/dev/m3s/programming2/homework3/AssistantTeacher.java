package dev.m3s.programming2.homework3;

public class AssistantTeacher extends Employee implements Teacher {  
    
public AssistantTeacher(String lname, String fname) {
        super(lname, fname);
        //TODO Auto-generated constructor stub
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
