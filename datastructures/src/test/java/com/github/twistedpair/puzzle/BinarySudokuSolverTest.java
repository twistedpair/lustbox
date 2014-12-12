package com.github.twistedpair.puzzle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class BinarySudokuSolverTest {

	private static final int N = 9;
	private static int S = N / 3;

	final BinarySudokuSolver sudoku = new BinarySudokuSolver(); // sure, could all be static

	@Test
	public void testMe() {
		final int[][] testPuzzel = getPuzzel();
		final int[][] actual = sudoku.solve(testPuzzel);

		// pretty print
		assertNotNull("No solution found!", actual);

		printMatrix(actual, sudoku.getMoveCnt());
		assertTrue("Bad solution found!", isValid(actual));
	}

	private boolean isValid(final int[][] field) {
		// rows
		for (final int[] row : field) {
			if (!isRowValid(row)) { return false; }
		}
		// cols
		for (int c = 0; c < N; c++) {
			// extract col
			final int[] col = new int[N];
			for (int r = 0; r < N; r++) {
				col[r] = field[r][c];
			}

			if (!isRowValid(col)) { return false; }
		}
		// cubes - ignoring kernel pain for now
		// Wow, this is ugly!
		for (int sr = 0; sr < S; sr++) {
			for (int sc = 0; sc < S; sc++) {
				// SxS kernel - could just unwind with direct indexed access
				final int[] sqr = new int[N];
				int i = 0;
				for (int r = S * sr; r < S * (sr + 1); r++) {
					for (int c = S * sc; c < S * (sc + 1); c++) {
						sqr[i] = field[r][c];
						i++;
					}
				}
				// test
				if (!isRowValid(sqr)) { return false; }
			}
		}

		return true;
	}

	private boolean isRowValid(final int[] row) {
		boolean isValid = true;
		final boolean[] hasValueArr = new boolean[N + 1];
		for (final int colVal : row) {
			hasValueArr[colVal] = true; // mark values we've got
		}
		for (int n = 1; n < N + 1; n++) {
			isValid &= hasValueArr[n];
		}

		return isValid;
	}

	private int[][] getPuzzel() {
		final int[][] field = new int[N][N];

		final int[] raw = new int[] {
				0, 0, 0,  0, 1, 0,  0, 2, 6,
				0, 0, 6,  0, 0, 4,  0, 0, 0,
				0, 8, 0,  7, 0, 0,  5, 0, 0,

				0, 0, 4,  0, 0, 3,  0, 9, 0,
				9, 0, 0,  0, 0, 0,  0, 0, 8,
				0, 1, 0,  5, 0, 0,  3, 0, 0,

				0, 0, 5,  0, 0, 2,  0, 7, 0,
				0, 0, 0,  3, 0, 0,  4, 0, 0,
				6, 4, 0,  0, 8, 0,  0, 0, 0
		};

		for(int r=0; r<N; r++) {
			System.arraycopy(raw,r*N,field[r],0,N);
		}
		/*
		field[0] = new int[] { 0, 0, 0, 0, 0, 0, 0, 5, 0 };
		field[1] = new int[] { 0, 0, 0, 7, 0, 0, 8, 0, 0 };
		field[2] = new int[] { 5, 4, 0, 6, 0, 0, 9, 0, 3 };

		field[3] = new int[] { 0, 3, 0, 0, 6, 7, 0, 0, 0 };
		field[4] = new int[] { 0, 0, 4, 0, 0, 0, 1, 0, 0 };
		field[5] = new int[] { 0, 0, 0, 4, 2, 0, 0, 6, 0 };

		field[6] = new int[] { 4, 0, 1, 0, 0, 9, 0, 2, 7 };
		field[7] = new int[] { 0, 0, 2, 0, 0, 5, 0, 0, 0 };
		field[8] = new int[] { 0, 8, 0, 0, 0, 0, 0, 0, 0 };
		 */
		return field;
	}

	private void printMatrix(final int[][] mat, final int moveCnt) {
		for (final int[] row : mat) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println(String.format("Attempted %s iterations", moveCnt));
	}

}