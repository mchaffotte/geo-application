package fr.chaffotm.data.io.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = AlreadyUsedValidator.class)
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Documented
public @interface AlreadyUsed {

    String message() default "already used by another one";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
