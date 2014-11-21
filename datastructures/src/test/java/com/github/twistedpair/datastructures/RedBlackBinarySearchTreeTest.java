package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public final class RedBlackBinarySearchTreeTest {

	private final Integer first = -1000;
	private final Integer last = 5004;
	private final int uniqueEntries = 8;
	private final Integer[] testArr = new Integer[] { last, 44, first, 2, 303,
			3, 3, 3, -10, 2, 201 };
	private final BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();

	@Before
	public void setup() {
		// setup - double and triple recurring values, odd total
		// fill
		for (final Integer n : testArr) {
			tree.add(n, n + "V");
		}
	}

	// TODO iterator && order of contents

	@Test
	public void testTreeRandomInsertOrder() {
		// Is essentially testing the setup function
		assertEquals("Tree missing items", uniqueEntries, tree.size());
		assertEquals("Wrong first", first, tree.min());
		assertEquals("Wrong last", last, tree.max());
	}

	@Test
	public void fetchAllKeys() {
		// fetch all keys, should have correct values - reverse order
		for (int n = testArr.length - 1; n >= 0; n--) {
			final Integer key = testArr[n];
			final String expectedValue = key + "V";
			assertEquals("Wrong get value", expectedValue, tree.get(key));
			assertTrue("Wrong contains", tree.contains(key));
		}
	}

	@Test
	public void testMax() {
		assertEquals("Wrong get", last, tree.max());
	}

	@Test
	public void testNonExistantKeys() {
		// test bad keys
		assertFalse("Wrong contains", tree.contains(Integer.MAX_VALUE));
		assertFalse("Wrong contains", tree.contains(Integer.MIN_VALUE));
		assertFalse("Wrong contains", tree.contains(4));
	}

	@Test
	public void testUpdateKey() {
		// test update value
		final Integer testKey = testArr[3];
		final String newValue = "moose";
		tree.add(testKey, newValue);
		assertEquals("Wrong update value", newValue, tree.get(testKey));
		assertEquals("Tree not updated correctly items", uniqueEntries,
				tree.size());
	}

	@Test
	public void testFloor() {
		// test floor
		final Integer expected1 = 303;
		final Integer expected2 = 201;
		assertEquals("Bad floor", expected1, tree.floor(303));
		assertEquals("Bad floor", expected2, tree.floor(302));
		assertEquals("Bad floor", null, tree.floor(-2000));
	}

	@Test
	public void testRemoveNodeWithBothChildren() {
		// test remove
		final Integer removeMe = 44;
		tree.remove(removeMe);
		assertEquals("Wrong update value", uniqueEntries - 1, tree.size());
		assertFalse("Removed item remains", tree.contains(removeMe));
		assertEquals("Can't find value", "5004V", tree.get(last));
		assertEquals("Can't find value", "303V", tree.get(303)); // check children
		assertEquals("Can't find value", "201V", tree.get(201));
	}

	@Test
	public void testRemoveNodeWithSingleChildLeft() {
		// test remove
		final Integer removeMe = 303;
		tree.remove(removeMe);
		assertEquals("Wrong update value", uniqueEntries - 1, tree.size());
		assertFalse("Removed item remains", tree.contains(removeMe));
		assertEquals("Can't find value", "5004V", tree.get(last));
		assertEquals("Can't find value", "44V", tree.get(44)); // check parent
		assertEquals("Can't find value", "201V", tree.get(201)); // child child
	}

	// TODO add single Right case

	@Test
	public void testRemoveNodeWithNoChildren() {
		// test remove
		final Integer removeMe = 201;
		tree.remove(removeMe);
		assertEquals("Wrong update value", uniqueEntries - 1, tree.size());
		assertFalse("Removed item remains", tree.contains(removeMe));
		assertEquals("Can't find value", "5004V", tree.get(last));
		assertEquals("Can't find value", "44V", tree.get(44)); // check grandparent
		assertEquals("Can't find value", "303V", tree.get(303)); // child parent
	}

	@Test
	public void testRemoveNodeWithNonExistantKey() {
		// test remove
		final Integer removeMe = 33033;
		assertFalse("Removed item remains", tree.contains(33033)); // shouldn't

		tree.remove(removeMe);
		assertEquals("Wrong update value", uniqueEntries, tree.size());
	}

	@Test
	public void testRemoveNodeRootKey() {
		// test remove
		final Integer removeMe = last;
		tree.remove(removeMe);
		assertEquals("Wrong update value", uniqueEntries - 1, tree.size());
		assertEquals("Can't find value", "303V", tree.get(303)); // child parent
	}

	@Test
	public void testRemoveNodeEmptyTree() {
		// test remove
		final BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
		final Integer removeMe = last;
		tree.remove(removeMe);
		assertEquals("Should be empty", 0, tree.size());
		assertTrue("Should be empty", tree.isEmpty());
		assertFalse("Should be empty", tree.contains(removeMe));
	}
}
