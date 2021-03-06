package com.github.twistedpair.sort;


public final class InsertionSort<T extends Comparable<T>> extends Sorter<T> {

	@Override
	public void sort(final T[] arr) {

		final int N = arr.length;
		for (int i = 0; i < N; i++) { // scan down
			for (int j = i; j > 0 && less(arr, j, j - 1); j--) { // scan back
				exch(arr, j, j - 1);
			}
		}
	}
}