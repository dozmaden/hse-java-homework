package com.ozmaden.size;
import com.ozmaden.ValidationError;
import com.ozmaden.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/*
Валидатор размера
 */
public class SizeValidator implements Validator {

    /*
    класс разрешенных значений
     */
    private final Class<?>[] supportedClasses = new Class[] {List.class, Set.class, Map.class, String.class};

    // минимальное значение
    final private int min;

    // максимальное значение
    final private int max;

    // нынешний путь
    final private String path;

    /*
    Конструктор
     */
    public SizeValidator(final int min, final int max, final String path) {
        this.min = min;
        this.max = max;
        this.path = path;
    }

    /*
    Метод для определения размера
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            return Set.of();

        var dataStream = Stream.of(supportedClasses);

        if (dataStream.noneMatch(x -> x.isInstance(object))) {
            throw new IllegalArgumentException(String.format("Annotation does not support %s class!",
                    object.getClass()));
        }

        int size = 0;

        if(object instanceof String){
            size = ((String) object).length();
        } else if (object instanceof Map){
            size = ((Map<?, ?>)object).size();
        } else if (object instanceof Collection){
            size = ((Collection<?>)object).size();
        }

        if (size < min || size > max) {
            return Set.of(new SizeError(object, path));
        }

        return Set.of();
    }
}
