package com.github.twistedpair.datastructures;

final class Node<K, V> {

	private final K key;
	private final V value;
	private final int hash;
	private Node<K, V> next;


	public Node(final K key, final V value, final int hash,
			final Node<K, V> next) {
		super();
		this.key = key;
		this.value = value;
		this.hash = hash;
		this.next = next;
	}

	public final K getKey() {
		return key;
	}

	public final V getValue() {
		return value;
	}

	public final int getHash() {
		return hash;
	}

	public final Node<K, V> getNext() {
		return next;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (key == null ? 0 : key.hashCode());
		return result;
	}

	// based only on key equality
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Node other = (Node) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		}
		else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}

	public final void setNext(final Node<K, V> next) {
		this.next = next;
	}

}