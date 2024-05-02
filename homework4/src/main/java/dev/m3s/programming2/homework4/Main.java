package dev.m3s.programming2.homework4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        WordList words = new WordList("words.txt");
        Hangman hangman = new Hangman(words, 5);
        Scanner letterScanner = new Scanner(System.in);

        System.out.println("The hidden word...");
        for (int i = 0; i < hangman.word().length(); i++) {
            System.out.print("_ ");
        }
        System.out.println("Guesses left: " + hangman.guessesLeft());
        System.out.println("Guessed letters: " + hangman.guesses());
        System.out.println("\nGuess a letter: ");

        Character guessCharacter = letterScanner.nextLine().charAt(0);

        
    }
}
