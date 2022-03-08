package com.ozmaden;

import java.util.Set;

public interface Validator {
    Set<ValidationError> validate(Object object);
}
