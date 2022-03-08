package com.ozmaden.size;
import com.ozmaden.ValidationError;

/*
Эррор размера
 */
class SizeError implements ValidationError {

    final private Object obj;
    final private String path;
    final static String MESSAGE = "Object is of incorrect size!";

    public SizeError(final Object obj, final String path) {
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
