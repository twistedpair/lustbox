package com.github.twistedpair.sort;

import com.github.twistedpair.sort.AbstractSortIntegerTest;
import com.github.twistedpair.sort.SelectionSort;


/**
 * Common test for sorts
 * 
 * @author Joseph Lust
 */
public final class SelectionSortIntegerTest extends
		AbstractSortIntegerTest<SelectionSort<Integer>> {

	final SelectionSort<Integer> sorter = new SelectionSort<>();

	@Override
	SelectionSort<Integer> getSorter() {
		return sorter;
	}

}
