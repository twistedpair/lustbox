package com.github.twistedpair.datastructures;


/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class QuickSortIntegerTest extends
		AbstractIntegerSortTest<QuickSort<Integer>> {

	final QuickSort<Integer> sorter = new QuickSort<>();

	@Override
	QuickSort<Integer> getSorter() {
		return sorter;
	}

}
