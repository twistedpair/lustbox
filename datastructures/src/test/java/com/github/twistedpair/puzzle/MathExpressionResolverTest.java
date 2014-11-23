package com.github.twistedpair.puzzle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.twistedpair.puzzle.ExpressionResolver;
import com.github.twistedpair.puzzle.MathExpressionResolver;

public final class MathExpressionResolverTest {

	private static final double PRECISION = 1e-10;

	private final ExpressionResolver resolver = new MathExpressionResolver();

	@Test
	public void testOperators() {
		// setup
		final String expression = "9+2-(5*4/5)+3";
		final double expected = 10d;
		// test
		final double actual = resolver.resolve(expression);
		assertEquals("Wrong evaluation", expected, actual, PRECISION);
	}

	@Test
	public void testOperatorsAndParens() {
		// setup
		final String expression = ".10*((2-5)+(4/5)+(2))";
		final double expected = -0.02;
		// test
		final double actual = resolver.resolve(expression);
		assertEquals("Wrong evaluation", expected, actual, PRECISION);
	}
}