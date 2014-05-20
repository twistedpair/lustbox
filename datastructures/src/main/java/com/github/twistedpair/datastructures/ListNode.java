package com.github.twistedpair.datastructures;

final class ListNode<E> {

	private final E value;
	private ListNode<E> next;


	public ListNode(final E value, final ListNode<E> next) {
		super();
		this.value = value;
		this.next = next;
	}

	public boolean nodeEquals(final ListNode<E> target) {
		// NPE protect
		if(value==null) {
			return target.getValue()==null;
		}
		return value.equals(target.getValue());
	}

	public final ListNode<E> getNext() {
		return next;
	}

	public final boolean hasNext() {
		return next != null;
	}

	public final void setNext(final ListNode<E> next) {
		this.next = next;
	}

	public final E getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ListNode [value=" + value + ", next=" + next + "]";
	}

}