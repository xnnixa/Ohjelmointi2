/*Kirjoita Java metodi...

int getMinimum(int[] arr)

... joka saa listan kokonaislukuja syötteenä ja palauttaa listan pienimmän elementin.

Esimerkiksi:
input: 1 2 3 4 5
output: 1 */

package com.example;

import java.util.Scanner;

public class Minimum {

    static int getMinimum(int[] arr)
{
    int min = arr[0];
    
    for (int i = 0; i < arr.length; i++) {
        if (arr[i]<min)
            min = arr[i];
    }
    
    return min;
}

    public static void main(String[] args) {

        int nums = 5;
        Scanner myObj = new Scanner(System.in);
        int[] arr = new int[nums];
        int min = 0;

        for (int i = 0; i < nums; i++) {
            System.out.print("Enter number > ");
            arr[i] = myObj.nextInt();

        }

        min = getMinimum(arr);

        System.out.println(min);

        myObj.close();
    }
}
