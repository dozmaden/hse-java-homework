package com.ozmaden.anyOf;
import com.ozmaden.ValidationError;

import java.util.Arrays;

/*
 * Эррор, если значение не входило в values
 */
class AnyOfError implements ValidationError {

    final private Object obj;
    final private String path;
    final private String[] values;

    final static String MESSAGE = "Object must be one of this: %s";

    public AnyOfError(final Object obj, final String path, final String[] values) {
        this.obj = obj;
        this.path = path;
        this.values = values;
    }

    @Override
    public String getMessage() {
        return String.format(MESSAGE, Arrays.toString(values));
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
