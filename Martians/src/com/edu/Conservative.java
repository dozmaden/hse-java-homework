package com.edu;

import java.util.*;

public final class Conservative<T> extends Martian<T> {

    private final Set<Conservative<?>> children;
    private final Conservative<?> parent;

    /**
     * constructor with innovator
     * @param iv innovator
     * @param parent his parent
     */
    private Conservative(Innovator<T> iv, Conservative<?> parent) {
        super(iv.getGenCode());
        this.parent = parent;
        children = ConvertToCon(iv);
    }

    /**
     * for transforming children into cons
     * @param iv innovator
     */
    public Conservative(Innovator<T> iv) {
        super(iv.getGenCode());

        parent = null;

        children = ConvertToCon(iv);
    }

    /**
     *
     * @param iv innovator
     * @return converts iv to conservative
     */
    public Set<Conservative<?>> ConvertToCon(Innovator<T> iv) {
        Set<Conservative<?>> cons = new LinkedHashSet<>();
        for (var child : iv.getChildren()) {
            if (child != null) {
                cons.add(new Conservative<>(child, this));
            }
        }
        return cons;
    }


    /**
     * get parent of Martian
     * @return
     */
    @Override
    Conservative<?> getParent() {
        return parent;
    }

    /**
     * get only children
     * @return collection of children
     */
    @Override
    Collection<? extends Conservative<?>> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    /**
     * get all of the childen (and their childen)
     * @return collecion of them
     */
    @Override
    Collection<? extends Conservative<?>> getDescendants() {
        var descendants = new LinkedHashSet<Conservative<?>>();
        for (var child : getChildren()) {
            descendants.add(child);
            descendants.addAll(child.getDescendants());
        }
        return Collections.unmodifiableCollection(descendants);
    }

    /**
     * seeking genncode
     * @param value gencode
     * @return boolean true/false
     */
    @Override
    boolean hasChildWithValue(T value) {
        if (value == null)
            return false;

        for (var child : getChildren()) {
            if (child.getGenCode() != null && child.getGenCode().equals(value))
                return true;
        }

        return false;
    }

    /**
     * seeking value in children of children and childen
     * @param value gencode
     * @return boolean
     */
    @Override
    boolean hasDescendantWithValue(T value) {
        if (value == null) {
            return false;
        }

        for (var child : getDescendants()) {
            if (child.getGenCode() != null && child.getGenCode().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
