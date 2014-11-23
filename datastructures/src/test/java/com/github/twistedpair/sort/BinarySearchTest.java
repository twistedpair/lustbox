package com.github.twistedpair.sort;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class BinarySearchTest {
	
	@Test
	public void testStringArrays_oddArray() {
		final String[] arr = new String[]{"Z","a","b","c","d","g","z"};
		int i=0;
		for(String entry : arr) {
			int expected = i;
			int actual = BinarySearch.search(arr, entry);			
			assertEquals(expected, actual);
			i++;
		}
	}

	@Test
	public void testStringArrays_evenArray() {
		final String[] arr = new String[]{"a","b","c","d","g","z"};
		
		int i=0;
		for(String entry : arr) {
			int expected = i;
			int actual = BinarySearch.search(arr, entry);			
			assertEquals(expected, actual);
			i++;
		}
	}
	
	@Test
	public void testStringArrays_emptyArray() {
		final String[] arr = new String[]{};
		int expected = -1;
		int actual = BinarySearch.search(arr, "b");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testStringArrays_missingValue() {
		final String[] arr = new String[]{"a","b","c","d","g","z"};
		int expected = -1;
		int actual = BinarySearch.search(arr, "K");
		
		assertEquals(expected, actual);
	}
}
