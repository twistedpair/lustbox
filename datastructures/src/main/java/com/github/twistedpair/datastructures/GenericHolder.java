package com.github.twistedpair.datastructures;


/**
 * Generic holder to test abilities to find generic types despite erasure
 * 
 * @author Joseph Lust
 * @param <T>
 */
final class GenericHolder<T> {
	private final T value;

	public GenericHolder(final T value) {
		super();
		this.value = value;
	}

	// assumes non-null values
	public boolean isNumericInstanceOf() {
		return value instanceof Number;
	}

	public boolean isNumericIsAssignableFrom() {
		return Number.class.isAssignableFrom(value.getClass());
	}

	public boolean isNumericIsInstance() {
		return Number.class.isInstance(value);
	}
}