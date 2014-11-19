package com.github.twistedpair.datastructures;

import java.util.Iterator;

final class RedBlackTree<K extends Comparable<K>, V> implements Iterable<K> {

	private class Node<KK extends Comparable<KK>, VV> {
		Node<KK, VV> left;
		Node<KK, VV> right;
		private final KK key;
		VV value;

		public Node(final KK key, final VV value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Node [key=" + key + ", value=" + value + "]";
		}
	}

	private class KeyIterator implements Iterator<K> {

		private Node<K, V> node;

		public KeyIterator(final Node<K, V> root) {
			super();
			node = root;
		}

		@Override
		public boolean hasNext() {
			if (node != null) {

			}
			return false;
		}

		@Override
		public K next() {
			if (node.left != null) {
				node = node.left;
				return node.key;
			}
			else if (node.right != null) {
				node = node.right;
				return node.key;
			}
			else {
				// go up - but we'd need a parent ref in every node
			}
			return node.key;
		}

		@Override
		public void remove() {
			// NOOP
		}
	}

	private Node<K, V> root;
	private int size;
	private int lastBalance;
	// TODO keep reference to first/last for performance

	public void add(final K key, final V value) {
		// check if already exists!
		final Node<K,V> node = new Node<>(key, value);

		if (root == null) {
			root = new Node<>(key, value);
			size++;
		}
		else {
			insert(node, root);
		}

		balance();
	}

	public void remove(final K key) {
		remove(key, null, root);
	}

	private void remove(final K key, final Node<K,V> prevLoc, final Node<K,V> loc) {

		final int cmp = key.compareTo(loc.key);
		if (cmp < 0) {
			remove(key, loc, loc.left);
		}
		else if (cmp > 0) {
			remove(key, loc, loc.right);
		}
		else { // found target node!
			// pull up left/right if present
			if (loc.left != null || loc.right != null) {
				final boolean isPrevNodeLeft = prevLoc.left.key.compareTo(key) == 0;
				if (isPrevNodeLeft) {
					// was left branch
					prevLoc.left = loc;
					// reinsert all values to the right?
				}
				else {
					// was right branch
					prevLoc.right = loc;
					// reinsert all values to the left?
				}
			}
		}
	}

	private void insert(final Node<K, V> node, final Node<K, V> loc) {

		final int cmp = node.key.compareTo(loc.key);
		if (cmp < 0) {
			if (loc.left == null) {
				loc.left = node;
				size++;
				return;
			}
			insert(node, loc.left);
		}
		else if (cmp > 0) {
			if (loc.right == null) {
				loc.right = node;
				size++;
				return;
			}
			insert(node, loc.right);
		}
		else {
			loc.value = node.value; // overwrite!
		}
	}

	private Node<K,V> find(final K key, final Node<K, V> node) {
		if(node==null) {
			return null; // not found
		}

		final int cmp = key.compareTo(node.key);
		if (cmp < 0) {
			return find(key, node.left);
		}
		else if (cmp > 0) {
			return find(key, node.right);
		}
		else { // same node!
			return node;
		}
	}

	public V get(final K key) {
		final Node<K,V> node = find(key,root);
		if(node==null) {
			return null;
		}
		return node.value;
	}

	public boolean contains(final K key) {
		final Node<K, V> node = find(key, root);
		return node != null;
	}

	public K first() {
		if(size==0) {
			return null;
		}
		return first(root);
	}

	private K first(final Node<K,V> node) {
		if(node.left!=null) {
			return first(node.left);
		}
		return node.key;
	}

	public K last() {
		if(size==0) {
			return null;
		}
		return last(root);
	}

	private K last(final Node<K,V> node) {
		if(node.right!=null) {
			return last(node.right);
		}
		return node.key;
	}


	private void balance() {
		// don't balance on every call or for small sizes
		if (lastBalance < 10 || size < 8) { // TODO move to static
			lastBalance++;
			return;
		}

		// balance
		lastBalance = 0;
	}

	@Override
	public Iterator<K> iterator() {
		return new KeyIterator(root);
	}

	public int size() {
		return size;
	}

}