package com.github.twistedpair.puzzle;

import java.util.HashSet;
import java.util.Set;



public final class EightQueens {

	private static final int N = 8;

	private final Set<String> solutions = new HashSet<>();

	public int solutions() {

		solutions.clear();

		// try every possible start position
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {

				// TODO fold this into main body
				final boolean[] rows = new boolean[N]; // rows wth queens
				final boolean[] cols = new boolean[N]; // cols with queens
				final boolean[][] solution = new boolean[N][N];
				solution[r][c] = true;
				rows[r] = true;
				cols[c] = true;

				if (putQueen(r, c, rows, cols, 1, solution)) {
					// printSoln(solution);
				}
			}
		}

		for (final String soln : solutions) {
			System.out.println(soln);
		}

		return solutions.size();
	}

	private boolean putQueen(final int r, final int c, final boolean[] rows,
			final boolean[] cols, final int queens, final boolean[][] solution) {

		// System.out.println(String.format("r: %s c: %s q: %s", r, c, queens));

		if (queens < N) { // queens left?

			// cover remaining possible moves
			for (int row = 0; row < N; row++) { // row clear?
				if (rows[row]) {continue;}

				for (int col = 0; col < N; col++) { // col clear?
					if (cols[col]) {continue;}

					// diagonal clear?
					boolean skip = false;
					for (int cd = 0; cd < N; cd++) {
						final int rd = cd-col;
						if(row+rd < N && row+rd >=0 && solution[row+rd][cd]) {skip=true; break;}
						if(row-rd < N && row-rd >=0 && solution[row-rd][cd]) {skip=true; break;}
					}

					if(skip) {continue;}

					// insert
					cols[col] = true;
					rows[row] = true;
					solution[row][col] = true;

					if (!putQueen(row, col, rows, cols, queens + 1, solution)) {
						cols[col] = false; // undo
						rows[row] = false;
						solution[row][col] = false; // try next
					}
					else {
						return true; // solution found, find next
					}
				}
			}

			// printSoln(solution);
			return false;
		}
		else { // solution!
			saveSolution(solution);
			return true;
		}
	}

	private void saveSolution(final boolean[][] board) {
		final StringBuilder sb = new StringBuilder(8);
		for (final boolean row[] : board) {
			for (final boolean col : row) {
				if (!col) {
					sb.append('-');
				}
				else {
					sb.append('+');
				}
			}
			sb.append(' ');
		}

		solutions.add(sb.toString());
	}

	private void printSoln(final boolean[][] board) {
		for (final boolean row[] : board) {
			final StringBuilder sb = new StringBuilder(8);
			for (final boolean col : row) {
				if (!col) {
					sb.append('-');
				}
				else {
					sb.append('+');
				}
			}
			System.out.println(sb.toString());
		}
		System.out.println("");
	}
}