
/*Kirjoita Java metodi...

String convertTime(String s)

... joka muuttaa ajan 12 tunnin hh:mm AM/PM muodosta 24 tunnin muotoon hh:mm.
Oletetaan, että syöte on oikea aika muodossa hh:mm AM/PM.

convertTime("01:34 PM") --> 13:34
convertTime("12:45 AM") --> 00:45 */

package com.example;

import java.util.Scanner;

public class TimeAMPM {


    static String convertTime(String s) {

        String time24;

        if (s.charAt(6)=='A') {
            time24 = s.substring(0,5);
            if (s.substring(0,2).equals("12")) {
                time24 = "00" + s.substring(2,5);
            }
        } else {
            int hour = Integer.parseInt(s.substring(0,2));
            if (hour != 12) {
                hour += 12;
            }    
            time24 = hour + s.substring(2, 5);      
        }
        
        return time24;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        String s, ss;

        System.out.print("Enter string > ");
        s = myObj.nextLine();

        ss = convertTime(s);

        System.out.println(ss);

        myObj.close();
    }
}