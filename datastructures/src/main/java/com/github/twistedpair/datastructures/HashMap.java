package com.github.twistedpair.datastructures;


/**
 * 
 * @author Joseph Lust
 *
 * @param <K> key
 * @param <V> value
 */
public final class HashMap<K, V> {

	private Object[] buckets;

	private int bucketCount = 16;
	private int size;


	public HashMap() {
		super();
		resize(bucketCount);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private int getBucketNumber(final int hash, final int bucketCount) {
		return hash % bucketCount;
	}

	private void resize(final int newSize) {
		bucketCount = newSize;

		// new storage
		final Object[] newBuckets = new Object[newSize];

		for (int n = 0; n < newSize; n++) {
			newBuckets[n] = new LinkedList<Entry<K, V>>();
		}

		// rehash all values - if exant
		if(buckets!=null) {
			for (final Object list : buckets) {
				final LinkedList<Entry<K, V>> linkedList = (LinkedList<Entry<K, V>>) list;

				for(final Entry<K,V> entry : linkedList) {
					final int bucketNumber = getBucketNumber(entry.getHash(), newSize);
					((LinkedList<Entry<K, V>>)newBuckets[bucketNumber]).add(entry);
				}
			}
		}

		buckets = newBuckets;

	}

	/**
	 * Does not handle NULL (no null bucket)
	 * 
	 * @param key
	 * @param value
	 */
	public void put(final K key, final V value) {
		put(new Entry<K, V>(key, value, key.hashCode()));
	}

	private void put(final Entry<K, V> entry) {
		final Entry<K, V> foundEntry = getEntry(entry);

		// update
		if (foundEntry != null) {
			foundEntry.setValue(entry.getValue());
		}
		else {
			getBucket(entry.getHash()).add(entry);
			size++;
		}

		// resize if limit hit
		if (size >= bucketCount) {
			resize(size * 2);
		}
	}

	private Entry<K, V> getEntry(final Entry<K, V> entry) {

		final LinkedList<Entry<K, V>> bucket = getBucket(entry.getHash());
		return bucket.find(entry);
	}

	public V get(final K key) {
		if(isEmpty()) {
			return null;
		}

		final Entry<K, V> entry = new Entry<K, V>(key, key.hashCode());
		final Entry<K, V> foundEntry = getEntry(entry);

		if(foundEntry==null) {
			return null;
		}
		return foundEntry.getValue();
	}

	private LinkedList<Entry<K, V>> getBucket(final int hash) {
		final int bucketIdx = getBucketNumber(hash, bucketCount);
		return (LinkedList<Entry<K, V>>) buckets[bucketIdx];
	}

	public void remove(final K key) {
		// NOOP

		// find node and remove

		// TODO resize if too large
	}

	public int size() {
		return size;
	}

	/** Entry helper **/
	private static final class Entry<K, V> {
		private final K k;
		private V v;
		private final int hash;

		public Entry(final K k, final V v, final int hash) {
			super();
			this.k = k;
			this.v = v;
			this.hash = hash;
		}

		public Entry(final K k, final int hash) {
			super();
			this.k = k;
			this.hash = hash;
		}

		public final K getK() {
			return k;
		}

		public final V getValue() {
			return v;
		}

		public final int getHash() {
			return hash;
		}

		public final void setValue(final V v) {
			this.v = v;
		}

		@Override
		public String toString() {
			return "Entry [k=" + k + ", v=" + v + ", hash=" + hash + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (k == null ? 0 : k.hashCode());
			return result;
		}

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
			final Entry other = (Entry) obj;
			if (k == null) {
				if (other.k != null) {
					return false;
				}
			}
			else if (!k.equals(other.k)) {
				return false;
			}
			return true;
		}


	}
}