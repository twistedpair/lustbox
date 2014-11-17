package com.github.twistedpair.datastructures;


/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class InsertionIntegerSortTest extends
		AbstractIntegerSortTest<InsertionSort<Integer>> {

	final InsertionSort<Integer> sorter = new InsertionSort<>();

	@Override
	InsertionSort<Integer> getSorter() {
		return sorter;
	}

}
