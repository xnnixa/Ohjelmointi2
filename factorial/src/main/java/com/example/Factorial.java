/*Kirjoita rekursiivinen funktio...

int factorial(int n)

... joka laskee positiivisen kokonaisluvun n kertoman (n!)

factorial(3) --> 6 */

package com.example;

import java.util.Scanner;

public class Factorial {

    static int factorial(int n) {

        if (n == 1 || n == 0) {
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        int a = 0, fac = 0;

        System.out.print("Enter number > ");
        a = myObj.nextInt();

        fac = factorial(a);

        System.out.println(fac);

        myObj.close();
    }
}
