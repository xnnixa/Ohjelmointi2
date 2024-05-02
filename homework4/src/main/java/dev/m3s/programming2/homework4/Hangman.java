package dev.m3s.programming2.homework4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hangman {

    // ATTRIBUTES
    private String wordTBG; //word to be guessed
    private int guessCount;
    private List<Character> guessedList = new ArrayList<>();

    // CONSTRUCTOR
    public Hangman(WordList words, int guesses) {
        List<String> hangmanWords = words.giveWords();

        if (!hangmanWords.isEmpty()) {
            Random hangman = new Random();
            int rIndex = hangman.nextInt(hangmanWords.size());
            wordTBG = hangmanWords.get(rIndex);
        }
        guessCount = guesses;
    }

    // METHODS

    // The method compares the character entered as a parameter to the word being
    // guessed and adds the guess
    // to the list of guesses if the character had not been added to the list, yet.
    // If the character is found in the
    // word being guessed, the method will return true. If character is not found
    // from the word being guessed,
    // the number of guesses is reduced by one and the method will return false.
    public boolean guess(Character c) {    
        char[] wordArray = wordTBG.toCharArray(); // turn word into array to iterate through
        c = Character.toLowerCase(c);

        for (int i = 0; i < wordArray.length; i++) {
            char letterTBG = wordArray[i];
            if (c == letterTBG) {
                if (!guessedList.contains(c)) {
                    guessedList.add(c);
                }
                return true;
            }
        }

        guessCount--;
        return false;
    }

    // The method returns the guesses made (as a List of Character objects). Each character should be in the list
    // only once (even if the user has guessed the same character several times)
    public List<Character> guesses(){
        return guessedList;
    }

    // The method returns the number of remaining guesses. Note, must not be negative.
    public int guessesLeft(){
        return guessCount;
    }

    // The method returns the selected word (unmasked, i.e., as read from the file).
    public String word(){
        return wordTBG;
    }

    // The method indicates whether the game is over or not. The game ends if all the letters in the word are
    // guessed correctly or the user has no more guesses left (= too many wrong guesses).
    public boolean theEnd(){
        if (guessCount == 0 ) {     // tai peli läpäisty
            return true;
        }
        return false;
    }

}
