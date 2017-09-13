package com.common.utils.memcached;

/**
 * A value with a CAS identifier.
 */
public class CASValue<T> {

    private final long version;

    private final T value;

    /**
     * Construct a new CASValue with the given identifer and value.
     *
     * @param version
     *            the CAS identifier
     * @param value
     *            the value
     */
    public CASValue(long version, T value) {
        super();
        this.value = value;
        this.version = version;
    }

    public T getValue() {
        return value;
    }

    public long getVersion() {
        return version;
    }
}
