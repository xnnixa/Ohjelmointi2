package dev.m3s.programming2.homework3;

public class PersonID {
    
    private String birthDate = ConstantValues.NO_BIRTHDATE;

    public String getBirthDate() {
        return (birthDate != null && !birthDate.equals(ConstantValues.NO_BIRTHDATE)) ? birthDate : "Not available";
    }

    public String setPersonId(final String personID) {
        if (personID == null) {
            return "Invalid birthday!";
        }
        if (!checkPersonIDNumber(personID)) {
            return "Invalid birthday!";
        }
    
        String birthDate = formatBirthDate(personID);
    
        if (!checkBirthDate(birthDate)) {
            return "Invalid birthday!";
        }
    
        if (!checkValidCharacter(personID)) {
            return "Incorrect check mark!";
        }
    
        this.birthDate = birthDate;
    
        return "Ok";
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

    private String formatBirthDate(final String personID) {
        String day = personID.substring(0, 2);
        String month = personID.substring(2, 4);
        String year = personID.substring(4, 6);
        String centuryChar = personID.substring(6, 7);
    
        if (centuryChar.equals("+")) {
            year = "18" + year;
        } else if (centuryChar.equals("-")) {
            year = "19" + year;
        } else if (centuryChar.equals("A")) {
            year = "20" + year;
        }
    
        return day + "." + month + "." + year;
    }

}
