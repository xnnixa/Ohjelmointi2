package com.example;

public class IntWrapper {
    
    private int val;

    public int getVal()
    {
        return val;
    }
     public void setVal(int newval)
    {
       this.val = newval;
    }

    public static void main(String args[]){

        IntWrapper iw1 = new IntWrapper();
        IntWrapper iw2 = new IntWrapper();

        iw1.setVal(2);
        iw2.setVal(5);

        check(iw1, iw2);

    }

    static void check(IntWrapper iw1, IntWrapper iw2){

        boolean w1 = false, w2 = false;
        if(iw1.getVal() == 2)
            w1 = true;
        if(iw2.getVal() == 5)
            w2 = true;
        
        System.out.println(w1);
        System.out.println(w2);
    }
}
