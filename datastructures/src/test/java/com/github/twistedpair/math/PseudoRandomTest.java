package com.github.twistedpair.math;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Note: tests are RANDOM - makes them non-deterministic.<br>
 * Set a seed for deterministic behavior
 * 
 * @author Joseph Lust
 */
public class PseudoRandomTest {

	@Test
	public void testSimpleRandomLong() {

		final PseudoRandom random = new PseudoRandom();

		final long r1 = random.rand();
		final long r2 = random.rand();
		final long r3 = random.rand();

		assertNotSame("Values (probably) not random", r1, r2);
		assertNotSame("Values (probably) not random", r2, r3);
	}

	@Test
	public void testSimpleRandomInt() {

		final PseudoRandom random = new PseudoRandom();

		final int r1 = random.rand(0, 1);
		final int r2 = random.rand(10, 12);
		final int r3 = random.rand(-1, 5);

		assertTrue("Values out of range", r1 >= 0 && r1 <= 1);
		assertTrue("Values out of range", r2 >= 10 && r2 <= 12);
		assertTrue("Values out of range", r3 >= -1 && r3 <= 5);
	}
}
