package com.systems.spectro_assignment.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinAgeValidator.class)
@Documented
public @interface MinAge {
    String message() default "Age must be at least {value} years";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int value();
}
