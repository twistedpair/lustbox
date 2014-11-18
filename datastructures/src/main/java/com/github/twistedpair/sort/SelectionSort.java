package com.github.twistedpair.sort;


public final class SelectionSort<T extends Comparable<T>> extends Sorter<T> {

	@Override
	public void sort(final T[] arr) {

		final int N = arr.length;
		// scan entire array
		for (int i = 0; i < N; i++) {
			// move min value in remaining range to present scan index
			int min = i;
			for (int j = i + 1; j < N; j++) {
				if (less(arr, j, min)) {
					min = j;
				}
			}
			exch(arr, i, min);
		}
	}
}