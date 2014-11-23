package com.github.twistedpair.puzzel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class MathExpressionResolverTest {

	private static final double PRECISION = 1e-10;

	private final ExpressionResolver resolver = new MathExpressionResolver();

	@Test
	public void testOperators() {
		// setup
		final String expression = "9+2-(5*4/5)";
		final double expected = 7d;
		// test
		final double actual = resolver.resolve(expression);
		assertEquals("Wrong evaluation", expected, actual, PRECISION);
	}

	@Test
	public void testOperatorsAndParens() {
		// setup
		final String expression = "10+(2-5)*(4/5)";
		final double expected = 7.6d;
		// test
		final double actual = resolver.resolve(expression);
		assertEquals("Wrong evaluation", expected, actual, PRECISION);
	}
}