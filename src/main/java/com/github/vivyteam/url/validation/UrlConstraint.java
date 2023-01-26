package com.github.vivyteam.url.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlConstraint {
    String message() default "Invalid url";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}