package com.github.twistedpair.sort;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public abstract class AbstractSortIntegerTest<T extends Sorter<Integer>> {

	abstract T getSorter();

	@Test
	public void testSortRandom() {
		// setup - double and triple recurring values, odd total
		final Integer[] testArr = new Integer[] { -1000, 5004, 2, 303, 3, 3, 3, -10, 44, 2, 201 };
		final Integer[] expected = new Integer[] { -1000, -10, 2, 2, 3, 3, 3, 44, 201, 303, 5004 };		// test

		getSorter().sort(testArr);

		assertArrayEquals("Sort error!", expected, testArr);
	}

	@Test
	public void testSortPresorted() {
		// setup
		final Integer[] testArr = new Integer[] { -10, 2, 3, 44, 201, 303 };
		final Integer[] expected = new Integer[] { -10, 2, 3, 44, 201, 303 };
		// test
		getSorter().sort(testArr);

		assertArrayEquals("Sort error!", expected, testArr);
	}

	@Test
	public void testSortReversePresorted() {
		// setup
		final Integer[] testArr = new Integer[] { 303, 201, 44, 3, 2, -10 };
		final Integer[] expected = new Integer[] { -10, 2, 3, 44, 201, 303 };
		// test
		getSorter().sort(testArr);

		assertArrayEquals("Sort error!", expected, testArr);
	}
}
