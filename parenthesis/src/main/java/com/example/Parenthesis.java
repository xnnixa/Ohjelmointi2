package com.example;

import java.util.Scanner;

public class Parenthesis {

    static boolean checkParenthesis(String s) {
        boolean isok = false;
       
        int openCount = 0;

        for (char c : s.toCharArray()) {
            if (c== '(') {
                openCount++;
            } else if (c== ')') {
                if (openCount == 0) {
                    return false;
                }
                openCount--;
            }
        } 
        isok = openCount == 0;
        return isok;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        String s;
        boolean isok = false;

        System.out.print("Enter string > ");
        s = myObj.nextLine();

        isok = checkParenthesis(s);

        System.out.println("Is parenthesis: " + isok);

        myObj.close();
    }
}
