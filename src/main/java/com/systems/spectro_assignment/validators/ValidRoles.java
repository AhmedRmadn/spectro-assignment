package com.systems.spectro_assignment.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RolesValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoles {
    String message() default "Invalid role. Allowed values are USER or ADMIN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
