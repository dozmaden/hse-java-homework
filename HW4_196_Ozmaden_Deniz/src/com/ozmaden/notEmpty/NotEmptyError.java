package com.ozmaden.notEmpty;

import com.ozmaden.ValidationError;

/*
Эррор, если коллекция пустая
 */
class NotEmptyError implements ValidationError {

    final private Object obj;
    final private String path;
    final static String MESSAGE = "Object cannot not empty!";

    public NotEmptyError(final Object obj, final String path) {
        this.obj = obj;
        this.path = path;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return obj;
    }
}
