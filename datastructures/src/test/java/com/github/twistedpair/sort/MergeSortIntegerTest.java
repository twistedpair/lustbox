package com.github.twistedpair.sort;

/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class MergeSortIntegerTest extends
		AbstractSortIntegerTest<Sorter<Integer>> {

	final Sorter<Integer> sorter = new MergeSort<>();

	@Override
	Sorter<Integer> getSorter() {
		return sorter;
	}

}