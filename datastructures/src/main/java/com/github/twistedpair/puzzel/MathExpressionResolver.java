package com.github.twistedpair.puzzel;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Very simple math expression evaluator.<br>
 * Handles:<br>
 * <ul>
 * <li>+,-,*,/ operations</li>
 * <li>doubles and ints i.e. 4, 0.4, .4, 10.1</li>
 * <li>() and nested (()())</li>
 * </ul>
 * <br>
 * TODO Needs: Order of operations support, informative error messages, unary operator support
 * 
 * @author Joseph Lust
 */
public class MathExpressionResolver implements ExpressionResolver {

	int pos; // ugly global. Too bad stack frames can only return single value in Java

	@Override
	public double resolve(final String expression) {
		pos = -1;
		return resolve(expression.toCharArray());
	}

	private double resolve(final char[] tokens) {
		pos++;

		int evalCount = 0;
		double result = 0;

		final Queue<Character> operators = new ArrayDeque<>();
		final Queue<Double> values = new ArrayDeque<>();

		while (pos < tokens.length) {
			// deeper
			switch (tokens[pos]) {
			case '(':
				values.offer(resolve(tokens));
				result = eval(result, values, operators, evalCount++);
				break;
			case ')':
				// case of unary string i.e. (3) -> 3
				return values.isEmpty() ? result : values.poll();
			case '+':
			case '-':
			case '/':
			case '*':
				operators.offer(tokens[pos]);
				break;
			default: // should be a number
				values.offer(parseNumber(tokens));

				if (!operators.isEmpty()) {
					result = eval(result, values, operators, evalCount++);
				}
			}
			pos++;
		}
		return result;
	}

	private String readNumberString(final char tokens[]) {

		// move reader to separate function so we know length
		final StringBuilder builder = new StringBuilder();

		boolean isNumber = true;
		while (pos < tokens.length && isNumber) {
			switch (tokens[pos]) {
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '0':
			case '.':
				builder.append(tokens[pos]);
				pos++;
				break;
			default: // not number
				isNumber = false;
				pos--; // backtrack
				break;
			}
		}

		return builder.toString();
	}

	private double parseNumber(final char[] tokens) {
		try {
			return Double.valueOf(readNumberString(tokens));
		}
		catch (final NumberFormatException e) {
			throw new IllegalAccessError("Cannot parse number!");
		}
	}

	private double eval(double result, final Queue<Double> values,
			final Queue<Character> operators, final int evalCount) {

		if (evalCount == 0) { // fill on first operation - simpler way?, could test Double for null, but I hate passing null
			result = values.poll();
		}

		while (!operators.isEmpty()) {
			final double value = values.poll();
			switch (operators.poll()) {
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