package com.github.twistedpair.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Various fun with prime numbers
 * 
 * @author Joseph Lust
 * @param <T>
 */
public final class Primer {

	final Random rand = new Random(); // seed for double the fun!

	/**
	 * Use Fermat if number is >= limit
	 */
	private static final long FERMAT_LIMIT = 1_000_000_000L;
	private static final long FERMAT_ITERATIONS = 100; // 1-1/2^k confidence
	private static final BigInteger ONE = BigInteger.ONE;

	/**
	 * Find the primes up to this value
	 * @param limit
	 * @return
	 */
	public final Long[] findPrimes(final long limit) {
		final List<Long> primes = new ArrayList<>(100);

		if (limit >= 2) {
			primes.add(2l);

			// try all numbers
			for (long p = 3; p < limit; p += 2) {
				if (isPrime(p)) {
					primes.add(p);
				}
			}
		}

		return primes.toArray(new Long[] {});
	}

	// TODO hack: BigInteger.isProbablePrime();

	public final boolean isPrime(final long p) {

		// quick sanity checks
		if (p < 2) { return false; } // bounds check
		if (p == 2) { return true; } // only even prime check
		if (p % 2 == 0) { return false; } // even check

		// big number? could take a while, branch logic
		return p < FERMAT_LIMIT ? isIterativePrime(p) : isFermatPrime(BigInteger.valueOf(p));
	}

	private boolean isIterativePrime(final long p) {
		// iterative approach - small numbers, try floats (rounding anyway)
		final long limit = (int) Math.ceil(Math.sqrt(p));
		for (long i = 3; i <= limit; i += 2) {
			if(p % i==0 ) {
				return false;
			}
		}
		return true; // no divisors found
	}

	/**
	 * Apply Fermat prime test.<br>
	 * Fermat witness or liar? 1/2^k chance we're wrong for k passes
	 * 
	 * @see http://en.wikipedia.org/wiki/Fermat_primality_test
	 * @param p
	 * @return
	 */
	private boolean isFermatPrime(final BigInteger p) {
		long i = 1;
		while( i < FERMAT_ITERATIONS) {
			// ugly high quality random, misses ~50% of time, no bounded random for BigInt
			BigInteger a;
			while (true) {
				a = new BigInteger(p.bitLength(), rand);
				if (ONE.compareTo(a) <= 0 && a.compareTo(p) < 0) {
					break;
				}
			}
			// test
			final BigInteger ferm = a.modPow(p.subtract(ONE), p); // (1,p)
			if (!ferm.equals(ONE)) {
				return false;
			}
			i++;
		}
		return true; // 1e-61 chance that we're wrong
	}
}