package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashMapTest {

	@Test
	public void testList() {
		final HashMap<String, String> map = new HashMap<>();

		final String key1 = "track";
		final String key2 = "cat";
		final String key3 = "team";

		final String val1 = "reggie";
		final String val2 = "howard";
		final String val3 = "burins";

		map.put(key1, val1);
		map.put(key2, val2);
		map.put(key3, val3);

		assertEquals(3, map.size());

		final String actual3 = map.get(key3);
		assertEquals(val3, actual3);

		/*
		 * map.remove(key1); map.remove(key3); map.remove(key2);
		 * 
		 * assertEquals(0, map.size()); assertTrue(map.isEmpty());
		 */
	}

	@Test
	public void testBigMap() {
		final HashMap<String, String> map = new HashMap<>();

		final String keys = "abcdefghijklmnopqrstuvwxyzABCDEFGHIKJLMNOPQRSTUVWXYZ";

		for (int n = 0; n < keys.length(); n++) {
			final String s = keys.substring(n, n + 1);
			map.put(s, n + "-");
		}

		assertEquals(52, map.size());

		final String key = map.get("Z");
		final String val = "51-";
		assertEquals(val, key);

	}
}
