package com.github.twistedpair.sort;


public final class MergeSort<T extends Comparable<T>> extends Sorter<T> {

	// TODO don't do this - not thread safe
	@SuppressWarnings("rawtypes")
	private Comparable[] aux; // hack b/c cannot instantiate generic array

	@Override
	public void sort(final T[] arr) {
		aux = new Comparable[arr.length];
		tdSort(arr, 0, arr.length - 1);
		aux = null; // prevent memory leak
	}

	@SuppressWarnings("unchecked")  // hack (see above)
	private void merge(final T[] arr, final int lo, final int mid, final int hi) {
		System.arraycopy(arr, lo, aux, lo, hi - lo + 1);

		int i = lo, j = mid + 1;				//1:[lo,mid], j:(mid,hi]

		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				arr[k] = (T) aux[j++];			// w/in left bounds
			}
			else if (j > hi) { 					// w/in right bounds
				arr[k] = (T) aux[i++];			// i < j
			}
			else { // within bounds, compare
                arr[k] = (T) ((less((T[]) aux, i, j)) ? aux[i++] : aux[j++]); // take the lower
            }
		}
	}

	private void tdSort(final T[] arr, final int lo, final int hi) {
		if (hi <= lo) { // single element - cease recursion
			return;
		}

		final int mid = (hi + lo) / 2;	// split
		tdSort(arr, lo, mid);			// left side	[lo,mid]
		tdSort(arr, mid + 1, hi);		// right side 	(mid,hi]
		merge(arr, lo, mid, hi);		// merge at end of recursion
	}
}