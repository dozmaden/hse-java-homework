package com.ozmaden.notBlank;
import com.ozmaden.ValidationError;

/*
Эррор, если строка пустая
 */
class NotBlankError implements ValidationError {

    final private Object obj;
    final private String path;
    final static String MESSAGE = "String cannot be blank!";

    public NotBlankError(final Object obj, final String path) {
        this.obj = obj;
        this.path = path;
    }

    @Override
    public String getMessage() { return MESSAGE;
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
