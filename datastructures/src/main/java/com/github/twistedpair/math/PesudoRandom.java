package com.github.twistedpair.math;

import java.util.concurrent.atomic.AtomicLong;

final class PseudoRandom {

	private final AtomicLong seed;
	private final int XOR_COUNT = 50;

	public PseudoRandom() {
		super();
		seed = new AtomicLong(System.nanoTime());
	}

	public int rand(final int min, final int max) {
		assert min<max : "Min must be less than max!";

		final long rand = rand();
		// scale to range
		final double normalized = (double) rand / Long.MAX_VALUE;
		final double delta = max-min;
		return (int) Math.round(normalized * delta + min);
	}

	/**
	 * Random number between 0 and max range for Long
	 * 
	 * @return
	 */
	public long rand() {

		long value = seed.get();

		// XOR shift RNG - proven well distributed better than a linear congruential generator
		// See: http://www.javamex.com/tutorials/random_numbers/xorshift.shtml
		// Assured to cover entire 64 bit range of long
		value ^= value << 21;
		value ^= value >>> 35;
		value ^= value << 4;

		value *= Long.signum(value);// make positive

		seed.set(value);

		return value;
	}
}