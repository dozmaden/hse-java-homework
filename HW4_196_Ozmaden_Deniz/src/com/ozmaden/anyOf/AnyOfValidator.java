package com.ozmaden.anyOf;

import com.ozmaden.ValidationError;
import com.ozmaden.Validator;
import java.util.Arrays;
import java.util.Set;

/*
Валидатор, который проверяет является ли значение одним из позволенных
 */
public class AnyOfValidator implements Validator {

    final private String[] values;
    final private String path;

    public AnyOfValidator(final String[] values, final String path) {
        this.values = values;
        this.path = path;
    }

    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            return Set.of();

        if(object instanceof String){
            String target = (String)object;
            if (Arrays.asList(values).contains(target)){
                return Set.of();
            } else {
                return Set.of(new AnyOfError(object, path, values));
            }
        } else{
            throw new IllegalArgumentException("Object must be String!");
        }
    }
}
