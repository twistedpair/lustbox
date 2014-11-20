package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class RedBlackTreeTest {

	@Test
	public void testTreeRandomInsertOrder() {

		// setup - double and triple recurring values, odd total
		final Integer first = -1000;
		final Integer last = 5004;
		final int uniqueEntries = 8;
		final Integer[] testArr = new Integer[] {last, 44, first, 2, 303, 3, 3, 3,	-10, 2, 201 };
		// final Integer[] expected = new Integer[] { first, -10, 2, 2, 3, 3, 3, 44, 201, 303, last
		// }; // test

		// test
		final RedBlackTree<Integer, String> tree = new RedBlackTree<>();

		for (final Integer n : testArr) {
			tree.add(n, n + "V");
		}

		// test
		assertEquals("Tree missing items", uniqueEntries, tree.size());
		assertEquals("Wrong first", first, tree.min());
		assertEquals("Wrong last", last, tree.max());

		// fetch all keys, should have correct values - reverse order
		for (int n = testArr.length - 1; n >= 0; n--) {
			final Integer key = testArr[n];
			final String expectedValue = key + "V";
			assertEquals("Wrong get value", expectedValue, tree.get(key));
			assertTrue("Wrong contains", tree.contains(key));
		}

		// test bad keys
		assertFalse("Wrong contains", tree.contains(Integer.MAX_VALUE));
		assertFalse("Wrong contains", tree.contains(Integer.MIN_VALUE));
		assertFalse("Wrong contains", tree.contains(4));

		assertEquals("Wrong get", last, tree.max());

		// test update value
		final Integer testKey = testArr[3];
		final String newValue = "moose";
		tree.add(testKey, newValue);
		assertEquals("Wrong update value", newValue, tree.get(testKey));
		assertEquals("Tree not updated correctly items", uniqueEntries,
				tree.size());

		// test floor
		final Integer expected1 = 303;
		final Integer expected2 = 201;
		assertEquals("Bad floor", expected1, tree.floor(303));
		assertEquals("Bad floor", expected2, tree.floor(302));
		assertEquals("Bad floor", null, tree.floor(-2000));

		// TODO iterator && order of contents

		// test remove
		final Integer removeMe = 303;
		tree.remove(removeMe);
		assertEquals("Wrong update value", uniqueEntries - 1, tree.size());
		assertFalse("Removed item remains", tree.contains(removeMe));
		assertEquals("Can't find value", "5004V", tree.get(last));
	}
}
