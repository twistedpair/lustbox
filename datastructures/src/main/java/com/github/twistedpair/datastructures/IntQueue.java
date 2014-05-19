package com.github.twistedpair.datastructures;

import java.util.NoSuchElementException;

public class IntQueue {

	// array container
	private final int[] a;

	// i is head of queue, j is tail
	private int i, j;

	// size of queue
	private final int s;

	// number of items in queue
	private int numEnqueued;

	public IntQueue(final int s) {
		i = 0;
		j = 0;
		a = new int[s];
		this.s = s;
		numEnqueued = 0;
	}

	public int getNumEnqueued() {
		return numEnqueued;
	}

	public void push(final int x) {
		if ( j == i && numEnqueued > 0 ){
			//System.out.println("Queue is full");
		}
		else {
			// place at tail, inc
			a[j] = x;
			j = (j + 1) % s;
			numEnqueued++;
		}
	}

	public void printContents() {
		System.out.print("Contents: ");
		for (int ii = 0; ii < s; ii++) {
			System.out.print(a[ii] + " ");
		}
		System.out.println();
	}

	public void printQueue() {
		System.out.print("Queue: ");
		for (int offset = 0; offset < numEnqueued; offset++) {
			System.out.print(a[(i + offset) % s] + " ");
		}
		System.out.println();
	}

	public int front() {
		if (numEnqueued == 0) {
			throw new NoSuchElementException();
		}
		return a[i];
	}

	public void pop() {

		if (numEnqueued == 0) {
			System.out.println("pop() Empty Queue");
		}
		else {
			final int x = a[i];
			i = (i + 1) % s;
			numEnqueued--;
		}
	}

	public int get(final int i) {
		return a[i];
	}

}
