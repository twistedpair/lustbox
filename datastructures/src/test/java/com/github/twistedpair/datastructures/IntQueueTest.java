package com.github.twistedpair.datastructures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntQueueTest {

	@Test
	public void testIntQueue() {

		final int queueSize = 100;
		final IntQueue q = new IntQueue(queueSize);

		assertEquals("Wrong number enqueued", 0, q.getNumEnqueued());

		final int first = 13;
		q.push(first);

		assertEquals(first, q.front());

		for (int i = 0; i < queueSize*2; i++) {
			q.push(i);
		}

		assertEquals(first, q.front());

		for (int i = 0; i < queueSize-1; i++){
			q.pop();
			assertEquals(i, q.front());
		}

	}
}
