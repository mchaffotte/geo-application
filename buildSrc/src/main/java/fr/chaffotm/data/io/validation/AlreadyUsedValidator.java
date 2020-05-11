package fr.chaffotm.data.io.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class AlreadyUsedValidator implements ConstraintValidator<AlreadyUsed, String> {

    private final Set<String> values;

    public AlreadyUsedValidator() {
        values = new HashSet<>();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean contains = values.contains(value);
        values.add(value);
        return !contains;
    }

}
