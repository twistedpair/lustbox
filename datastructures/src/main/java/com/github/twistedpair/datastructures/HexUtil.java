package com.github.twistedpair.datastructures;

/**
 * Answers the interview question:<br>
 * <code>Create a function that converts long to a Hex string and
 * vica versa. Don't use any builtin/lib string conversion or parsing libraries."</code>
 * 
 * @author Joseph Lust
 */
public final class HexUtil {

	private static final long TOTAL_HEX_VALUES = 16;
	private static final int LONG_CHARS = 16;
	private static final int WORD_BITS = 4;

	public static long hexToLong(final String in) throws Exception {
		assert in.length() <= LONG_CHARS : "Max of 16 characters";

		long value = 0;

		for (int depth = 0, idx = in.length() - 1; idx >= 0; idx--, depth++) {
			// MSB to LSB, left to right
			final long cVal = hexCharToLong(in.charAt(idx));
			value |= cVal << depth * WORD_BITS;
		}
		return value;
	}

	public static String longToHexString(final long in) throws Exception {

		// TODO minimize loops by analyzing input size
		int depth = LONG_CHARS - 1;
		final StringBuilder builder = new StringBuilder(LONG_CHARS);

		boolean hitFirstNonZero = false;
		while (depth >= 0) {
			// TODO make static lookup table of masks
			final long mask = TOTAL_HEX_VALUES - 1 << depth * WORD_BITS;
			final long value = (in & mask) >> depth * WORD_BITS;
			final char c = longToHexChar(value);

			hitFirstNonZero |= '0' != c; // faster than trimming
			if (hitFirstNonZero) {
				builder.append(c);
			}
			depth--;
		}
		return builder.toString();
	}

	private static char longToHexChar(long in) throws Exception {

		// wrap small negatives
		if (in < 0) {
			in += TOTAL_HEX_VALUES; // wrap sign (+1) for sign bit
		}

		if (in <= 9) {
			return (char) (in + 48); // '0'=48, narrow
		}
		else if (in <= 15) {
			return (char) (in + 65 - 10); // 'A'=65, narrow
		}
		else {
			// TODO use more specific exception
			throw new Exception("HEX values must be uppercase 0-15: " + in);
		}
	}

	// use long, otherwise int & long math => int overflow
	private static long hexCharToLong(final char c) throws Exception {

		// will compile to lookup table/jump map
		switch(c) {
		case 'A': // A:65 , a:97
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
			return c-65+10; // compiler will combine
		case '0': // 0:48
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return c-48;
		default :
			// TODO use more specific exception
			throw new Exception("HEX values must be uppercase 0-9 or A-F: " + c);
		}
	}
}