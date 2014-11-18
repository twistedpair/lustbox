package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HexUtilTest {

	/*
	 * final String target = "2F";
	 * final BigInteger value = new BigInteger(target, 16); // final
	 * Long value = Long.decode("DEADBEEFB19B00B5");
	 * System.out.println(value);
	 * System.out.println(Long.toHexString(Long.MAX_VALUE - 3));
	 * System.out.println(Long.MAX_VALUE);
	 */

	// TODO make a test automatic recursion counting up by primes to max/min

	@Test
	public void shouldHandleMaxLong() throws Exception {
		doHexCharTest("7FFFFFFFFFFFFFFF", Long.MAX_VALUE);
	}

	@Test
	public void shouldHandleUnderMaxLong() throws Exception {
		doHexCharTest("7FFFFFFFFFFFFFFC", Long.MAX_VALUE - 3);
	}

	@Test
	public void shouldHandleUnderMinLong() throws Exception {
		doHexCharTest("8000000000000003", Long.MIN_VALUE + 3);
	}

	@Test
	public void shouldHandleMinLong() throws Exception {
		doHexCharTest("8000000000000000", Long.MIN_VALUE);
	}

	@Test
	public void shouldHandle2Chars() throws Exception {
		doHexCharTest("2F", 47);
	}

	@Test
	public void shouldHandleOutputting2Chars() throws Exception {
		doLongTest(47L, "2F");
	}

	@Test
	public void shouldHandleOutputtingNegative2Chars() throws Exception {
		// System.out.println(Long.toHexString(-47));
		doLongTest(-47L, "FFFFFFFFFFFFFFD1");
	}

	@Test
	public void shouldHandleOutputtingMinLong() throws Exception {
		doLongTest(Long.MIN_VALUE, "8000000000000000");
	}

	@Test
	public void shouldHandleOutputtingMaxLong() throws Exception {
		doLongTest(Long.MAX_VALUE, "7FFFFFFFFFFFFFFF");
	}

	@Test
	public void shouldHandleOutputting8Chars() throws Exception {
		doLongTest(2979725493L, "B19B00B5");
	}

	@Test
	public void shouldHandle3Chars() throws Exception {
		doHexCharTest("FB5", 4021L);
	}

	@Test
	public void shouldHandle8Chars() throws Exception {
		doHexCharTest("B19B00B5", 2979725493L);
	}

	@Test
	public void shouldHandle6Chars() throws Exception {
		doHexCharTest("FBED09", 16510217L);
	}

	@Test
	public void shouldHandle7Chars() throws Exception {
		doHexCharTest("FFFFFF", 16777215);
	}

	public void doHexCharTest(final String test, final long expected)
			throws Exception {
		// test
		final long actual = HexUtil.hexToLong(test);
		// verify
		assertEquals(expected, actual);
	}

	public void doLongTest(final long test, final String expected)
			throws Exception {
		// test
		final String actual = HexUtil.longToHexString(test);
		// verify
		assertEquals(expected, actual);
	}

}
