package com.edu;

import java.util.Collection;

abstract class Martian<T> {

    private final T genCode;

    /**
     * general constructor
     * @param genCode
     */
    protected Martian(T genCode) {
        if (genCode == null) {
            throw new IllegalArgumentException("GenCode is null!");
        }
        this.genCode = genCode;
    }

    /**
     * get parent of node
     * @return
     */
    abstract Martian<?> getParent();

    /**
     * get gen code of Martian
     * @return
     */
    T getGenCode() {
        return genCode;
    }

    /**
     * get all children
     * @return collection
     */
    abstract Collection<? extends Martian<?>> getChildren();

    /**
     * get all descendants
     * @return collection
     */
    abstract Collection<? extends Martian<?>> getDescendants();

    /**
     * seeking child with gen code
     * @param Value gencode
     * @return boolean
     */
    abstract boolean hasChildWithValue(T Value);

    /**
     * seeking descendant with gen code
     * @param Value gencode
     * @return boolean
     */
    abstract boolean hasDescendantWithValue(T Value);
}
