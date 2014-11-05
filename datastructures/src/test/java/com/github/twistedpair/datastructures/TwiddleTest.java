package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TwiddleTest {

	@Test
	public void testGoodTwiddle() {

		final String s1 = "wombatMother";
		final String s2 = "tabbyCat";
		final String expected = "bat";

		final String actual = TwiddleDetector.findTwiddle(s1, s2);

		assertEquals("Wrong twiddle", expected, actual);
	}

	@Test
	public void testGoodLongTwiddle() {

		final String s1 = "yuroojjyudooodOOmdsfdskf";
		final String s2 = "yuryjjooyuddOOmdsfdskf";
		final String expected = "oojjy";

		final String actual = TwiddleDetector.findTwiddle(s1, s2);

		assertEquals("Wrong twiddle", expected, actual);
	}

	@Test
	public void testGoodTwiddle_recursive() {

		final String s1 = "wombatMother";
		final String s2 = "tabbyCat";
		final String expected = "bat";

		final String actual = TwiddleDetector.findTwiddleRecursive(s1, s2);

		assertEquals("Wrong twiddle", expected, actual);
	}

	@Test
	public void testGoodLongTwiddle_recursive() {

		final String s1 = "yuroojjyudooodOOmdsfdskf";
		final String s2 = "yuryjjooyuddOOmdsfdskf";
		final String expected = "oojjy";

		final String actual = TwiddleDetector.findTwiddleRecursive(s1, s2);

		assertEquals("Wrong twiddle", expected, actual);
	}
}
