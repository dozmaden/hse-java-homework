package com.ozmaden.negative;

import com.ozmaden.ValidationError;
import com.ozmaden.Validator;
import java.util.Set;
import java.util.stream.Stream;

/*
Валидатор для проверки является ли значение отрицательным
 */
public class NegativeValidator implements Validator {

    private Set<ValidationError> valerrors;

    private final Class<?>[] supportedClasses = new Class[] {Byte.class, Short.class, Integer.class, Long.class};

    final private String path;

    public NegativeValidator(final String path) {
        this.path = path;
    }

    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            return Set.of();

        if (Stream.of(supportedClasses).noneMatch(x -> x.isInstance(object))) {
            throw new IllegalArgumentException(String.format("Annotation does not support %s class!",
                    object.getClass()));
        }

        if (((Number)object).doubleValue() > 0) {
            return Set.of(new NegativeError(object, path));
        }

        return Set.of();
    }
}
