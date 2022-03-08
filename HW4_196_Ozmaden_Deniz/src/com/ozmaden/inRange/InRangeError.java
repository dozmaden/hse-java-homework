package com.ozmaden.inRange;
import com.ozmaden.ValidationError;

/*
Эррор, если значение выступило за рамки интервала
 */
class InRangeError implements ValidationError {

    final private Object obj;
    final private String path;
    final private long min;
    final private long max;

    final static String MESSAGE = "Must be in range: %s - %s";

    public InRangeError(final Object obj, final String path, final long min, final long max) {
        this.obj = obj;
        this.path = path;
        this.min = min;
        this.max = max;
    }

    @Override
    public String getMessage() {
        return String.format(MESSAGE, min, max);
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
