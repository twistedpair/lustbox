package com.github.twistedpair.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Recursive square/col/row remaining move intersect solver<br>
 * TODO don't iterate in line scan mode. Pick random set of moves and jump through them
 * 
 * @author Joseph Lust
 */
public class SudokuSolver {

	private int moveCnt = 0;
	private static final int N = 9;
	private static int S = N / 3;

	private int[][] jump;

	public int[][] solve(final int[][] puzzle) {

		final Stack<Integer>[] rows = new Stack[N]; // generics and arrays can't be friends
		final Stack<Integer>[] cols = new Stack[N];
		final Stack<Integer>[] sqrs = new Stack[N];
		fillStacks(puzzle, rows, cols, sqrs);

		jump = createJumpTable(puzzle);

		// TODO, start at most defined row
		// TODO make preset random jump table, reset table/change start row after threshold moves
		moveCnt = 0;

		final int mIdx = 0; // count down
		final int[] move = jump[mIdx];
		return solve(puzzle, move[0], move[1], rows, cols, sqrs, mIdx + 1);
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

	/**
	 * Tried using this to make different iterating patterns<br>
	 * Also reduces time spent computing next move by making a lookup
	 * 
	 * @param puzzle
	 * @return
	 */
	private int[][] createJumpTable(final int[][] puzzle) {
		// would prefer arrays, but only one instance, so no need for verbosity
		final List<List<Integer>> jumps = new ArrayList<>(N * N);

		/*
		// iterate by columns
		for(int c=0; c<N; c++) {
			for (int r = 0; r < N; r++) {
				if (puzzle[r][c] == 0) {
					final List<Integer> jump = new ArrayList<>(2);
					jump.add(r);
					jump.add(c);
					jumps.add(jump);
				}
			}
		}
		// iterate by rows
		for (int r = 0; r < N; r++) {
			for(int c=0; c<N; c++) {
				if (puzzle[r][c] == 0) {
					final List<Integer> jump = new ArrayList<>(2);
					jump.add(r);
					jump.add(c);
					jumps.add(jump);
				}
			}
		}
		 */

		// iterate by squares
		for (int sr = 0; sr < S; sr++) {
			for (int sc = 0; sc < S; sc++) {
				// SxS kernel
				for (int r = S * sr; r < S * (sr + 1); r++) {
					for (int c = S * sc; c < S * (sc + 1); c++) {
						if (puzzle[r][c] == 0) {
							final List<Integer> jump = new ArrayList<>(2);
							jump.add(r);
							jump.add(c);
							jumps.add(jump);
						}
					}
				}
			}
		}

		// Collections.shuffle(jumps); // nope
		// Collections.reverse(jumps); // works - perhaps within rows?

		int i = 0;
		final int[][] result = new int[jumps.size() + 1][2]; // pad 1 empty at end
		for(final List<Integer> jump : jumps) {
			result[i++] = new int[] { jump.get(0), jump.get(1) };
		}

		return result;
	}

	private int[][] solve(final int[][] puzzel, final int r, final int c,
			final Stack<Integer>[] rows, final Stack<Integer>[] cols,
			final Stack<Integer>[] sqrs, final int mIdx) {
		moveCnt++;

		// continue?

		//if (r < N) {
		if (mIdx < jump.length) {

			final int nr = jump[mIdx][0];
			final int nc = jump[mIdx][1];
			final int s = S * (r / S) + c / S;

			final Collection<Integer> opts = intersect(intersect(rows[r], cols[c]), sqrs[s]);
			for (final int opt : opts) {

				rows[r].removeElement(opt); // save - need a better data structure, is bulk of time
				cols[c].removeElement(opt);
				sqrs[s].removeElement(opt);
				puzzel[r][c] = opt;

				final int[][] solution = solve(puzzel, nr, nc, rows, cols, sqrs, mIdx + 1);

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

	public int getMoveCnt() {
		return moveCnt;
	}

}