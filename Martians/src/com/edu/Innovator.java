package com.edu;

import java.util.*;

public final class Innovator<T> extends Martian<T> {

    private Set<Innovator<?>> children;
    private Innovator<?> parent;

    /**
     * get genetic code
     * @param genCode
     */
    protected Innovator(T genCode) {
        super(genCode);
    }

    /**
     * get parent
     * @return
     */
    @Override
    Innovator<?> getParent() {
        return parent;
    }

    /**
     * get only children
     * @return collecion
     */
    @Override
    Collection<? extends Innovator<?>> getChildren() {
        if (children == null) {
            children = new LinkedHashSet<>();
        }
        return Collections.unmodifiableCollection(children);
    }

    /**
     * get all descendants
     * @return collection of them
     */
    @Override
    Collection<? extends Innovator<?>> getDescendants() {
        var descendants = new LinkedHashSet<Innovator<?>>();
        for (var child : getChildren()) {
            descendants.add(child);
            descendants.addAll(child.getDescendants());
        }
        return Collections.unmodifiableCollection(descendants);
    }

    /**
     * seeking child with gen code
     * @param value
     * @return boolean
     */
    @Override
    public boolean hasChildWithValue(T value) {
        if (value == null)
            return false;

        for (var child : getChildren()) {
            if (child.getGenCode() != null && child.getGenCode().equals(value))
                return true;
        }

        return false;
    }


    /**
     * seekind descendant with gencode
     * @param value gencode
     * @return boolean
     */
    @Override
    public boolean hasDescendantWithValue(T value) {
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

    /**
     * setting new gene code
     */
    <U> boolean setGenCode(U newGene) {
        if (newGene == null) {
            return false;
        }

        Innovator<U> iv = new Innovator<>(newGene);

        if (getParent() != null && getParent().addChild(iv) && getParent().deleteChild(this)) {
            iv.setParent(getParent());
            setParent(null);
        }

        iv.setNewDescendants(getChildren());

        children = null;
        return true;
    }

    /**
     * setting new parent and deleting old
     * @param newParent
     * @return boolean
     */
    boolean setParent(Innovator<?> newParent) {
        if (newParent == null || newParent == this) {
            return false;
        }

        for (var child : this.getChildren()) {
            if (child.getDescendants().contains(newParent)) {
                return false;
            }
        }

        if (!newParent.getChildren().contains(this)) {
            newParent.addChild(this);
        }

        if (this.parent != newParent && this.parent != null) {
            this.parent.deleteChild(this);
        }

        parent = newParent;
        return true;
    }

    /**
     * changing all children and their children
     * @param newChildren
     * @return boolean if successfull
     */
    boolean setNewDescendants(Collection<? extends Innovator<?>> newChildren) {
        if (newChildren == null) {
            return false;
        }

        for (var child : newChildren) {
            if (child == null || child.getDescendants().contains(this)) {
                return false;
            }
        }

        children = (Set<Innovator<?>>) newChildren;
        return true;
    }

    /**
     * adding new child to node
     * @param newChild
     * @return boolean
     */
    boolean addChild(Innovator<?> newChild) {
        if (newChild == null || getChildren().contains(newChild)) {
            return false;
        }

        if (newChild.getDescendants().contains(this)) {
            return false;
        }

        if (children == null) {
            children = new LinkedHashSet<>();
        }
        children.add(newChild);

        if (newChild.getParent() != this) {
            newChild.setParent(this);
        }

        return true;
    }

    /**
     * deleting some child
     * @param delChild
     * @return child
     */
    boolean deleteChild(Innovator<?> delChild) {
        if (delChild == null) {
            return false;
        }

        for (var child : getChildren()
        ) {
            if (child == delChild) {
                children.remove(delChild);
                return true;
            }
        }

        return false;
    }
}
