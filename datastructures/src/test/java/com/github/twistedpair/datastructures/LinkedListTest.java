package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void testList() {
		final LinkedList<String> list = new LinkedList<>();

		final String key1 = "a";
		final String key2 = "b";
		final String key3 = "c";

		list.add(key1);
		list.add(key2);
		list.add(key3);

		assertEquals(3, list.size());

		list.remove(key1);
		list.remove(key3);
		list.remove(key2);

		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}
}
