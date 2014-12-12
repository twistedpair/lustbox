package com.github.twistedpair.puzzle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EightQueensTest {

	final EightQueens queens = new EightQueens(); // sure, could all be static

	@Test
	public void testMe() {

		final int actual = queens.solutions();
		assertEquals(8, actual);
	}

}