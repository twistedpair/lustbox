package com.github.twistedpair.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Necessary broken LinkedList implementation that will allow creation of a cycle
 * 
 * @author Joseph Lust
 * @param <E>
 */
final class CyclicLinkedList<E> implements Iterable<E> {

	private ListNode<E> root;
	private ListNode<E> last;

	private int size;

	private final CycleDetectionStrategy<E> cycleDetectionStrategy = new TortoiseHareCycleDetectionStrategy<E>();

	/** Let user tell us how large a cycle to make **/

	/**
	 * Create nodes with a cycle
	 * 
	 * @param listSize
	 * @param cycleSize
	 *            set to 0 for no cycle
	 */
	public void addCycle(final int listSize, final int cycleSize) {

		assert listSize > 0 : "List size must be > 0";
		assert cycleSize >= 0 : "List size must be >= 0";
		assert cycleSize <= listSize : "Cycle cannot be larger than listSize";

		final int cycleEdgetIdx = listSize - cycleSize;

		int nodeCount = 1;
		while (nodeCount <= listSize) {

			@SuppressWarnings("unchecked")
			final E mockPayload = (E) Integer.toString(nodeCount);

			if (nodeCount == listSize && cycleSize > 0) {
				// add cycle by final return edge
				final ListNode<E> node = addNode(mockPayload, null);
				node.setNext(get(cycleEdgetIdx));
			}
			else {
				// keep growing
				addNode(mockPayload, null);
			}

			size++;
			nodeCount++;
		}
	}

	private ListNode<E> get(final int idx) {
		assert idx < size : "Out of range index: " + idx;

		int n = 0;
		ListNode<E> node = root;
		while (n < idx) {
			node = node.getNext();
			n++;
		}
		return node;
	}

	private ListNode<E> addNode(final E payload, final ListNode<E> next) {

		final ListNode<E> appenedNode = new ListNode<E>(payload, null);
		if (size == 0) { // start
			root = appenedNode;
			last = root;
			cycleDetectionStrategy.setRootNode(root);
		}
		else { // append
			last.setNext(appenedNode);
			last = appenedNode;
		}
		size++;
		return appenedNode;
	}

	public boolean isCyclic() {
		return cycleDetectionStrategy.isCyclic(this);
	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator<E>(root);
	}

	/** Iterator **/
	private class LinkedListIterator<P> implements Iterator<P> {

		private ListNode<P> currentNode;

		public LinkedListIterator(final ListNode<P> root) {
			super();
			this.currentNode = root;
		}

		@Override
		public boolean hasNext() {
			// null check because could be an empty iterator
			return currentNode != null && currentNode.getNext() != null;
		}

		@Override
		public P next() {
			if (hasNext()) {
				currentNode = currentNode.getNext();
				return currentNode.getValue();
			}
			else {
				throw new NoSuchElementException(
						"Cannot call next if iterator hasNext() is false!");
			}
		}

		@Override
		public void remove() {
			throw new IllegalStateException("Not implemented");
		}

	}
}