package com.github.twistedpair.datastructures;


/**
 * Find twiddles between two strings
 * 
 * @author Joseph Lust
 */
final class TwiddleDetector {

	/*
	 * TODO can use trees for faster lookup.
	 * TODO can use streams and buffers for more realistic situations
	 * TODO can compile a list of twiddle strings and return all unique twiddles
	 */

	/**
	 * Detect twiddles
	 * 
	 * @param s1
	 * @param s2
	 * @return twiddled substring or NULL for no twiddle
	 */
	// WARNING: is not a binary algorithm, so overflow is possible for long strings
	// TODO can leap ahead and read back until twiddle is found from initialing node
	// in a binary fashion to to get closer to log n, but that wouldn't really be recursive
	public static final String findTwiddleRecursive(final String s1, final String s2) {

		// TODO null and range checks (i.e. if length=1, cannot be twiddled!)
		final char[] a = s1.toCharArray();
		final char[] b = s2.toCharArray();

		// switch to tree structures for longer strings to get n^2 to n ln(n)
		for (int idxA = 1; idxA < a.length; idxA++) { // read right
			for (int idxB = b.length - 1; idxB > 0; idxB--) { // read left

				final String twiddle = twiddleSlide(a, b, idxA, idxB, "");

				if(twiddle.length()>1) {
					return twiddle;
				}
			}
		}

		return null;
	}
	/**
	 * Detect twiddles
	 * 
	 * @param s1
	 * @param s2
	 * @return twiddled substring or NULL for no twiddle
	 */
	public static final String findTwiddle(final String s1, final String s2) {

		// TODO null and range checks (i.e. if length=1, cannot be twiddled!)
		final char[] a = s1.toCharArray();
		final char[] b = s2.toCharArray();

		// switch to tree structures for longer strings to get n^2 to n ln(n)
		for (int idxA = 1; idxA < a.length; idxA++) { // read right
			for (int idxB = b.length - 1; idxB > 0; idxB--) { // read left

				if(a[idxA]==b[idxB]) {

					int idxC = idxA;
					int idxD = idxB;

					while (inBounds(a, idxC) && inBounds(b, idxD)
							&& a[idxC] == b[idxD]) {
						idxC++;
						idxD--;
					}

					if (idxC - idxA < 2) { // not a twiddle
						continue;
					}

					// at end or end of twiddle
					return s1.substring(idxA, idxC);
				}
			}
		}

		return null;
	}

	private static String twiddleSlide(final char[] a, final char[] b,
			final int idxA, final int idxB, final String twiddle) {

		if (inBounds(a, idxA) && inBounds(b, idxB) && a[idxA] == b[idxB]) {
			return twiddleSlide(a, b, idxA+1, idxB-1, twiddle + a[idxA]);
		}
		else {
			return twiddle;
		}
	}

	private static boolean inBounds(final char[] a, final int idxA) {
		return idxA < a.length && idxA >= 0;
	}

}