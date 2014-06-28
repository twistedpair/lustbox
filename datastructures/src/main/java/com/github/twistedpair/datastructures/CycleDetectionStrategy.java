package com.github.twistedpair.datastructures;


/**
 * Necessary broken LinkedList implementation that will allow creation of a cycle
 * 
 * @author Joseph Lust
 * @param <E>
 */
interface CycleDetectionStrategy<E> {

	boolean isCyclic(final CyclicLinkedList<E> list);

	void setRootNode(final ListNode<E> rootNode);
}