package com.ozmaden.notEmpty;

import com.ozmaden.ValidationError;
import com.ozmaden.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/*
Валидатор того, является ли коллекция пустой
 */
public class NotEmptyValidator implements Validator {

    /*
     Поддерживаемые классы
     */
    private final Class<?>[] supportedClasses = new Class[] {List.class, Set.class, Map.class, String.class};

    final private String path;

    public NotEmptyValidator(final String path) {
        this.path = path;
    }

    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            return Set.of();

        var dataStream = Stream.of(supportedClasses);

        if (dataStream.noneMatch(x -> x.isInstance(object))) {
            throw new IllegalArgumentException(String.format("Annotation does not support %s class!",
                    object.getClass()));
        }

        boolean empty = false;
        if(object instanceof String && ((String)object).isEmpty()){
            empty = true;
        } else if (object instanceof Map && ((Map<?,?>)object).isEmpty()) {
            empty = true;
        } else if (object instanceof Collection && ((Collection<?>)object).isEmpty()){
            empty = true;
        }

        if (empty) {
            return Set.of(new NotEmptyError(object, path));
        }

        return Set.of();
    }
}
