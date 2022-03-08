package com.ozmaden.notNull;
import com.ozmaden.ValidationError;

class NotNullError implements ValidationError {

    final private Object obj;
    final private String path;
    final static String MESSAGE =  "Object must be not null!";

    public NotNullError(final Object obj, final String path) {
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
