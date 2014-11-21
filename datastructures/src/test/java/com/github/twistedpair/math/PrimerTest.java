package com.github.twistedpair.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Test those primes
 * 
 * @author Joseph Lust
 */
public final class PrimerTest {

	final Primer primer = new Primer();

	@Test
	public void testPrimerangeTwoOrLess() {
		assertEquals(0, primer.findPrimes(0).length);
		assertEquals(0, primer.findPrimes(1).length);
		assertEquals(1, primer.findPrimes(2).length);
	}

	@Test
	public void testPrimerangeOneHundred() {
		final Long[] primes = primer.findPrimes(100);
		assertEquals(25, primes.length);
	}

	@Test
	public void testPrimerangeOneThousand() {
		final Long[] primes = primer.findPrimes(1000);
		assertEquals(168, primes.length);
	}

	@Test
	public void testPrimerangeTenThousand() {
		final Long[] primes = primer.findPrimes(10000);
		assertEquals(1229, primes.length);
	}

	@Test
	public void testPrime() {

		assertEquals("bad result 0", false, primer.isPrime(0));
		assertEquals("bad result 1", false, primer.isPrime(1));
		assertEquals("bad result 2", true, primer.isPrime(2));
		assertEquals("bad result 3", true, primer.isPrime(3));
		assertEquals("bad result 4", false, primer.isPrime(4));
		assertEquals("bad result 5", true, primer.isPrime(5));
		assertEquals("bad result 6", false, primer.isPrime(6));
		assertEquals("bad result 7", true, primer.isPrime(7));
		assertEquals("bad result 8", false, primer.isPrime(8));
		assertEquals("bad result 9", false, primer.isPrime(9));
		assertEquals("bad result 17", true, primer.isPrime(17));
		assertEquals("bad result 53", true, primer.isPrime(5));

		int n = 1;
		while (n < 1000) { // 3000 tests
			assertEquals("bad result 1000002", false, primer.isPrime(1000002L));
			assertEquals("bad result 1000003", true, primer.isPrime(1000003L));
			assertEquals("bad result 1000004", false, primer.isPrime(1000004L));
			assertEquals("bad result 32416190071L", true, primer.isPrime(32416190071L));
			assertEquals("bad result 179426549", true, primer.isPrime(179426549L));
			assertEquals("bad result 9223372036854775807L", false, primer.isPrime(9223372036854775807L));
			n++;
		}
	}

}
