package com.github.twistedpair.sort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

/**
 * 
 * @author Joseph Lust
 */
public final class LinkedListSortIntegerTest {

	final LinkedListSort<Integer> sorter = new LinkedListSort<>();

	@Test
	public void testSortRandom() {
		// setup - double and triple recurring values, odd total
		final Integer[] testArr = new Integer[] { -1000, 5004, 2, 303, 3, 3, 3,	-10, 44, 2, 201 };
		final Integer[] expected = new Integer[] { -1000, -10, 2, 2, 3, 3, 3, 44, 201, 303, 5004 }; // test
		final LinkedList<Integer> list = new LinkedList<>(Arrays.asList(testArr));

		// test
		sorter.sort(list);
		final Integer[] actual = list.toArray(new Integer[] {});

		assertArrayEquals("Sort error!", expected, actual);
	}

	@Test
	public void testSortPresorted() {
		// setup
		final Integer[] testArr = new Integer[] { -10, 2, 3, 44, 201, 303 };
		final Integer[] expected = new Integer[] { -10, 2, 3, 44, 201, 303 };
		final LinkedList<Integer> list = new LinkedList<>(Arrays.asList(testArr));

		// test
		sorter.sort(list);
		final Integer[] actual = list.toArray(new Integer[] {});

		assertArrayEquals("Sort error!", expected, actual);
	}

	@Test
	public void testSortReversePresorted() {
		// setup
		final Integer[] testArr = new Integer[] { 303, 201, 44, 3, 2, -10 };
		final Integer[] expected = new Integer[] { -10, 2, 3, 44, 201, 303 };
		final LinkedList<Integer> list = new LinkedList<>(Arrays.asList(testArr));

		// test
		sorter.sort(list);
		final Integer[] actual = list.toArray(new Integer[] {});

		assertArrayEquals("Sort error!", expected, actual);
	}

}
