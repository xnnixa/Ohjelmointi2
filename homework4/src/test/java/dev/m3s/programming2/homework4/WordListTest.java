package dev.m3s.programming2.homework4;

import static dev.m3s.programming2.homework4.H4Matcher.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.m3s.maeaettae.jreq.Quantifier;
import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;

@TestCategories({ @TestCategory("WordList") })
class WordListTest extends H4 {
	private Object wordList;

	@BeforeEach
	void init() {
		wordList = wordListConstructor.instantiate(testFile.getAbsolutePath());
	}

	@Test
	@DisplayName("All attributes are private and non-static")
	@Points(category = "WordList")
	void attributesTest() {
		classWordList.hasField()
				.isPrivate()
				.require(Quantifier.FOR_ALL);

		classWordList.hasField()
				.isStatic()
				.require(Quantifier.DOES_NOT_EXIST);

	}

	@Test
	@DisplayName("Test giveWords method returns all words")
	@Points(category = "WordList")
	void giveWordsTest() {
		List<String> words = (List<String>) giveWords.call(wordList);
		for (String word : testWords) {
			assertTrue(words.contains(word.toLowerCase()), "The word " + word + " was not returned by giveWords");
		}
	}
}
