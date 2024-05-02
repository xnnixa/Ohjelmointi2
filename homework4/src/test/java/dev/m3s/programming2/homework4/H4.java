package dev.m3s.programming2.homework4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;

import dev.m3s.maeaettae.jreq.BooleanMethods;
import dev.m3s.maeaettae.jreq.Classes;
import dev.m3s.maeaettae.jreq.Constructors;
import dev.m3s.maeaettae.jreq.IntMethods;
import dev.m3s.maeaettae.jreq.Methods;
import dev.m3s.maeaettae.jreq.Packages;
import dev.m3s.maeaettae.jreq.StringMethods;
import dev.m3s.maeaettae.jreq.Types;

class H4 {
	private static final String TEST_FILE_ERROR = "Test word file could not be %s! This is an error in the testing program, not in the program being tested. Please try again later. If the program does not solve itself after a few tries, please contact a teacher for assistance.";
	private static final Packages location = Packages.requirePackage(H4.class.getPackageName());
	protected static final Random random = new Random();
	protected static final String testFilePrefix = "words-";
	protected static final String testFileSuffix = ".txt";
	protected static File testFile;
	protected static ArrayList<String> testWords;

	// Classes
	static final Classes classWordList = location.hasClass().hasName("WordList");
	static final Classes classHangman = location.hasClass().hasName("Hangman");
	static final Classes classMain = location.hasClass().hasName("Main");

	// Constructors
	protected static final Constructors wordListConstructor = classWordList
			.hasConstructor()
			.hasParameters(String.class);

	protected static final Constructors hangmanConstructor = classHangman
			.hasConstructor()
			.hasParameters(classWordList)
			.hasParameter(int.class);

	protected static final Constructors mainConstructor = classMain
			.hasConstructor();

	// Methods
	protected static final BooleanMethods guess = classHangman
			.booleanMethod()
			.hasName("guess")
			.hasParameters(Character.class)
			.isPublic();

	protected static final Methods guesses = classHangman
			.hasMethod().hasName("guesses")
			.hasReturnType(Types.parameterized(List.class, Character.class))
			.isPublic();

	protected static final IntMethods guessesLeft = classHangman
			.intMethod()
			.hasName("guessesLeft")
			.isPublic();

	protected static final StringMethods word = classHangman
			.stringMethod()
			.hasName("word")
			.isPublic();

	protected static final BooleanMethods theEnd = classHangman
			.booleanMethod()
			.hasName("theEnd")
			.isPublic();

	protected static final Methods giveWords = classWordList
			.hasMethod()
			.hasName("giveWords")
			.hasReturnType(Types.parameterized(List.class, String.class))
			.isPublic();

	protected static final Methods theWordsOfLength = classWordList
			.hasMethod()
			.hasName("theWordsOfLength")
			.hasParameter(int.class)
			.hasReturnType(classWordList)
			.isPublic();

	protected static final Methods theWordsWithCharacters = classWordList
			.hasMethod()
			.hasName("theWordsWithCharacters")
			.hasParameter(String.class)
			.hasReturnType(classWordList)
			.isPublic();

	protected static final Methods mainMethod = classMain
			.hasMethod()
			.hasName("main")
			.hasParameters(String[].class)
			.hasReturnType(void.class);

	// populate the test word file with random words
	@BeforeAll
	static void populateTestFile() {
		try {
			testFile = File.createTempFile(testFilePrefix, testFileSuffix);
		} catch (IOException e) {
			throw new RuntimeException(String.format(TEST_FILE_ERROR, "created"), e);
		}
		testFile.deleteOnExit();

		testWords = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < random.nextInt(4, 20); j++) {
				sb.append(random.nextBoolean() ? (char) (random.nextInt(26) + 'a') : (char) (random.nextInt(26) + 'A'));
			}
			testWords.add(sb.toString().toLowerCase());
		}
		// Joint the words with a newline character
		String words = String.join("\n", testWords);
		// Write the words to the test file
		try (FileWriter fw = new FileWriter(testFile)) {
			fw.write(words);
		} catch (IOException e) {
			throw new RuntimeException(String.format(TEST_FILE_ERROR, "saved"), e);
		}
	}
}
