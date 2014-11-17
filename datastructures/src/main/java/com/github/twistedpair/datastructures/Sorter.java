package com.github.twistedpair.datastructures;


public abstract class Sorter<T extends Comparable<T>> {

	public abstract T[] sort(final T[] arr);

	protected boolean less(final T[] arr, final int o1, final int o2) {
		return arr[o1].compareTo(arr[o2]) < 0;
	}

	protected boolean less(final T o1, final T o2) {
		return o1.compareTo(o2) < 0;
	}

	protected void exch(final T[] arr, final int from, final int to) {
		if (from == to) { return; }

		final T temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
}