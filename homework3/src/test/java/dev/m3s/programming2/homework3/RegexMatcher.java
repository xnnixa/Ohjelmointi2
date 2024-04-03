package dev.m3s.programming2.homework3;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.text.MatchesPattern;

import dev.m3s.maeaettae.jreq.FailureMessage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

class RegexMatcher extends BaseMatcher<String> {

	private final Matcher<String> matcher;

	private final Pattern expected;
	private final String actual;

	private static final Map<String, String> metacharExplanations = Map.of(
			// @formatter:off
			//"^", "Match start of line, negation inside character class",
			//"$", "Match end of line",
			"(", "Start group (a \"capturing group\")",
			")", "End capturing group",
			".", "Match any character, literal '.' inside character class",
			"[", "Start character class (e.g. [abc])",
			"]", "End character class (e.g. [^A-Z])",
			"-", "Character range (e.g. [a-z])",
			"|", "Either or (e.g. cat|dog)",
			"?", "Element to the left is optional (e.g. colou?r)",
			"*", "Element to the left can occur zero or more times",
			"+", "Element to the left can occur one or more times"
			// @formatter:on
	);

	private static final Map<String, String> escapeExplanations = Map.of(
			// @formatter:off
			"\\s", "Match any whitespace character [ \\t\\n\\x0B\\f\\r]",
			"\\t", "Tab",
			"\\n", "New line",
			"\\f", "Form feed",
			"\\r", "Carriage return"
			// @formatter:on
	);

	public RegexMatcher(Object actual, Pattern regex) {
		Objects.requireNonNull(regex);
		this.matcher = MatchesPattern.matchesPattern(regex);
		this.expected = regex;
		this.actual = Objects.toString(actual);
	}

	@Override
	public boolean matches(Object o) {
		return matcher.matches(o);
	}

	@Override
	public void describeMismatch(Object item, Description description) {
		description.appendText("was: " + actual);
		description.appendText("""

				(<== see Console output for more context)
				
				Regular expression explanation:
				""");
		description.appendText(regexExplanation(expected));
	}

	@Override
	public void describeTo(Description description) {
		String regex = expected.toString();
		boolean tooLong = false;
		int firstLineMaxLength = 30;
		if (expected.toString()
				.length() > firstLineMaxLength) {
			tooLong = true;
			regex = FailureMessage.splitIntoLines(regex);
		}
		description.appendText(
				"a String matching the pattern:" + (tooLong ? "\n" : " ")
						+ regex);
	}

	public static void assertMatches(Object actual, Pattern expected) {
		assertThat(Objects.toString(actual), new RegexMatcher(actual, expected));
	}

	public static void assertMatches(Object actual, Pattern expected,
			String failureReason) {
		assertThat(FailureMessage.splitIntoLines(failureReason),
				Objects.toString(actual), new RegexMatcher(actual, expected)
		);
	}

	// Explains the meaning of individual regex components
	static String regexExplanation(Pattern regex) {
		Set<String> explanations = new HashSet<>();
		String regexString = regex.toString();
		// Iterate over available explanations, matching them against the regex
		for (Map.Entry<String, String> entry :
				metacharExplanations.entrySet()) {
			if (regexString.contains(entry.getKey())) {
				// Find all occurrences of the metacharacter in the regex
				int index = regexString.indexOf(entry.getKey());
				while (index >= 0) {
					// Add the explanation of the character if not escaped
					if (isNotEscaped(index, regexString)) {
						explanations.add(
								String.format("%-2s", entry.getKey()) + " : "
										+ entry.getValue());
					}
					// Find the next occurrence of the key
					index = regexString.indexOf(entry.getKey(), index + 1);
				}
			}
		}
		// Get all escaped characters
		for (int i = 0; i < regexString.length(); i++) {
			if (regexString.charAt(i) == '\\' && i + 1 < regexString.length()) {
				String escapedChar = regexString.substring(i, i + 2);
				if (escapeExplanations.containsKey(escapedChar)) {
					explanations.add(String.format("%-2s", escapedChar) + " : "
							+ escapeExplanations.get(escapedChar));
				} else {
					explanations.add(String.format("%-2s", escapedChar) + " : "
							+ "Escaped metacharacter " + escapedChar.charAt(1)
							+ " (literal '" + escapedChar.charAt(1) + "')");
				}
				i++;
			}
		}
		// Concatenate explanations into a single string, in the order that
		// they appear in the regex
		return explanations.stream()
				.sorted(Comparator.comparingInt(s -> {
					String re = s.split(" ")[0];
					// Find the first unescaped occurrence of the
					// regex element in the regex string
					int index = regexString.indexOf(re);
					while (index >= 0) {
						if (isNotEscaped(index, regexString)) {
							return index;
						}
						index = regexString.indexOf(re, index + 1);
					}
					return -1;
				}))
				.collect(Collectors.joining("\n ", " ", ""));
	}

	private static boolean isNotEscaped(int index, String regex) {
		return index <= 0 || regex.charAt(index - 1) != '\\';
	}
}
