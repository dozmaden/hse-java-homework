package com.ozmaden.notNull;
import com.ozmaden.ValidationError;
import com.ozmaden.Validator;

import java.util.Set;

/*
Валидатор для определения является ли объект Null
 */
public class NotNullValidator implements Validator {

    final private String path;

    public NotNullValidator(final String path) {
        this.path = path;
    }

    /*
    Чекаем на Null
     */
    @Override
    public Set<ValidationError> validate(Object object) {

        if (object == null){
            return Set.of(new NotNullError(null, path));
        } else{
            return Set.of();
        }
    }
}
