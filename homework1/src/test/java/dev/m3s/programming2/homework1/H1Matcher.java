package dev.m3s.programming2.homework1;

import static org.hamcrest.MatcherAssert.assertThat;

import dev.m3s.maeaettae.jreq.FailureMessage;
import dev.m3s.maeaettae.jreq.Range;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;

import java.util.regex.Pattern;

class H1Matcher<T> extends BaseMatcher<T> {

	private final org.hamcrest.Matcher<? super T> matcher;
	private final FailureMessage message;
	private final T expected;

	public H1Matcher(T expected, FailureMessage message) {
		this.matcher = Matchers.is(expected);
		this.message = message;
		this.expected = expected;
	}

	public static FailureMessage explain() {
		return FailureMessage.explain();
	}

	public static FailureMessage explain(String reason) {
		return FailureMessage.explain(reason);
	}

	@Override
	public boolean matches(Object o) {
		return matcher.matches(o);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(message.getExpectedMessage(expected));
	}

	@Override
	public void describeMismatch(Object item, Description description) {
		description.appendText(message.getMismatchMessage(item));
		description.appendText("\n(<== see Console output for more context)");
	}

	/**
	 * Asserts that the actual value is equal to the expected value.
	 * The failure message is formatted as follows:
	 * 
	 * <pre>
	 * java.lang.AssertionError: argument supplied to FailureMessage.reason(), or ""
	 * Expected: argument supplied to FailureMessage.expected(), or "is ⟨expected⟩"
	 *      but: argument of FailureMessage.butWas(), or "was ⟨actual⟩"
	 * </pre>
	 */
	public static <T> void assertEquals(T actual, T expected,
			FailureMessage message) {
		makeAssertion(actual, new H1Matcher<>(expected, message));
	}

	public static <T> void assertEquals(T actual, T expected) {
		assertEquals(actual, expected, explain());
	}

		public static void assertTrue(boolean actual, FailureMessage message) {
		assertEquals(actual, true, message);
	}

	public static void assertTrue(boolean actual, String message) {
		FailureMessage modifiedMessage = explain(message);
		assertTrue(actual, modifiedMessage);
	}

	public static void assertTrue(boolean actual) {
		assertTrue(actual, explain());
	}

	public static void assertMatches(Object actual, Pattern expected,
			String failureReason) {
		RegexMatcher.assertMatches(actual, expected, failureReason);
	}

	public static void assertMatches(Object actual, Pattern expected) {
		RegexMatcher.assertMatches(actual, expected);
	}

	public static void assertReturns(Object actual, Object expected,
			String methodName, FailureMessage message) {
		FailureMessage def = explain()
				.expected(methodName + " to return ⟨" + expected + "⟩")
				.but(methodName + " returned ⟨" + actual + "⟩"
		);
		makeAssertion(actual,
				new H1Matcher<>(expected, def.copyFrom(message))
		);
	}

	public static void assertReturns(Object actual, Object expected,
			String methodName) {
		assertReturns(actual, expected, methodName, explain());
	}

	public static <T extends Number> void assertWithin(T actual,
			Range<T> expected, String name, FailureMessage message) {
		FailureMessage def = explain()
				.expected(name + " within " + expected)
				.but("was ⟨" + actual + "⟩"
		);
		makeAssertion(expected.includes(actual),
				new H1Matcher<>(true, def.copyFrom(message))
		);
	}

	public static <T extends Number> void assertWithin(T actual,
			Range<T> expected, String name) {
		assertWithin(actual, expected, name, explain());
	}

	public static <T extends Number> void assertWithout(T actual,
			Range<T> expected, String name, FailureMessage message) {
		FailureMessage def = explain()
				.expected(name + " not within " + expected)
				.but("was ⟨" + actual + "⟩"
		);
		makeAssertion(expected.includes(actual),
				new H1Matcher<>(false, def.copyFrom(message))
		);
	}

	public static <T extends Number> void assertWithout(T actual,
			Range<T> expected, String name) {
		assertWithout(actual, expected, name, explain());
	}

	private static <T> void makeAssertion(T actual, H1Matcher<T> matcher) {
		assertThat(matcher.message.getFailureReason(), actual, matcher);
	}
}
