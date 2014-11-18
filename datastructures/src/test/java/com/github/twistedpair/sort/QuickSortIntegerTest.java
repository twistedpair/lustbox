package com.github.twistedpair.sort;

/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class QuickSortIntegerTest extends
		AbstractSortIntegerTest<QuickSort<Integer>> {

	final QuickSort<Integer> sorter = new QuickSort<>();

	@Override
	QuickSort<Integer> getSorter() {
		return sorter;
	}

}
