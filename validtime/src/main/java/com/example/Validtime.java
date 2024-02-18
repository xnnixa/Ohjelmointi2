/*Kirjoita Java metodi...

boolean isValidTime(String s)

... joka varmistaa onko annettu syöte s validi aika merkkijono formaatissa hh:mm. Oletetaan, että hh ja mm on numeroita.

isValidTime("13:45") ---> true
isValidTime("33:75") ---> false */

package com.example;

import java.util.Scanner;

public class Validtime {
    
    static boolean isValidTime(String s){
    boolean isvalid;

    int hours = Integer.parseInt(s.substring(0,2));
    int minutes = Integer.parseInt(s.substring(3, 5));

    if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) {
        isvalid = true;
    } else if (hours == 24 && minutes == 0){
        isvalid = true;
    } else {    
        isvalid = false;
    }
    return isvalid;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        String s;
        boolean isok = false;

        System.out.print("Enter string > ");
        s = myObj.nextLine();

        isok = isValidTime(s);

        System.out.println(isok);

        myObj.close();
    }

}
