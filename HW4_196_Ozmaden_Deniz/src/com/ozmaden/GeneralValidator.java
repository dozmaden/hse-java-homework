package com.ozmaden;

import com.ozmaden.anyOf.AnyOf;
import com.ozmaden.anyOf.AnyOfValidator;
import com.ozmaden.constrained.Constrained;
import com.ozmaden.inRange.InRange;
import com.ozmaden.inRange.InRangeValidator;
import com.ozmaden.negative.Negative;
import com.ozmaden.negative.NegativeValidator;
import com.ozmaden.notBlank.NotBlank;
import com.ozmaden.notBlank.NotBlankValidator;
import com.ozmaden.notEmpty.NotEmpty;
import com.ozmaden.notEmpty.NotEmptyValidator;
import com.ozmaden.notNull.NotNull;
import com.ozmaden.notNull.NotNullValidator;
import com.ozmaden.positive.Positive;
import com.ozmaden.positive.PositiveValidator;
import com.ozmaden.size.Size;
import com.ozmaden.size.SizeValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * Главный валидатор.
 * Будет рекурсивно находить нужные валидаторы и вернёт ошибки
 *
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */
public class GeneralValidator implements Validator {


    /*
    Использовал эррей дек для хранения пути.
    Путь к элементам будет динамически обновляться.
     */
    private final ArrayDeque<String> path = new ArrayDeque<>();

    /*
    Главый метод. Тут запускаем рекурсию и возвращаем результат.
     */
    @Override
    public Set<ValidationError> validate(Object object) {

        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null!");
        }

        Set<ValidationError> errors = new HashSet<>(validateLoop(object));

        return errors;
    }

    /*
    Метод рекурсии
     */
    private Set<ValidationError> validateLoop(Object obj) {
        if (obj == null || !isConstrained(obj)) {
            return Set.of();
        }

        Set<ValidationError> errors = new HashSet<>();

        // получаем поля объекта
        var fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {

            // логика добавления точки в путь
            if(path.size() > 0){
                path.addLast(String.format(".%s", field.getName()));
            } else{
                path.addLast(String.format("%s", field.getName()));
            }

            field.setAccessible(true);

            try {
                var fieldObj = field.get(obj);
                // если лист то особый случай, обрабатываем его
                if (fieldObj instanceof List<?>) {
                    errors.addAll(processList(field.getAnnotatedType(), fieldObj));
                }
                // получаем аннотации
                var annotations = field.getAnnotatedType().getDeclaredAnnotations();

                // собираем эрроры, идём в рекурсию
                errors.addAll(getErrorsByAnnotations(annotations, fieldObj));
                errors.addAll(validateLoop(fieldObj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            path.removeLast();
        }
        return errors;
    }


    /*
    Метод для обработки случая, когда элемент - Лист
     */
    private Set<ValidationError> processList(AnnotatedType annotatedType, Object fieldObj) {
        Set<ValidationError> validationErrorSet = new HashSet<>();

        var newAnnotatedType = ((AnnotatedParameterizedType) annotatedType).
                getAnnotatedActualTypeArguments()[0];


        // Аннотации дженерика
        var annotations = newAnnotatedType.getAnnotations();

        var fieldList = (List<?>) fieldObj;

        for (int i = 0; i < fieldList.size(); i++) {
            // идём по элементам листа
            path.addLast(String.format("[%d]", i));

            // обрабатываем ещё один вложенный лист
            if(fieldList.get(i) instanceof List<?>){
                validationErrorSet.addAll(processList(newAnnotatedType, fieldList.get(i)));
            }
            // Обрабатываем аннотации дженерика
            validationErrorSet.addAll(getErrorsByAnnotations(annotations, fieldList.get(i)));

            // Запускаем рекурсию
            validationErrorSet.addAll(validateLoop(fieldList.get(i)));

            path.removeLast();
            // удаляем путь, по которому шли.
        }
        return validationErrorSet;
    }

    /*
    Метод который проверят Constrained
     */
    private boolean isConstrained(Object obj) {
        return Stream.of(obj.getClass().getDeclaredAnnotations()).
                anyMatch(x -> x.annotationType().isAssignableFrom(Constrained.class));
    }

    // Метод, решаюший какой валидатор использовать в зависимости от аннотаций
    private Set<ValidationError> getErrorsByAnnotations(Annotation[] annotations, Object obj) {
        Set<ValidationError> errors = new HashSet<>();
        // превращаем массив нашего пути в строку
        String strPath = String.join("", path);

        // Чекаем каждую аннотацию
        for (Annotation ann : annotations) {
            Validator validator = null;

            if (ann instanceof AnyOf) {
                validator = new AnyOfValidator(((AnyOf) ann).value(),
                        String.join("", strPath));
            } else if (ann instanceof InRange) {
                validator = new InRangeValidator(((InRange) ann).min(),
                        ((InRange) ann).max(), strPath);
            } else if (ann instanceof Negative) {
                validator = new NegativeValidator(strPath);
            } else if (ann instanceof NotBlank) {
                validator = new NotBlankValidator(strPath);
            } else if (ann instanceof NotEmpty) {
                validator = new NotEmptyValidator(strPath);
            } else if (ann instanceof NotNull) {
                validator = new NotNullValidator(strPath);
            } else if (ann instanceof Positive) {
                validator = new PositiveValidator(strPath);
            } else if (ann instanceof Size) {
                validator = new SizeValidator(((Size) ann).min(), ((Size) ann).max(), strPath);
            }

            if(validator != null){
                errors.addAll(validator.validate(obj));
            }
        }

        return errors;
    }
}
