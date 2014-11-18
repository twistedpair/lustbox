package com.github.twistedpair.sort;

import com.github.twistedpair.sort.AbstractIntegerSortTest;
import com.github.twistedpair.sort.SelectionSort;


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
