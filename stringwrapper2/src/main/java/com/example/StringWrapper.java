package com.example;

public class StringWrapper {
    
    private String str = "";
    public void setStr(String newstr)
    {
        str = newstr;
    }
    
    public void setStr(int ival) {
        str = String.valueOf(ival);
    }
     
    public String getStr()
    {
        return str;
    }

    public static void main(String args[]){

        StringWrapper sw = new StringWrapper();
        sw.setStr(5);
        System.out.println(sw.getStr());
    }
}
