package com.github.twistedpair.datastructures;


public final class QuickSort<T extends Comparable<T>> extends Sorter<T> {

	@Override
	public T[] sort(final T[] arr) {

		// TODO randomize before hand w/ Shuffle
		quickSort(arr, 0, arr.length - 1);
		return arr;
	}

	private void quickSort(final T[] arr, final int lo, final int hi) {

		if (hi <= lo) { // single element, can't sort
			return;
		}

		final int p = partition(arr, lo, hi); // set pivot
		quickSort(arr, lo, p - 1);
		quickSort(arr, p + 1, hi);
	}

	// pick pivot, partition, return pivot
	private int partition(final T[] arr, final int lo, final int hi) {

		// enhancement - 2 element arrays, can also do 3 element case and pick median for large partitions
		if (hi - lo == 1) {
			if (less(arr, hi, lo)) {
				exch(arr, hi, lo);
			}
			return lo;
		}

		int i = lo, j = hi + 1;
		final int p = lo;
		final T pVal = arr[p];

		while (true) {
			while (less(arr[++i], pVal) && i < hi) {} // scan LTR, up to hi
			while (less(pVal, arr[--j]) && j > lo) {} // scan RTL, down to pivot
			if (i >= j) { // crossed, we're finished
				break;
			}
			exch(arr, i, j); // out of place, swap

		}
		exch(arr, lo, j); // place pivot in middle
		return j; // new partition @ pivot location
	}
}