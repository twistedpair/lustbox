package com.github.twistedpair.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Various fun with prime numbers
 * 
 * @author Joseph Lust
 * @param <T>
 */
public final class Primer {

	/**
	 * Use Fermat if number is >= limit
	 */
	private static final long FERMAT_LIMIT = 100;
	private static final long FERMAT_ITERATIONS = 200; // 1-1/2^k confidence

	/**
	 * Find the primes up to this value
	 * @param limit
	 * @return
	 */
	public static final Long[] findPrimes(final long limit) {
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

	public static final boolean isPrime(final long p) {

		// quick sanity checks
		if(p < 2) { return false;}        // bounds check
		if(p == 2) { return true;}        // only even prime check
		if(p % 2 ==0 && p!=2) { return false;}   // even check

		// big number? could take a while, branch logic
		return p < FERMAT_LIMIT ? isIterativePrime(p) : isFermatPrime(p);
	}

	private static boolean isIterativePrime(final long p) {
		// iterative approach
		final long limit = (int) Math.ceil(Math.sqrt(p));
		for (long i = 3; i <= limit; i += 2) {
			if(p % i==0 ) {
				return false;
			}
		}
		return true; // no divisors found
	}

	private static boolean isFermatPrime(final long p) {

		long i = 1;
		while( i < FERMAT_ITERATIONS) {
			// Fermat witness or liar? 1/2^k chance we're wrong for k passes
			// need a = [1,p)
			// http://en.wikipedia.org/wiki/Fermat_primality_test
			/*
			long a;
			while(true) {
				a = (long) Math.floor(Math.random()*(p-1))+1; // [0,1) * p-1 => [0,p-1) +1 => [1,p) for p>1
				if (a > 1) {
					break;
				}
			}
			 */
			final long a = (long) Math.floor(Math.random()*(p-3))+2; // [0,1) * p-1 => [0,p-1) +1 => [1,p) for p>1
			final boolean notPrime = Math.pow(a, p - 1) % p == 1; // arr, comparing ints & floating point!
			if(notPrime) {
				return false;
			}
			i++;
		}
		return true; // 1e-61 chance that we're wrong
	}
}