package com.github.twistedpair.math;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;


/**
 * Test those fibs!
 * 
 * @author Joseph Lust
 */
public final class FibonacciTest {
	
	private static final long[] fibs = new long[]{1,1,2,3,5,8,13,21,34,55,89,144,233,377};

	// Blows up FAST - SO
	@Test
	public void testFibRecursive() {
		int n=1;
		for(long expectd : fibs) {
			assertEquals("Failed at "+n, expectd, Fibonacci.recursive(n));
			n++;
		}
		assertEquals("Failed at "+n, 2178309, Fibonacci.recursive(32));
	}
	
	// Works pretty well, but POW still is costly and loses precision above ~n=70
	@Test
	public void testFibConstant() {
		int n=1;
		for(long expectd : fibs) {
			assertEquals("Failed at "+n, expectd, Fibonacci.constant(n).longValue());
			n++;
		}
		// jumbo!
		final BigInteger expected = new BigInteger("190392490709135");
		assertEquals("Failed at "+n, expected, Fibonacci.constant(70));
	}
	
	// Like a champ! Changed to BigInt for the large values
	@Test
	public void testFibIterative() {
		int n=1;
		for(long expectd : fibs) {
			assertEquals("Failed at "+n, expectd, Fibonacci.iterative(n).intValue());
			n++;
		}
		assertEquals("Failed at "+n, 12586269025L, Fibonacci.iterative(50).longValue());
		// jumbo!
		final BigInteger expected = new BigInteger("354224848179261915075");
		assertEquals("Failed at "+n, expected, Fibonacci.iterative(100));
	}
}
