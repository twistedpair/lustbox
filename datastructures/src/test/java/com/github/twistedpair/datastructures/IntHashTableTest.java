package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntHashTableTest {

	@Test
	public void testHashTable() {

		final int tableSize = 4093; // large prime number
		final IntHashTable iht = new IntHashTable(tableSize);

		final Integer key1 = 3,              value1 = 12;
		final Integer key2 = 12,             value2 = 143;
		final Integer key3 = tableSize+12,   value3 = 99;
		final Integer key4 = 12,             value4 = 1;
		final Integer key5 = tableSize+12,   value5 = 2;
		final Integer key6 = 2*tableSize+12, value6 = 21;

		iht.put(key1,value1);
		iht.put(key2,value2);

		assertTrue("Missing key", iht.hasKey(key1));
		assertTrue("Missing key", iht.hasKey(key2));

		assertEquals("Wrong number enqueued", value1, iht.get(key1));
		assertEquals("Wrong number enqueued", value2, iht.get(key2));

		assertEquals("No collisions expected", 0, iht.getNumCollisions());

		iht.put(key3,value3);
		iht.put(key4,value4);

		assertEquals("Wrong entry", value3, iht.get(key3));
		assertEquals("Wrong entry", value4, iht.get(key4));

		assertEquals("Collisions expected", 1, iht.getNumCollisions());

		iht.remove(key3);

		assertFalse("No key expected", iht.hasKey(key3));
		assertEquals("Wrong entry", value4, iht.get(key4));
		assertEquals("No collisions expected", 0, iht.getNumCollisions());

		iht.put(key5,value5);
		iht.put(key6,value6);

		assertEquals("Collisions expected", 2, iht.getNumCollisions());

		assertEquals("Wrong entry", value4, iht.get(key4));
		assertEquals("Wrong entry", value5, iht.get(key5));
		assertEquals("Wrong entry", value6, iht.get(key6));

		iht.remove(key5);
		assertEquals("Wrong entry", value4, iht.get(key4));
		assertFalse("Key not expected", iht.hasKey(key5));

		assertEquals("Wrong entry", value6, iht.get(key6));
		assertEquals("Collisions expected", 1, iht.getNumCollisions());

		iht.printTable();
	}
}
