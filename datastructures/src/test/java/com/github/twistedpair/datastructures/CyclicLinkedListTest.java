package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CyclicLinkedListTest {

	@Test
	public void testEmptyList() {
		// setup
		final CyclicLinkedList<String> list = new CyclicLinkedList<>();

		// test
		assertFalse("Expected acyclic", list.isCyclic());
	}

	@Test
	public void testMediumNoCycle() {
		// setup
		final int cycleSize = 0;// none
		final int listSize = 557;
		final CyclicLinkedList<String> list = new CyclicLinkedList<>();

		// test
		list.addCycle(listSize, cycleSize);

		assertFalse("Expected acyclic", list.isCyclic());
	}

	@Test
	public void testSubListLoop() {
		// setup
		final int cycleSize = 37;
		final int listSize = 531;
		final CyclicLinkedList<String> list = new CyclicLinkedList<>();

		// test
		list.addCycle(listSize, cycleSize);

		assertTrue("Expected cyclic", list.isCyclic());
	}

	@Test
	public void testFullListLoop() {
		// setup
		final int cycleSize = 2;
		final int listSize = 2;
		final CyclicLinkedList<String> list = new CyclicLinkedList<>();

		// test
		list.addCycle(listSize, cycleSize);

		assertTrue("Expected cyclic", list.isCyclic());
	}

	@Test
	public void testFullListLoopLarge() {
		// setup
		final int cycleSize = 20000;
		final int listSize = 20000;
		final CyclicLinkedList<String> list = new CyclicLinkedList<>();

		// test
		list.addCycle(listSize, cycleSize);

		assertTrue("Expected cyclic", list.isCyclic());
	}
}
