/*Kirjoita Java metodi...

void printArray(int[] arr)

... joka saa listan kokonaislukuja syötteenä ja tulostaa listan sisällön, yksi elementti per rivi. */

package com.example;

import java.util.Scanner;

public class IntegerArray {

    static void printArray(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    
    }

    public static void main(String[] args) {

        int nums = 3;
        Scanner myObj = new Scanner(System.in);
        int[] arr = new int[nums];
        

        for (int i = 0; i < nums; i++) {
            System.out.print("Enter number > ");
            arr[i] = myObj.nextInt();

        }

        printArray(arr);

        myObj.close();
    }

}