package com.example;

import java.util.Scanner;

public class CheckDifference {

    static boolean almostEqual(double a, double b, double c) {

        return Math.abs(a - b) <= c;
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        double a, b, c;
        boolean isok = false;

        System.out.print("Enter number > ");
        a = myObj.nextDouble();
        System.out.print("Enter number > ");
        b = myObj.nextDouble();
        System.out.print("Enter diff > ");
        c = myObj.nextDouble();

        isok = almostEqual(a, b, c);

        System.out.println(isok);

        myObj.close();
    }
}