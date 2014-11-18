package com.github.twistedpair.sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;


public final class LinkedListSort<T extends Comparable<T>> {

	@SuppressWarnings("unchecked")
	public void sort(final LinkedList<T> list) {

		// hack sol'n. Why would you sort a LL? Use arrays! That's what the JDK does!
		final Object[] arr = list.toArray();

		// final Sorter<T> sort = new QuickSort<>(); // my way, but can't do with proper generics
		// sort.sort((T[]) arr);
		Arrays.sort(arr);

		// fill
		final ListIterator<T> iter = list.listIterator();
		for (int n = 0; n < arr.length; n++) {
			iter.next();
			iter.set((T) arr[n]);
		}
	}
}