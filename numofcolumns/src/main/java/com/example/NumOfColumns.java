/*Kirjoita Java metodi...

boolean hasEqualColumlWidth(String s)

... joka saa CSV-formatoidun merkkijonon syötteenä ja tarkistaa onko jokaisella rivillä yhtä paljon sarakkeita.

hasEqualColumnWidth("1,2 \n 3,4")
hasEqualColumnWidth("a,b \n c") */

package com.example;

import java.util.Scanner;

public class NumOfColumns {
    
    static boolean hasEqualColumnWidth(String s) {
        boolean isEqual = true;

        String[] rows = s.split("\n");
        if (rows.length <= 1) {
            isEqual = false;
        }

        int expColumns = rows[0].split(",").length;

        for (int i = 1; i < rows.length; i++) {
            int currentColumns = rows[i].split(",").length;
            if (currentColumns != expColumns) {
                isEqual = false;
                break;
            }
        }

        return isEqual;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        int choice;
        String s = "";
        boolean isok = false;

        System.out.print("Enter num > ");
        choice = myObj.nextInt();

        switch (choice){
            case 1:
            s = "this\n";
            break;
            case 2:
            s = "this,asd\nthis";
            break;
            case 3:
            s = "this,asd\nasd,this";
            break;
        }

        isok = hasEqualColumnWidth(s);

        System.out.println(isok);

        myObj.close();
    }

}