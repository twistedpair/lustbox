package com.github.twistedpair.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Single Linked List
 * 
 * @author Joseph Lust
 * @param <E>
 */
final class LinkedList<E> implements Iterable<E> {

	private ListNode<E> root;
	private ListNode<E> last;

	private int size;

	public LinkedList() {
		super();
	}

	public void add(final E value) {

		final ListNode<E> node = new ListNode<E>(value, null);

		if (isEmpty()) {
			root = node;
		}
		else {
			last.setNext(node);
		}
		last = node;
		size++;
	}

	public ListNode<E> findRecursive(final ListNode<E> targetNode, final ListNode<E> node) {
		if (node == null) { // end of list
			return node;
		}
		if (node.nodeEquals(targetNode)) { // found it
			return node;
		}
		return findRecursive(targetNode, node.getNext()); // go fish
	}

	private ListNode<E> findNode(final ListNode<E> targetNode) {
		if (targetNode == null) {
			return null;
		}
		return findRecursive(targetNode, root);
	}

	public E find(final E target) {
		if (target == null) {
			return null;
		}
		final ListNode<E> targetNode = new ListNode<E>(target, null);
		final ListNode<E> result = findNode(targetNode);
		return result != null ? result.getValue() : null;
	}

	public void remove(final E target) {
		remove(new ListNode<E>(target, null));
	}

	// you could do this recursively with a slight modification of the above
	private void remove(final ListNode<E> currentNode) {

		if (isEmpty()) { return; }

		// find node O(n)
		ListNode<E> node = root;
		ListNode<E> prevNode = null;

		while (node.hasNext() || node == last) {

			if (node.nodeEquals(currentNode)) {
				// remove

				// at start
				if (prevNode == null) { // at root
					root = node.getNext(); // pull up 1 to 0
				}
				// in middle
				else {
					prevNode.setNext(node.getNext());
				}
				// at end
				if (last == node) {
					last = prevNode;
				}

				size--;
				return;
			}

			prevNode = node;
			node = node.getNext(); // next
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator<E>(root);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
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

		@SuppressWarnings("unchecked") // I know, ugly
		@Override
		public void remove() {
			if(currentNode==null) {
				throw new IllegalStateException("Cannot remove from empty iterator");
			}
			else {
				// call remove on enclosing class - ugly cast b/c of generic type cycle
				LinkedList.this.remove((ListNode<E>) currentNode);
			}
		}

	}
}