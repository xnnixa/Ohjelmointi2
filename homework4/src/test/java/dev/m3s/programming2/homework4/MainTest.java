package dev.m3s.programming2.homework4;

import static org.junit.jupiter.api.Assertions.fail;
import static dev.m3s.programming2.homework4.H4Matcher.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.m3s.maeaettae.jreq.ConsoleUtils;
import dev.m3s.maeaettae.jreq.TargetException;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories({ @TestCategory("Main") })
class MainTest extends H4 {
	// Regex tests for the console output
	@Test
	@DisplayName("An integration test for the main method")
	@Points(category = "Main")
	void mainTest() {
		final String mainTestFile = "words.txt";
		int testWordLength = 10;

		// Used for creating the hidden word and the list of guess inputs
		List<Character> inputList = new ArrayList<Character>();

		// Used for storing the hidden word
		String hiddenWord = "";
		try {
			File testFile = new File(mainTestFile);
			testFile.deleteOnExit();
			FileWriter writer = new FileWriter(testFile, false);
			List<Character> alphabet = new LinkedList<>(
					Stream.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")).map(s -> s.charAt(0)).toList());

			// Create new random word consisting of only unique characters
			for (int j = 0; j < testWordLength; j++) {
				char c = alphabet.remove((int) (Math.random() * alphabet.size()));
				inputList.add(c);
			}
			// Write the generated word into the test file
			hiddenWord = inputList.stream()
					.map(String::valueOf).collect(Collectors.joining());
			writer.write(hiddenWord.toString());
			writer.close();
		} catch (IOException e) {
			fail("An unexpected error occurred. Try again later.");
		}

		// Shuffle until the first character to be guessed is not the
		// first character that is in the generated string.
		// This serves two purposes: 1. the shuffling itself is necessary
		// so that the word is not just revealed from left to right, and
		// 2. the regular expression doesn't confuse the printed state of the
		// game has the list of past guesses that might be printed to console
		// too. For example, if past guesses are printed as [T, E, S, T]
		// and the word was T E S T, the regular expression accepts the list
		// of past guesses as the completed word. I have taken it into account
		// here, although it's an extreme edge case has barely any impact
		// to test validity.
		Character firstChar = inputList.get(0);
		while (Character.compare(inputList.get(0), firstChar) == 0)
			Collections.shuffle(inputList);

		List<Character> gameState = new ArrayList<Character>();
		StringJoiner newInput = new StringJoiner("\n");
		for (char c : inputList) {
			newInput.add(String.valueOf(c));
			gameState.add(' ');
		}

		// Start capturing console output, provide the main method has a series
		// of guesses in the input buffer, call the method,
		// and store the produced output
		ConsoleUtils.writeConsole(newInput.toString());
		Object main = mainConstructor.instantiate();
		List<String> gameStateExamples = new ArrayList<>();
		try {
			Map<String, List<String>> lines = null;
			try{
				lines = ConsoleUtils.getConsoleOutput(mainMethod, main, mainTestFile);
			}catch(dev.m3s.maeaettae.jreq.TargetException e){
				fail(String.format("Unable to access the %s file. Ensure that the text file used by the program is correctly named", mainTestFile));
			}
			int matches = 0;
			Pattern pattern = Pattern.compile(
					buildRegexPattern(gameState),
					Pattern.CASE_INSENSITIVE);

			// Go through the lines of the console output, one by one, searching for
			// multiple game state strings that each show one more character than the
			// previous corresponding string
			for (String line : lines.get("output")) {
				boolean matchFound = pattern.matcher(line).find();
				if (matchFound) {
					// For debugging
					System.out.println("\nA match!\n\n" + line + "\n" + pattern.toString());
					matches++;
					gameStateExamples.add(line);

					// Keep searching until a match has been found for each guessed character
					if (matches <= testWordLength) {

						// What was the character guessed at this point?
						Character latestGuess = inputList.get(matches - 1);

						// Where was in the hidden word?
						int index = hiddenWord.indexOf(latestGuess);

						// Where should it be revealed?
						gameState.set(index, latestGuess);

						// What pattern should find it?
						pattern = Pattern.compile(
								buildRegexPattern(gameState),
								Pattern.CASE_INSENSITIVE);
					}
				}
			}
			assertTrue(matches > 0, explain(String.format("No game states were printed to the console. Check the main method.")));
			// get random example from the list of gameStates
			String randomExample = gameStateExamples.get(new Random().nextInt(0, gameStateExamples.size()));

			assertTrue(matches >= testWordLength, 
					explain(String.format("Expected the amount of times a game state (example: \"%s\") " +
					 "was printed (%d) to be higher or equal to the length of the word: (%d)", randomExample, matches, testWordLength)));
		} catch (TargetException exception) {
			String message = exception.getMessage();
			// if guess method is not used in main
			if (message.contains("NoSuchElementException")) {
				fail("'guess' method is not called in main");
			} else {
				throw exception;
			}
		}

	}

	/**
	 * This method builds regex patterns that are useful in recognising different
	 * styles of console outputs for the game state of hangman.
	 * This algorithm will probably be revisited and changed later, but here is
	 * the rationale behind my idea:
	 * An output for a game state has no correct guesses and 5 hidden letters can
	 * look something like:
	 * (1) _ _ _ _ _
	 * (2) - - - - -
	 * (3) * * * * *
	 * (4) -----
	 * (5) . . . . .
	 * ...or even something like:
	 * (6) ._.._.._.._.._.
	 * (7) -_-_-_-_-_
	 * If we then guess a letter correctly, the input can look (in more creative
	 * cases) something like:
	 * (8) ._.._.A._.._.
	 * (9) * * _A_ * *
	 * etc.
	 * We could go has a super lenient regex pattern, something like
	 * A[^a-zA-Z]*B[^a-zA-Z]*C.
	 * We could also go has a strict requirement, like enforcing the following
	 * pattern only: _ A _ B _
	 * However, if we don't want to be unnecessarily strict, but we also want
	 * there to be a structure of some kind, here is my proposal:
	 * I identified six different groups of characters that are possible in the
	 * output of the game state:
	 * (A) Letters, e.g. 'A'
	 * (B) Letter position markers, e.g. '_'
	 * (C) Markers between letters, e.g. ' ' (as in A-B)
	 * (D) Markers between positions, e.g. ' ' (as in _ _)
	 * (E) Markers between letters and positions, e.g. ' ' (as in A _B _)
	 * (F) and Markers between positions and letters, e.g. '' (as in A _B _)
	 *
	 * An example (here B: '_', C: '-', D: '', E: '', F: ' '):
	 * _______
	 * __ A____
	 * __ A_ P__
	 * __ A_ P-L_
	 * __ A_ P-L-E
	 * __ A-M-P-L-E
	 * E_ A-M-P-L-E
	 * E-X-A-M-P-L-E
	 *
	 * The solution: the first occurrence of a marker B-F gets a new numbered
	 * capturing group and the latter markers of the same category refer to that
	 * group using a backreference.
	 * The positional markers are found using .{1, X} (where X >= 1)
	 * And the other markers are found using .{0, X} (0 matches is OK)
	 * So in the end we build patterns like this:
	 * A(.{0,3})B(.{0,3})(.{1,3})(.{0,3})D\2\3
	 *
	 * There can be a better way, but this seems to work so far...
	 *
	 * @param guesses The list of guesses that have been made so far
	 * @return A regex pattern that can be used to match the output of the game
	 */
	protected String buildRegexPattern(List<Character> guesses) {
		int generosity = 3; // Change this to 2 or 1 if you want to accept less wild inputs.
		int currentBackreference = 0;
		int positionGroup = 0;
		int letterLetterGroup = 0;
		int positionPositionGroup = 0;
		int letterPositionGroup = 0;
		int positionLetterGroup = 0;

		String any = "(.{0," + generosity + "})";
		String pos = "(.{1," + generosity + "})";
		StringBuilder sb = new StringBuilder(guesses.size() * generosity * 2);

		for (int i = 0; i < guesses.size(); i++) {
			if (guesses.get(i) == ' ') { // position marker
				if (positionGroup == 0) { // first instance
					sb.append(pos);
					positionGroup = ++currentBackreference;
				} else
					sb.append("\\" + positionGroup);
				if (i < guesses.size() - 1) {
					if (guesses.get(i + 1) == ' ') { // Position -> Position
						if (positionPositionGroup == 0) {
							sb.append(any);
							positionPositionGroup = ++currentBackreference;
						} else
							sb.append("\\" + positionPositionGroup);
					} else { // Position -> Letter
						if (positionLetterGroup == 0) {
							sb.append(any);
							positionLetterGroup = ++currentBackreference;
						} else
							sb.append("\\" + positionLetterGroup);
					}
				}
			} else { // letter
				sb.append(guesses.get(i));
				if (i < guesses.size() - 1) {
					if (guesses.get(i + 1) == ' ') { // Letter -> Position
						if (letterPositionGroup == 0) {
							sb.append(any);
							letterPositionGroup = ++currentBackreference;
						} else
							sb.append("\\" + letterPositionGroup);
					} else { // Letter -> Letter
						if (letterLetterGroup == 0) {
							sb.append(any);
							letterLetterGroup = ++currentBackreference;
						} else
							sb.append("\\" + letterLetterGroup);
					}
				}
			}

		}
		return sb.toString();
	}
}
