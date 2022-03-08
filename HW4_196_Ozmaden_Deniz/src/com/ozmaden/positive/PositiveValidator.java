package com.ozmaden.positive;
import com.ozmaden.ValidationError;
import com.ozmaden.Validator;

import java.util.Set;
import java.util.stream.Stream;

/*
Валидатор для позитивных значений
 */
public class PositiveValidator implements Validator {

    /*
     Поддерживаемые классы
     */
    private final Class<?>[] supportedClasses = new Class[] {Byte.class, Short.class, Integer.class, Long.class};

    final private String path;

    public PositiveValidator(final String path) {
        this.path = path;
    }

    /*
    Метод для определения того, является ли значение положительным
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            return Set.of();

        if ( Stream.of(supportedClasses).noneMatch(x -> x.isInstance(object))) {
            throw new IllegalArgumentException(String.format("Annotation does not support %s class!",
                    object.getClass()));
        }
        if (((Number)object).doubleValue() < 0) {
            return Set.of(new PositiveError(object, path));
        }

        return Set.of();
    }
}
