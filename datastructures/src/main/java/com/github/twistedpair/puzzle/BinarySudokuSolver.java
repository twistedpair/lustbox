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
public class BinarySudokuSolver {

	private int moveCnt = 0;
	private static final int N = 9;
	private static int S = N / 3;

	private int[][] jump;

	public int[][] solve(final int[][] puzzle) {

		final int[] rows = new int[N]; // generics and arrays can't be friends
		final int[] cols = new int[N];
		final int[] sqrs = new int[N];
		fillStacks(puzzle, rows, cols, sqrs);

		jump = createJumpTable(puzzle);

		// TODO, start at most defined row
		// TODO make preset random jump table, reset table/change start row after threshold moves
		moveCnt = 0;

		final int mIdx = 0; // count down
		final int[] move = jump[mIdx];
		return solve(puzzle, move[0], move[1], rows, cols, sqrs, mIdx + 1);
	}

	private void fillStacks(final int[][] puzzel, final int[] rows,
			final int[] cols, final int[] sqrs) {

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

			rows[r] = arrayToBits(available);
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

			cols[c] = arrayToBits(available);
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

				sqrs[s] = arrayToBits(available);
				s++;
			}
		}
	}

	/**
	 * Convert 1 indexed moves into 0 indexed bit field
	 * 
	 * @param moves
	 * @return
	 */
	private int arrayToBits(final Collection<Integer> moves) {
		int bitMoves = 0;

		for (final int move : moves) {
			bitMoves |= 1 << move - 1; // 1->0, 9->8
		}

		return bitMoves;
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

	private int[][] solve(final int[][] puzzle, final int r, final int c,
			final int[] rows, final int[] cols, final int[] sqrs,
			final int mIdx) {
		moveCnt++;

		// continue?
		if (mIdx < jump.length) {

			final int nr = jump[mIdx][0];
			final int nc = jump[mIdx][1];
			final int s = S * (r / S) + c / S;

			final int moves = rows[r] & cols[c] & sqrs[s]; // intersect
			for (int b=0; b<N; b++) {
				final int mask = 1 << b; // 0 indexed
				if ((moves & mask) > 0) {

					rows[r] ^= mask;
					cols[c] ^= mask;
					sqrs[s] ^= mask;
					puzzle[r][c] = b + 1; // 0->1, 1->2 ...

					final int[][] solution = solve(puzzle, nr, nc, rows, cols, sqrs, mIdx + 1);

					if (solution == null) { // bad branch, rollback
						rows[r] |= mask;
						cols[c] |= mask;
						sqrs[s] |= mask;
						puzzle[r][c] = 0;
					}
					else {
						return solution; // solution branch
					}
				}
			}

			return null; // option exhaustion, bad branch
		}

		return puzzle; // solution found
	}

	public int getMoveCnt() {
		return moveCnt;
	}

}