package com.ozmaden.negative;
import com.ozmaden.ValidationError;

/*
Эррор, если число является положительным
 */
class NegativeError implements ValidationError {

    final private Object obj;
    final private String path;
    final static String MESSAGE = "Object must be negative!";

    public NegativeError(final Object obj, final String path) {
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
