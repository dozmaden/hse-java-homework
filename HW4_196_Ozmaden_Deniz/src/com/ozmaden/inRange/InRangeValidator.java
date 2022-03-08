package com.ozmaden.inRange;

import com.ozmaden.ValidationError;
import com.ozmaden.Validator;
import java.util.Set;
import java.util.stream.Stream;

/*
Валидатор для проверки является значение внутри интвервала
 */
public class InRangeValidator implements Validator {
    private final Class<?>[] supportedClasses = new Class[] {Byte.class, Short.class, Integer.class, Long.class};

    final private long min;
    final private long max;

    final private String path;

    public InRangeValidator(final long min, final long max, final String path) {
        this.min = min;
        this.max = max;
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

        double value = ((Number)object).doubleValue();
        if (value < min || value > max) {
            return Set.of(new InRangeError(object, path, min, max));
        }
        return Set.of();
    }

}
