package com.github.twistedpair.datastructures;

/**
 * Use Tortoise and Hare cycle detection
 * 
 * @author Joseph Lust
 * @param <E>
 *            node element type
 */
final class TortoiseHareCycleDetectionStrategy<E> implements
CycleDetectionStrategy<E> {

	private ListNode<E> root;

	@Override
	public boolean isCyclic(final CyclicLinkedList<E> list) {

		if (root == null) { return false; } // empty list

		ListNode<E> tortoise = root;
		ListNode<E> hare = root;

		while (hare != null && hare.getNext() != null) {
			tortoise = tortoise.getNext();
			hare = hare.getNext().getNext();

			if (hare == tortoise) {
				return true;
			}
		}

		return false; // EOL reached
	}

	@Override
	public void setRootNode(final ListNode<E> rootNode) {
		root = rootNode;
	}

}