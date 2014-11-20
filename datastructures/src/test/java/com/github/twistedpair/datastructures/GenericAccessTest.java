package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Ways to determine internal type from a generic context
 * 
 * @author Joseph Lust
 */
public final class GenericAccessTest {

	@Test
	public <T> void isANumberIsAssignable() {
		final Integer value = 3;
		final GenericHolder<Integer> holder = new GenericHolder<>(value);

		assertTrue("Should be number", holder.isNumericIsAssignableFrom());
	}

	@Test
	public void isANumberIsInstance() {
		final Integer value = 3;
		final GenericHolder<Integer> holder = new GenericHolder<>(value);

		assertTrue("Should be number", holder.isNumericIsInstance());
	}

	@Test
	public void isANumberInstanceOf() {
		final Integer value = 3;
		final GenericHolder<Integer> holder = new GenericHolder<>(value);

		assertTrue("Should be number", holder.isNumericInstanceOf());
	}
}
