package com.github.twistedpair.sort;

import com.github.twistedpair.sort.AbstractSortIntegerTest;


/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class InsertionSortIntegerTest extends
	AbstractSortIntegerTest<InsertionSort<Integer>> {

	final InsertionSort<Integer> sorter = new InsertionSort<>();

	@Override
	InsertionSort<Integer> getSorter() {
		return sorter;
	}

}
