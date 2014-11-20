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
		root = remove(key, root);
	}

	private Node<K, V> remove(final K key, Node<K, V> loc) {

		if(loc==null )  {
			return null; // EOB
		}

		final int cmp = key.compareTo(loc.key);
		if (cmp < 0) {
			loc.left = remove(key, loc.left);
		}
		else if (cmp > 0) {
			loc.right = remove(key, loc.right);
		}
		else { // found target node!
			size--;
			// use opposite if one branch null
			if (loc.left == null) {
				return loc.right;
			}
			else if (loc.right == null) {
				return loc.left;
			}
			// we've got both branches - find right minima
			else {
				final Node<K,V> temp = loc;
				loc = min(loc.right); // replacement node (has no left)
				loc.right = removeMin(loc.right);// prune min from right (we've moving it)
				loc.left = temp.left;
			}
		}
		return loc;
	}

	private Node<K,V> removeMin(final Node<K,V> node) {
		if(node.left==null) {
			return node.right;
		}
		node.left = removeMin(node.left);
		return node;
	}

	private Node<K, V> insert(final Node<K, V> node, final Node<K, V> loc) {

		if (loc == null) {
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

}