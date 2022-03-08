package com.ozmaden.positive;
import com.ozmaden.ValidationError;

/*
 Эррор, если значение отрицательное
 */
class PositiveError implements ValidationError {
    final private Object obj;
    final private String path;
    final static String MESSAGE = "Must be positive!";

    public PositiveError(final Object obj, final String path) {
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
