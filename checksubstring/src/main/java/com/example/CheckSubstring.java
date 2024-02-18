/*Kirjoita Java metodi...

String getSubstring(String s)

... joka palauttaa osamerkkijonon syötteestä s ensimmäiseen ':':n esiintymään asti

getSubstring("11:33") --> 11 */

package com.example;

import java.util.Scanner;

public class CheckSubstring {

    static String getSubstring(String s) {
       
        String ss = new String();

        int indexColon = s.indexOf(':'); 

        if (indexColon != -1) {
            ss = new String(s.substring(0, indexColon));
        } else {
            ss = new String(s);
        }

        return ss;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        String s, ss;

        System.out.print("Enter string > ");
        s = myObj.nextLine();

        ss = getSubstring(s);

        System.out.println(ss);

        myObj.close();
    }

}
