package com.github.twistedpair.math;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;


/**
 * Test those primes
 * 
 * @author Joseph Lust
 */
public final class PrimerTest {

	@Test
	public void testPrimerangeTwoOrLess() {

		assertEquals(0, Primer.findPrimes(0).length);
		assertEquals(0, Primer.findPrimes(1).length);
		assertEquals(1, Primer.findPrimes(2).length);
	}

	@Test
	public void testPrimerangeOverOneHundred() {
		final Long[] primes = Primer.findPrimes(100);
		assertEquals(25, primes.length);
		System.out.println(Arrays.toString(primes));
	}

	@Test
	public void testPrimerangeOneThousand() {
		final Long[] primes = Primer.findPrimes(1000);
		assertEquals(168, primes.length);
		System.out.println(Arrays.toString(primes));
	}

	@Test
	public void testPrimerangeTenThousand() {
		final Long[] primes = Primer.findPrimes(10000);
		assertEquals(168, primes.length);
		System.out.println(Arrays.toString(primes));
	}

	@Test
	public void testPrime() {

		assertEquals("bad result 0", false, Primer.isPrime(0));
		assertEquals("bad result 1", false, Primer.isPrime(1));
		assertEquals("bad result 2", true, Primer.isPrime(2));
		assertEquals("bad result 3", true, Primer.isPrime(3));
		assertEquals("bad result 4", false, Primer.isPrime(4));
		assertEquals("bad result 5", true, Primer.isPrime(5));
		assertEquals("bad result 6", false, Primer.isPrime(6));
		assertEquals("bad result 7", true, Primer.isPrime(7));
		assertEquals("bad result 8", false, Primer.isPrime(8));
		assertEquals("bad result 9", false, Primer.isPrime(9));
		assertEquals("bad result 17", true, Primer.isPrime(17));
		assertEquals("bad result 53", true, Primer.isPrime(5));

		int n = 1;
		while (n < 1000) { // 3000 tests
			assertEquals("bad result 1000002", false, Primer.isPrime(1000002));
			assertEquals("bad result 1000003", true, Primer.isPrime(1000003));
			assertEquals("bad result 1000004", false, Primer.isPrime(1000004));
			assertEquals("bad result 179426549", true,
					Primer.isPrime(179426549));
			n++;
		}
	}

}
