package dev.m3s.programming2.homework4;

import static dev.m3s.programming2.homework4.H4Matcher.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.m3s.maeaettae.jreq.Quantifier;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories({ @TestCategory("Hangman") })
class HangmanTest extends H4 {
	private Object wordList;
	private Object hangman;
	private final int totalGuesses = 10;

	@BeforeEach
	void init() {
		wordList = wordListConstructor.instantiate(testFile.getAbsolutePath());
		hangman = hangmanConstructor.instantiate(wordList, totalGuesses);
	}

	
	@Test
	@DisplayName("All attributes are private and non-static")
	@Points(category = "Hangman")
	void attributesTest()
	{
		classHangman.hasField()
			.isPrivate()
			.require(Quantifier.FOR_ALL);

		classHangman.hasField()
			.isStatic()
			.require(Quantifier.DOES_NOT_EXIST);
	}

	@Test
	@DisplayName("Is the word returned by word method in the test file")
	@Points(category = "Hangman")
	void wordTest() {
		assertTrue(testWords.contains(word.call(hangman)),
				explain("Expected the word returned by word method to be in the test file"));
		// Test that the word is not changed by word calls
		String currentWord = word.call(hangman);
		for (int i = 0; i < 100; i++) {
			assertEquals(word.call(hangman), currentWord,
					explain("The word returned by the word method was not the same after multiple calls"));
		}
	}

	@Test
	@DisplayName("Test that the constructor selects a random word")
	@Points(category = "Hangman")
	void randomWordTest() {
		boolean result = false;
		String word1 = word.call(hangman);

		for (int i = 0; i < 100; i++) {
			Object newGame = hangmanConstructor.instantiate(wordList, 10);
			if (!word1.equals(word.call(newGame))) {
				result = true;
				break;
			}
		}
		assertTrue(result, explain("Expected the word selected by the constructor to be random"));
	}

	@Test
	@DisplayName("Test if quess method returns correct values")
	@Points(category = "Hangman")
	void guessTest() {
		List<Character> word1 = Arrays
				.asList((ArrayUtils.toObject(word.call(hangman).toCharArray())));
		List<Character> alphabet = getCharactersNotInWord(word.call(hangman));
		// Get random character from the word
		char wordChar = word1.get(random.nextInt(0, word1.size()));
		char notWordCharUpper = alphabet.get(random.nextInt(0, alphabet.size()));

		assertTrue(guess.call(hangman, wordChar),
				explain("Expected the guess method to return true for a correct guess. Guessed " + wordChar
						+ " for a word containing characters: " + word1));
		assertFalse(guess.call(hangman, notWordCharUpper),
				explain("Expected the guess method to return false for an incorrect guess. Guessed " + notWordCharUpper
						+ " for a word containing characters: "
						+ word1));
	}

	@Test
	@DisplayName("Test guess method case sensitivity")
	@Points(category = "Hangman")
	void guessCaseSensitivityTest() {
		List<Character> word1 = Arrays
				.asList((ArrayUtils.toObject(word.call(hangman).toUpperCase().toCharArray())));
		List<Character> alphabet = getCharactersNotInWord(word.call(hangman));
		// Get random character from the word
		char charUpper = word1.get(random.nextInt(0, word1.size()));
		char charLower = Character.toLowerCase(charUpper);
		char notWordCharUpper = alphabet.get(random.nextInt(0, alphabet.size()));
		char notWordCharLower = Character.toLowerCase(notWordCharUpper);

		boolean correctLowerCaseGuess = guess.call(hangman, charLower);
		boolean correctUpperCaseGuess = guess.call(hangman, charUpper);
		
		assertEquals(correctLowerCaseGuess, correctUpperCaseGuess,
				explain("Expected calling guess method with a correct character \'" + charUpper + "\' in uppercase"
				+ " to return the same value as when calling it with the same character \'" + charLower + "\' in lowercase.")
				.expected(String.format("guess(\'%c\') to return %b", charUpper, correctLowerCaseGuess)).but("returned " + correctUpperCaseGuess));

		boolean incorrectLowerCaseGuess = guess.call(hangman, notWordCharLower);
		boolean incorrectUpperCaseGuess = guess.call(hangman, notWordCharUpper);

		assertEquals(incorrectLowerCaseGuess, incorrectUpperCaseGuess,
				explain("Expected calling guess method with an incorrect character \'" + notWordCharUpper + "\' in uppercase"
				+ " to return the same value as when calling it with the same character \'" + notWordCharLower + "\' in lowercase.")
				.expected(String.format("guess(\'%c\') to return %b", notWordCharUpper, incorrectLowerCaseGuess)).but("returned " + incorrectUpperCaseGuess));
	}

	@Test
	@DisplayName("Test that guesses method returns correct values")
	@Points(category = "Hangman")
	void guessesTest() {
		char randomChar = (char) random.nextInt(97, 123);
		Set<Character> madeGuesses = new HashSet<>();
		madeGuesses.add(randomChar);
		guess.call(hangman, randomChar);
		List<Character> guessesNow = (List<Character>) guesses.call(hangman);
		assertEquals(guessesNow.size(), 1, explain("Expected the guesses method to return a list of size 1 after one guess"));
		assertEquals(Character.toLowerCase(guessesNow.get(0)), randomChar,
				explain("Expected the guesses method to return the guessed character"));

		int repeat = random.nextInt(Math.floorDiv(totalGuesses, 2), totalGuesses);
		for (int i = 1; i <= repeat; i++) {
			randomChar = (char) random.nextInt(97, 123);
			madeGuesses.add(randomChar);
			guess.call(hangman, randomChar);
			guessesNow = (List<Character>) guesses.call(hangman);

			assertEquals(guessesNow.size(), madeGuesses.size(),
					explain("Expected the guesses method to return a list of size " + madeGuesses.size() + " after " + (i+1)
							+ " guesses"));
			assertTrue(
					guessesNow.contains(Character.toUpperCase(randomChar))
							|| guessesNow.contains(Character.toLowerCase(randomChar)),
					"Expected the guesses method to return the guessed character");
		}

		// convert all guessed characters to lowercase
		for (int i = 0; i < guessesNow.size(); i++) {
			guessesNow.set(i, Character.toLowerCase(guessesNow.get(i)));
		}

		assertTrue(guessesNow.containsAll(madeGuesses),
				"Expected the guesses method to return all the guessed characters. Guessed " + madeGuesses + " but got "
						+ guessesNow);
	}

	@Test
	@DisplayName("Test that the guessesLeft method reduces guesses correctly")
	@Points(category = "Hangman")
	void guessesLeftTest1() {
		int initialLeft = guessesLeft.call(hangman);
		int guesses = 0;
		assertEquals(initialLeft, totalGuesses,
				explain("Expected the guessesLeft method to return " + totalGuesses + " at the start of the game"));
		List<Character> alphabet = getCharactersNotInWord(word.call(hangman));
		for (int i = 0; i < totalGuesses; i++) {
			guesses++;
			char randomChar = alphabet.remove(random.nextInt(0, alphabet.size()));
			guess.call(hangman, randomChar);
			assertEquals(guessesLeft.call(hangman), totalGuesses - i - 1, 
					explain("Expected the guessesLeft method to return " + (totalGuesses - i - 1) + " after " + (i + 1) + " guesses"));
		}

		// One more guess should result in 0 guesses left
		char randomChar = alphabet.remove(random.nextInt(0, alphabet.size()));
		guess.call(hangman, randomChar);
		assertEquals(guessesLeft.call(hangman), 0,
				explain("Expected the guessesLeft to return 0 after " + (guesses) + " guesses"));
	}

	@Test
	@DisplayName("Test that the guessesLeft method handles correct guesses correctly")
	@Points(category = "Hangman")
	void guessesLeftTest2() {
		int initialLeft = guessesLeft.call(hangman);
		assertEquals(initialLeft, totalGuesses, 
				explain("Expected the guessesLeft method to return " + totalGuesses + " at the start of the game"));
		Set<Character> wordSet = new HashSet<>();
		for (char c : word.call(hangman).toUpperCase().toCharArray()) {
			wordSet.add(c);
		}
		List<Character> wordChars = new ArrayList<>(wordSet);
		for (int i = 0; i < wordChars.size(); i++) {
			char randomChar = wordChars.get(i);
			guess.call(hangman, randomChar);
			assertEquals(guessesLeft.call(hangman), totalGuesses,
					explain("Expected the guessesLeft method to return " + totalGuesses + " after " + (i + 1) + " correct guesses"));
		}
		for (int i = 0; i < wordChars.size(); i++) {
			char randomChar = Character.toLowerCase(wordChars.get(i));
			guess.call(hangman, randomChar);
			assertEquals(guessesLeft.call(hangman), totalGuesses, 
					explain("Expected the guessesLeft method to return " + totalGuesses + " after " + (i + 1) + " correct guesses"));
		}
	}

	@Test
	@DisplayName("Test that the guessesLeft method returns correct values after correct guesses")
	@Points(category = "Hangman")
	void guessesLeftCorrectGuessesTest() {
		int initialLeft = guessesLeft.call(hangman);
		assertEquals(initialLeft, totalGuesses,
				explain("Expected the guessesLeft method to return " + totalGuesses + " at the start of the game"));
		List<Character> word1 = Arrays
				.asList((ArrayUtils.toObject(word.call(hangman).toCharArray())));
		// Get random character from the word
		char wordChar = word1.get(random.nextInt(0, word1.size()));

		guess.call(hangman, wordChar);
		assertEquals(guessesLeft.call(hangman), totalGuesses,
				explain("Expected the guessesLeft method to return " + totalGuesses + " after a correct guess"));
	}

	@Test
	@DisplayName("Test that the game ends when there are no guesses left")
	@Points(category = "Hangman")
	void theEndTest() {
		assertFalse(theEnd.call(hangman), "Expected theEnd method to return false at the start of the game");
		List<Character> alphabet = getCharactersNotInWord(word.call(hangman));
		int guesses = 0;

		for (int i = 0; i < totalGuesses - 1; i++) {
			guesses++;
			char randomChar = alphabet.remove(random.nextInt(0, alphabet.size()));
			guess.call(hangman, randomChar);
			assertFalse(theEnd.call(hangman),
					explain("Expected theEnd method to return false when the correct word has not been guessed, and there are "
							+ (i + 1) + " guesses left"));
		}
		char randomChar = alphabet.remove(random.nextInt(0, alphabet.size()));
		guess.call(hangman, randomChar);
		assertTrue(theEnd.call(hangman),
				explain("Expected theEnd method to return true after running out of guesses after making " + (guesses + 1) + " guesses"));
	}

	@Test
	@DisplayName("Test that the game ends when the word is guessed")
	@Points(category = "Hangman")
	void theEndWinTest() {
		assertFalse(theEnd.call(hangman), "Expected theEnd method to return false at the start of the game");
		Set<Character> wordSet = new HashSet<>();
		for (char c : word.call(hangman).toUpperCase().toCharArray()) {
			wordSet.add(c);
		}
		System.out.print("Word characters: ");
		System.out.println(wordSet);
		List<Character> wordChars = new ArrayList<>(wordSet);
		for (int i = 0; i < wordChars.size() - 1; i++) {
			char nextGuess = wordChars.get(i);
			guess.call(hangman, nextGuess);
			assertFalse(theEnd.call(hangman),
					explain("Expected theEnd method to return false when the correct word has not been guessed, and there are "
							+ guessesLeft.invoke(hangman) + " guesses left"));
		}
		char lastGuess = wordChars.get(wordChars.size() - 1);
		guess.call(hangman, lastGuess);
		assertTrue(theEnd.call(hangman),
				explain("Expected theEnd method to return true after having correctly guessed the word while having "
						+ guessesLeft.invoke(hangman) + " guesses left"));
	}

	private List<Character> getCharactersNotInWord(String word) {
		List<Character> word1 = Arrays
				.asList((ArrayUtils.toObject(word.toUpperCase().toCharArray())));
		List<Character> alphabet = new LinkedList<>(
				Stream.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")).map(s -> s.charAt(0)).toList());
		alphabet.removeAll(word1);
		return alphabet;
	}
}
