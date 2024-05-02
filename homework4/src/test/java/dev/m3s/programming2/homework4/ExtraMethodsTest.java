package dev.m3s.programming2.homework4;

import static dev.m3s.programming2.homework4.H4Matcher.*;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@EnabledIfEnvironmentVariable(named = "CI_COMMIT_TAG", matches = "[Hh]omework[.\\-_]?4[.\\-_][Vv]2.*")
@TestCategories({ @TestCategory("Bonus") })
class ExtraMethodsTest extends H4 {
	private Object wordList;

	@BeforeEach
	void init() {
		wordList = wordListConstructor.instantiate(testFile.getAbsolutePath());
	}

	@Test
	@DisplayName("Test theWordsOfLength method returns correct amount of words")
	@Points(category = "Bonus")
	void testTheWordsOfLength1() {
		int maxWordLen = testWords.parallelStream().mapToInt((word) -> word.length()).max().getAsInt();
		for (int i = 1; i <= maxWordLen; i++) {
			final int len = i;
			ArrayList<String> expectedWords = testWords.parallelStream().filter((word) -> word.length() == len)
					.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
			System.out.println(expectedWords);
			Object actualWordsList = theWordsOfLength.call(wordList, len);
			ArrayList<String> actualWords = (ArrayList<String>) giveWords.call(actualWordsList);

			assertEquals(actualWords.size(), expectedWords.size(), 
					explain("theWordsOfLength(" + len + ") returned a list of the wrong size"));
		}
	}

	@Test
	@DisplayName("Test that words returned by theWordsOfLength method are of correct length")
	@Points(category = "Bonus")
	void testTheWordsOfLength2() {
		int maxWordLen = testWords.parallelStream().mapToInt((word) -> word.length()).max().getAsInt();
		for (int i = 1; i <= maxWordLen; i++) {
			final int len = i;
			ArrayList<String> expectedWords = testWords.parallelStream().filter((word) -> word.length() == len)
					.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
			System.out.println(expectedWords);
			Object actualWordsList = theWordsOfLength.call(wordList, len);
			ArrayList<String> actualWords = (ArrayList<String>) giveWords.call(actualWordsList);

			for (String word : actualWords) {
				assertEquals(word.length(), len,
						explain("theWordsOfLength(" + len + ") returned the word " + word + " which was not of length " + len));
			}
		}
	}

	@Test
	@DisplayName("Test that theWordsOfLength method returns an empty list for invalid lengths")
	@Points(category = "Bonus")
	void theWordsOfLengthTest3() {
		int maxWordLen = testWords.parallelStream().mapToInt((word) -> word.length()).max().getAsInt();
		Object wordsList = theWordsOfLength.call(wordList, 0);
		ArrayList<String> words = (ArrayList<String>) giveWords.call(wordsList);
		assertEquals(words.size(), 0, explain("theWordsOfLength(0) returned a list of the wrong size"));
		wordsList = theWordsOfLength.call(wordList, maxWordLen + 1);
		words = (ArrayList<String>) giveWords.call(wordsList);
		assertEquals(words.size(), 0, explain("theWordsOfLength(" + (maxWordLen + 1) + ") returned a list of the wrong size"));
	}

	@Test
	@DisplayName("Test that theWordsWithCharacters method returns the correct word")
	@Points(category = "Bonus")
	void theWordsWithCharactersTest() {
		for (String word : testWords) {
			String maskedWord = maskWord(word);
			Object wordsList = theWordsWithCharacters.call(wordList, maskedWord);
			ArrayList<String> words = (ArrayList<String>) giveWords.call(wordsList);
			assertTrue(words.contains(word),
					explain("Expected theWordsWithCharacters(\"" + maskedWord + "\") to return the word " + word));
		}
	}

	@Test
	@DisplayName("Test that theWordsWithCharacters method returns all words matching the mask")
	@Points(category = "Bonus")
	void theWordsWithCharactersTest2() {
		for (String word : testWords) {
			String maskedWord = (String) maskWord(word);
			Object wordsList = theWordsWithCharacters.call(wordList, maskedWord);
			ArrayList<String> words = (ArrayList<String>) giveWords.call(wordsList);
			String regex = maskedWord.replaceAll("_", ".");
			ArrayList<String> expectedWords = testWords.stream().filter(w -> w.matches(regex))
					.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
			System.out.print("Returned: ");
			System.out.println(words);
			System.out.print("Expected: ");
			System.out.println(expectedWords);
			assertEquals(words, expectedWords, explain("Expected theWordsWithCharacters(\"" + maskedWord
					+ "\") to return all words matching the mask"));
		}
	}

	private String maskWord(String word) {
		StringBuilder maskedWord = new StringBuilder();
		for (Character c : word.toCharArray()) {
			if (random.nextBoolean()) {
				maskedWord.append(c);
			} else {
				maskedWord.append("_");
			}
		}
		return maskedWord.toString();
	}
}
