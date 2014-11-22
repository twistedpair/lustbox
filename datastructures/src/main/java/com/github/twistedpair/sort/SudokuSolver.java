package com.github.twistedpair.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;

/**
 * @author a466857
 */
public class SudokuSolver {

	private static final int N = 9;
	private static int S = N / 3;

	public int[][] solve(final int[][] puzzel) {

		final Stack<Integer>[] rows = new Stack[N]; // generics and arrays can't be friends
		final Stack<Integer>[] cols = new Stack[N];
		final Stack<Integer>[] sqrs = new Stack[N];
		fillStacks(puzzel, rows, cols, sqrs);

		// TODO, start at most defined row
		// TODO make preset random jump table, reset table/change start row after threshold moves
		return solve(puzzel, 0, 0, rows, cols, sqrs);
	}

	private void fillStacks(final int[][] puzzel, final Stack<Integer>[] rows,
			final Stack<Integer>[] cols, final Stack<Integer>[] sqrs) {

		final Stack<Integer> opts = new Stack<>();
		opts.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

		// fill stacks with remaining options
		// rows
		int r = 0;
		for (final int[] row : puzzel) {
			final Stack<Integer> available = new Stack<>();
			available.addAll(opts);

			for (final int cVal : row) {
				if (cVal > 0) {
					available.removeElement(cVal);
				}
			}

			rows[r] = available;
			r++;
		}
		// cols
		for (int c = 0; c < N; c++) {
			final Stack<Integer> available = new Stack<>();
			available.addAll(opts);

			for (r = 0; r < N; r++) {
				if (puzzel[r][c] > 0) {
					available.removeElement(puzzel[r][c]);
				}
			}

			cols[c] = available;
		}
		// squares
		int s = 0;
		for (int sr = 0; sr < S; sr++) {
			for (int sc = 0; sc < S; sc++) {
				// each square - could just unwind with direct indexed access
				final Stack<Integer> available = new Stack<>();
				available.addAll(opts);

				// SxS kernel
				for (r = S * sr; r < S * (sr + 1); r++) {
					for (int c = S * sc; c < S * (sc + 1); c++) {
						if (puzzel[r][c] > 0) {
							available.removeElement(puzzel[r][c]);
						}
					}
				}

				sqrs[s] = available;
				s++;
			}
		}
	}

	private int[][] solve(final int[][] puzzel, final int r, final int c,
			final Stack<Integer>[] rows, final Stack<Integer>[] cols,
			final Stack<Integer>[] sqrs) {

		// continue?
		if (r < N) {

			final int nc = c < N - 1 ? c + 1 : 0; // [0,8]
			final int nr = nc == 0 ? r + 1 : r; // reset w/ c, no check above, breach indicates

			if (puzzel[r][c] > 0) { // skip prefilled
				return solve(puzzel, nr, nc, rows, cols, sqrs);
			}

			final int s = S * (r / S) + c / S;

			final Collection<Integer> opts = intersect(intersect(rows[r], cols[c]), sqrs[s]);
			for (final int opt : opts) {

				rows[r].removeElement(opt); // save
				cols[c].removeElement(opt);
				sqrs[s].removeElement(opt);
				puzzel[r][c] = opt;

				final int[][] solution = solve(puzzel, nr, nc, rows, cols, sqrs);

				if (solution == null) { // bad branch, rollback
					rows[r].push(opt);
					cols[c].push(opt);
					sqrs[s].push(opt);
					puzzel[r][c] = 0;
				}
				else {
					return solution; // solution branch
				}
			}

			return null; // option exhaustion, bad branch
		}

		return puzzel; // solution found
	}

	private <T> Collection<T> intersect(final Collection<T> c1, final Collection<T> c2) {
		final Collection<T> result = new ArrayList<>(N);
		for(final T item : c1) {
			if(c2.contains(item)) {
				result.add(item);
			}
		}
		return result;
	}

}