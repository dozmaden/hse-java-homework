package com.ozmaden;

public interface ValidationError {
    String getMessage();
    String getPath();
    Object getFailedValue();
}
