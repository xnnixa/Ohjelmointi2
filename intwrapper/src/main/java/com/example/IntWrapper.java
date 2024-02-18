/*
Huomaa seuraava luokka:

class IntWrapper
{
    public int val;
}


Kirjoita koodirivit, joissa luot kaksi ilmeentymää (objektia) luokasta ja tallennat ne muuttujiin iw1 ja iw2. Aseta muuttujan iw1 alkio val numeroksi 2 ja muuttujan iw2 alkio val numeroksi 5. */

package com.example;

public class IntWrapper {
    
    public int val;

    public static void main(String args[]){

        IntWrapper iw1 = new IntWrapper();
        IntWrapper iw2 = new IntWrapper();

        iw1.val = 2;
        iw2.val = 5;

        check(iw1, iw2);

    }

    static void check(IntWrapper iw1, IntWrapper iw2){

        boolean w1 = false, w2 = false;
        if(iw1.val == 2)
            w1 = true;
        if(iw2.val == 5)
            w2 = true;
        
        System.out.println(w1);
        System.out.println(w2);
    }
}