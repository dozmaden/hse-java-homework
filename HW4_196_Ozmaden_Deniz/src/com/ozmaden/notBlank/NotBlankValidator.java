package com.ozmaden.notBlank;

import com.ozmaden.ValidationError;
import com.ozmaden.Validator;

import java.util.Set;

/*
Валидатор пустой строки
 */
public class NotBlankValidator implements Validator {

    final private String path;

    public NotBlankValidator(final String path) {
        this.path = path;
    }

    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            return Set.of();

        if (object instanceof String){
            if(((String)object).isBlank()){
                return Set.of(new NotBlankError(null, path));
            } else{
                return Set.of();
            }
        } else{
            throw new IllegalArgumentException("Must be String!");
        }
    }
}
