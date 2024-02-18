package com.example;

public class StringWrapper {
    
    private String str = "";

    public StringWrapper(int ival) {
        str = String.valueOf(ival);
    }

    public StringWrapper(String istr)
    {
         str = istr;
    }
    public String getStr()
    {
        return str;
    }

    public static void main(String args[]){

        StringWrapper sw = new StringWrapper(1);
        String str = sw.getStr();
        System.out.println(str);
    }

}
