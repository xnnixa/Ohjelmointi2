package dev.m3s.programming2.homework3;

public abstract class Person {

    //ATTRIBUTES
    
    private String firstName = ConstantValues.NO_NAME;
    private String lastName = ConstantValues.NO_NAME;
    private String birthDate = ConstantValues.NOT_AVAILABLE;

    //CONSTRUCTORS

    public Person (String lname, String fname) {
        if (lname != null) {
            this.lastName = lname;
        }
        if (fname != null) {
            this.firstName = fname;
        }
    }

    //METHODS

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

    public String getBirthDate() {
        return birthDate;
    }

    protected int getRandomId(final int min, final int max) {
        int range = max - min;
        int id = (int) (Math.ceil(Math.random() * range) + min);
        return id;
    }

    public abstract String getIdString();
}
