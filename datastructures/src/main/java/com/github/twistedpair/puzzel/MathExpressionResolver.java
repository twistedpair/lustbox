package com.github.twistedpair.puzzel;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class MathExpressionResolver implements ExpressionResolver {

	// Recap							 					 YES		 NO
	// Associative 	(a+b)+c = a+(b+c) 	parens insensative	(+,*)		(-,/,cross,^)
	// Commutative 	a x b = b x a		order insensative 	(+,*)		(-,/,%,^)
	// Distributive a x (b+c) = ab+bc						(+,*)		(/,%)
	@Override
	public double resolve(final String expression) {

		final Queue<Double> values = new ArrayDeque<>();
		final Queue<Character> operators = new ArrayDeque<>();
		final Stack<Character> parens = new Stack<>();

		double result = 0;
		int evalCount = 0;
		boolean wasLastOperator = false;

		// TODO multi char numbers
		final String lastNumber = "";

		for(final char c : expression.toCharArray()) {

			switch(c) {
			case ')':
				parens.pop();
				result = eval(result, values, operators, evalCount++);
				wasLastOperator = false;
				break;
			case '(':
				parens.push(c);
				wasLastOperator = false;
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				operators.offer(c);
				wasLastOperator = true;
				break;
			default: // presume number
				//lastNumber += c;
				try {
					values.offer(Double.valueOf(c + "")); // Or will be ASCII char code stored!
				}
				catch(final NumberFormatException e) {
					throw new IllegalArgumentException(
							String.format("Invalid input [%s]. Must be +-*/() or 0-9(.([0.9]+)?)?",c));
				}

				if (wasLastOperator) {
					result = eval(result, values, operators, evalCount++);
				}
				wasLastOperator = false;
			}
		}

		if (!operators.isEmpty() || !values.isEmpty()) { // final pass
			result = eval(result, values, operators, evalCount++);
		}

		if (!parens.isEmpty() || !operators.isEmpty() || !values.isEmpty()) { // done?
			throw new IllegalArgumentException(String.format("Unbalanced expression [%s]",expression));
		}

		return result;
	}

	private double eval(double result, final Queue<Double> values, final Queue<Character> operators, final int evalCount) {

		if(evalCount==0) { // fill on first operation - arr, I hate null
			result = values.poll();
		}
		if (values.size() != operators.size()) { // balance
			throw new IllegalArgumentException("Unbalanced operators");
		}

		while (!values.isEmpty()) {
			final double value = values.poll();
			switch(operators.poll()) {
			case '+':
				result += value;
				break;
			case '-':
				result -= value;
				break;
			case '*':
				result *= value;
				break;
			case '/':
				result /= value;
				break;
			}
		}
		return result;
	}

}