package dev.m3s.programming2.homework2;



import java.util.regex.Pattern;

import dev.m3s.maeaettae.jreq.FailureMessage;
import dev.m3s.maeaettae.jreq.Range;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;

class H2Matcher<T> extends BaseMatcher<T> {

	private final org.hamcrest.Matcher<? super T> matcher;
	private final FailureMessage message;
	private final T expected;
	private boolean not;

	/**
	 * Creates a matcher that matches when the examined object is logically equal
	 * to <code>expected</code>, as determined by {@link Matchers#is(Object)},
	 * which invokes {@link Object#equals} on the examined object.
	 *
	 * @param expected the expected object/value
	 * @param message  failure message object, which contains messages for
	 *                 expected and actual values
	 *                 (see {@link FailureMessage#getExpectedMessage(Object)}
	 *                 and {@link FailureMessage#getMismatchMessage(Object)})
	 */
	public H2Matcher(T expected, FailureMessage message) {
		this.matcher = Matchers.is(expected);
		this.message = message;
		this.expected = expected;
	}

	/**
	 * Creates a matcher that matches when <code>matcher</code> matches the
	 * examined object.
	 *
	 * @param expected the expected object/value
	 * @param message  failure message object, which contains messages for
	 *                 expected and actual values
	 *                 (see {@link FailureMessage#getExpectedMessage(Object)}
	 *                 and {@link FailureMessage#getMismatchMessage(Object)})
	 * @param matcher  the matcher that must be satisfied by the examined object
	 *                 (see {@link org.hamcrest.Matchers})
	 */
	public H2Matcher(T expected, FailureMessage message,
					 org.hamcrest.Matcher<? super T> matcher) {
		this.matcher = matcher;
		this.message = message;
		this.expected = expected;
	}

	private static <T> H2Matcher<T> not(T expected, FailureMessage message) {
		H2Matcher<T> matcher = new H2Matcher<>(expected, message,
				Matchers.not(expected)
		);
		matcher.not = true;
		return matcher;
	}

	public static FailureMessage explain() {
		return FailureMessage.explain();
	}

	public static FailureMessage explain(String reason) {
		return FailureMessage.explain(reason);
	}

	public static <T> FailureMessage explain(String reason, T... args) {
		return FailureMessage.explain(String.format(reason, args));
	}

	@Override
	public boolean matches(Object o) {
		return matcher.matches(o);
	}

	@Override
	public void describeTo(Description description) {
		String msg = message.getExpectedMessage(expected);
		if (not) {
			String[] expectedParts = msg.split(" ");
			// if the message is "is ⟨expected⟩", replace it with "not ⟨expected⟩"
			// TODO: move this to FailureMessage
			if (expectedParts.length == 2 && expectedParts[0].equals("is")) {
				msg = "not " + expectedParts[1];
			} else {
				msg = "not: " + msg;
			}
		}
		description.appendText(msg);
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
		makeAssertion(actual, new H2Matcher<>(expected, message));
	}

	public static <T> void assertEquals(T actual, T expected) {
		assertEquals(actual, expected, explain());
	}

	public static <T> void assertNotEquals(T actual, T expected,
										   FailureMessage message) {
		makeAssertion(actual, not(expected, message));
	}

	public static <T> void assertNotEquals(T actual, T expected) {
		assertNotEquals(actual, expected, explain());
	}

	public static <T> void assertNull(T actual, FailureMessage message) {
		assertEquals(actual, null, message);
	}

	public static <T> void assertNull(T actual) {
		assertNull(actual, explain());
	}

	public static <T> void assertNotNull(T actual, FailureMessage message) {
		assertNotEquals(actual, null, message);
	}

	public static <T> void assertNotNull(T actual) {
		assertNotNull(actual, explain());
	}

	public static void assertTrue(boolean actual, FailureMessage message) {
		assertEquals(actual, true, message);
	}

	public static void assertTrue(boolean actual) {
		assertTrue(actual, explain());
	}

	public static void assertFalse(boolean actual, FailureMessage message) {
		assertEquals(actual, false, message);
	}

	public static void assertFalse(boolean actual) {
		assertFalse(actual, explain());
	}

	public static void assertMatches(Object actual, Pattern expected,
									 String failureReason) {
		System.out.println("Verifying that \"" + actual
				+ "\" matches the expected pattern\n\"" + expected + "\"");
		RegexMatcher.assertMatches(actual, expected, failureReason);
		System.out.println(" ✓");
	}

	public static void assertMatches(Object actual, Pattern expected) {
		System.out.println("Verifying that \"" + actual
				+ "\" matches the expected pattern\n\"" + expected + "\"");
		RegexMatcher.assertMatches(actual, expected);
		System.out.println(" ✓");
	}

	public static void assertReturns(Object actual, Object expected,
									 String methodName,
									 FailureMessage message) {
		FailureMessage def = explain().expected(
											  methodName + " to return ⟨" + expected + "⟩")
									  .but(methodName + " returned ⟨" + actual
											  + "⟩");
		makeAssertion(actual, new H2Matcher<>(expected, def.copyFrom(message)));
	}

	public static void assertReturns(Object actual, Object expected,
									 String methodName) {
		assertReturns(actual, expected, methodName, explain());
	}

	public static <T extends Number> void assertWithin(T actual,
													   Range<T> expected,
													   String name,
													   FailureMessage message) {
		FailureMessage def = explain().expected(name + " within " + expected)
									  .but("was ⟨" + actual + "⟩");
		makeAssertion(expected.includes(actual),
				new H2Matcher<>(true, def.copyFrom(message))
		);
	}

	public static <T extends Number> void assertWithin(T actual,
													   Range<T> expected,
													   String name) {
		assertWithin(actual, expected, name, explain());
	}

	public static <T extends Number> void assertWithout(T actual,
														Range<T> expected,
														String name,
														FailureMessage message) {
		FailureMessage def = explain().expected(
											  name + " not within " + expected)
									  .but("was ⟨" + actual + "⟩");
		makeAssertion(expected.includes(actual),
				new H2Matcher<>(false, def.copyFrom(message))
		);
	}

	public static <T extends Number> void assertWithout(T actual,
														Range<T> expected,
														String name) {
		assertWithout(actual, expected, name, explain());
	}

	private static <T> void makeAssertion(T actual, H2Matcher<T> matcher) {
		assertThat(matcher.message.getFailureReason(), actual, matcher);
		System.out.println(" ✓");
	}
}
