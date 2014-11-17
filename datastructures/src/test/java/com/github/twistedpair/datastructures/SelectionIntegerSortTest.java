package com.github.twistedpair.datastructures;


/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class SelectionIntegerSortTest extends
		AbstractIntegerSortTest<SelectionSort<Integer>> {

	final SelectionSort<Integer> sorter = new SelectionSort<>();

	@Override
	SelectionSort<Integer> getSorter() {
		return sorter;
	}

}
