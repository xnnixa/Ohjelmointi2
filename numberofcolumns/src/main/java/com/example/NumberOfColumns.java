package com.example;

import java.util.Scanner;

public class NumberOfColumns {
    
     static int numOfCols (String s) {
        int column = 1;
        
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (currentChar == ',') {
                column++;
            }
        }

        return column;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        int cols;
        String s = "";

        System.out.print("Enter num > ");
        s = myObj.nextLine();

        cols = numOfCols(s);

        System.out.println(cols);

        myObj.close();
    }
}
