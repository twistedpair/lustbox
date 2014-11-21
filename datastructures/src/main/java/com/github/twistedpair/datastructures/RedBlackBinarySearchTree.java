package com.github.twistedpair.datastructures;

import java.util.Iterator;

final class RedBlackBinarySearchTree<K extends Comparable<K>, V> implements Iterable<K> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private class Node<KK extends Comparable<KK>, VV> implements Comparable<Node<KK, VV>> {
		Node<KK, VV> left;
		Node<KK, VV> right;
		private final KK key;
		VV value;
		int N;
		boolean color;

		public Node(final KK key, final VV value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Node [key=" + key + ", value=" + value + "]";
		}

		// seems prettier, unused at present
		@Override
		public int compareTo(final Node<KK, VV> node) {
			return key.compareTo(node.key);
		}
	}

	private class KeyIterator implements Iterator<K> {

		// TODO Quite annoying. Usually path back to root encoded in stack frames
		// How move to position, but keep that path? Additional stack?
		// Note possible w/o parent ref in Node object, but that's cheating.
		// Look at JDK TreeSet -> extracts list of keys, returns iterator for that -> what a hack
		private Node<K, V> root;

		public KeyIterator(final Node<K, V> root) {
			super();
			this.root = root;
		}

		@Override
		public boolean hasNext() {
			if (root != null) {

			}
			return false;
		}

		@Override
		public K next() {
			if (root.left != null) {
				root = root.left;
				return root.key;
			}
			else if (root.right != null) {
				root = root.right;
				return root.key;
			}
			else {
				// go up - but we'd need a parent ref in every node
			}
			return root.key;
		}

		@Override
		public void remove() {
			// NOOP
		}
	}

	// So few items!
	private Node<K, V> root;
	private int size;
	private int lastBalance;
	// TODO keep reference to first/last for performance

	public void add(final K key, final V value) {
		final Node<K, V> node = new Node<>(key, value); // is superfluous for updates
		root = insert(node, root);
		root.color = BLACK; // root is Always black

		balance();
	}

	public void remove(final K key) {
		root = remove(key, root);
	}

	/**
	 * Remove the node from the given tree. Target node may be the root or below.
	 * 
	 * @param target
	 * @param loc
	 * @return redacted tree
	 */
	private Node<K, V> remove(final K key, final Node<K, V> loc) {

		if(loc==null) {
			return null; // hit if no such node to remove, i.e. Empty tree
		}

		// and return null
		final int cmp = key.compareTo(loc.key);
		if (cmp < 0) { // in left branch
			loc.left = remove(key, loc.left);
		}
		else if (cmp > 0) { // in right branch
			loc.right = remove(key, loc.right);
		}
		else { // at target
			// true removal only happens at null branch
			if (loc.right == null) { size--; return loc.left; }
			if (loc.left == null) { size--; return loc.right; }

			final Node<K, V> min = min(loc.right);
			min.right = remove(min.key, loc.right);
			min.left = loc.left;
			return min;
		}

		return loc;
	}

	private Node<K, V> insert(final Node<K, V> node, final Node<K, V> loc) {

		if (loc == null) {
			// adding
			size++;
			return node;
		}

		final int cmp = node.key.compareTo(loc.key);
		if (cmp < 0) {
			loc.left = insert(node, loc.left);
		}
		else if (cmp > 0) {
			loc.right = insert(node, loc.right);
		}
		else {
			loc.value = node.value; // overwrite!
		}
		return loc;
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

	/*
	 *  Seemed like a good idea.
	 *  First fetch the parent, if found, prune the subtree
	 *  Makes a nice remove from subtree function, don't need to start at root.
	 *  BUT, null is then overloaded - could be trying to find root, or might
	 *  not exist in tree.
	 *  Totality of checks make it less efficient than just starting from root
	 *  on each search. Useful however if you just wanted to repeatedly search
	 *  a subtree
	 */
	private Node<K, V> findParent(final K key, final Node<K, V> node) {
		if (node == null) { return null; }// not found

		final int cmp = key.compareTo(node.key);
		if (cmp < 0) {
			if (node.left != null && key.compareTo(node.left.key) == 0) { // look ahead left
				return node;
			}
			return find(key, node.left);
		}
		else if (cmp > 0) {
			if (node.right != null && key.compareTo(node.right.key) == 0) { // look ahead right
				return node;
			}
			return find(key, node.right);
		}
		else { // same node!
			return null; // started at target, can't get parent!
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
		return find(key, root) != null;
	}

	public K min() {
		if(size==0) {
			return null;
		}
		return min(root).key;
	}

	private Node<K,V> min(final Node<K,V> node) {
		if (node.left == null) {
			return node;
		}
		return min(node.left);
	}

	/**
	 * Find entry <= given key
	 * 
	 * @param key
	 * @return
	 */
	public K floor(final K key) {
		if (size == 0) {
			return null;
		}

		final Node<K,V> node = floor(key, root);
		if(node==null) {
			return null;
		}
		return node.key;
	}

	public Node<K,V> floor(final K key, final Node<K,V> node) {
		if(node==null) {
			return null;
		}

		final int cmp = key.compareTo(node.key);
		if (cmp == 0) {
			return node;
		}
		else if (cmp < 0) {
			return floor(key, node.left);
		}
		else {
			final Node<K, V> right = floor(key, node.right);
			return right == null ? node : right;
		}
	}

	public K max() {
		if(size==0) {
			return null;
		}
		return max(root).key;
	}

	private Node<K,V> max(final Node<K,V> node) {
		if(node.right!=null) {
			return max(node.right);
		}
		return node;
	}

	/**
	 * HELPERS
	 */

	private boolean isBlack(final Node<K, V> node) {
		if (node == null) { return true; // all leaves are black
		}
		return node.color == BLACK;
	}

	private void balance() {
		// don't balance on every call or for small sizes
		if (lastBalance < 10 || size < 8) { // TODO move to static
			lastBalance++;
			return;
		}

		// TODO implement

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

	public boolean isEmpty() {
		return size == 0;
	}
}