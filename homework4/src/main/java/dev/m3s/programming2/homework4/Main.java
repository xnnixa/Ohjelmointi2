package dev.m3s.programming2.homework4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        WordList words = new WordList("words.txt");
        Hangman hangman = new Hangman(words, 5);
        Scanner letterScanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nThe hidden word...");
            hangman.displayWord();
            
            if(hangman.theEnd()){
                if(hangman.guessesLeft() > 0) {
                    System.out.println("\nCongratulations! You won!!!\nThe hidden word was: \"" + hangman.word() + "\"");
                } else {
                    System.out.println("\nSorry, you lost!\nThe hidden word was: \"" + hangman.word() + "\"");
                }
                break;
            }

            System.out.println("\n\nGuesses left: " + hangman.guessesLeft());
            System.out.println("Guessed letters: " + hangman.guesses());
            System.out.println("\nGuess a letter: ");

            Character guessCharacter = letterScanner.nextLine().charAt(0);
            hangman.guess(guessCharacter);

        }
        letterScanner.close();
    }
}
