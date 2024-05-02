package dev.m3s.programming2.homework4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordList {

    //ATTRIBUTES
    private List<String> wordlist = new ArrayList<>();

    //CONSTRUCTOR
    public WordList(String words) {
        try (Scanner fileReader = new Scanner(new File(words))) {   //open file, try-catch to catch exceptions
            while (fileReader.hasNextLine()){                       //while there is a line following this one, add word to wordlist
                String word = fileReader.nextLine();
                word = word.toLowerCase();
                wordlist.add(word);
            }
            fileReader.close();                                     //close file
        } catch (FileNotFoundException e) {                         //catch exception and notify user
            System.out.println(e.getMessage());
        }
    }

    //METHODS
    public List<String> giveWords(){
        return wordlist;
    }

    
}
