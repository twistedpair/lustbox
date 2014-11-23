package com.github.twistedpair.sort;

/**
 * Simple binary search example
 * 
 * @author Joseph Lust
 *
 */
public final class BinarySearch {

	public static <T extends Comparable<? super T>> int search(final T[] arr,
			final T target) {
		if (arr.length == 0) {
			return -1;
		}
		return binSearch(arr, target, 0, arr.length-1);
	}

	private static <T extends Comparable<? super T>> int binSearch(
			final T[] arr, T target, int min, int max) {
		if (min > max) {
			return -1; // not here
		}
		// TODO for small sizes, i.e. 2, just do direct compare and return
		int mid = (min + max) / 2;
		int cmp = target.compareTo(arr[mid]);
		if (cmp > 0) {
			return binSearch(arr, target, mid + 1, max);
		} else if (cmp < 0) {
			return binSearch(arr, target, min, mid -1);
		} else { // found you
			return mid;
		}
	}
}