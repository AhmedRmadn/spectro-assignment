package com.systems.spectro_assignment.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class RolesValidator implements ConstraintValidator<ValidRoles, Set<String>> {

    private static final Set<String> ALLOWED_ROLES = Set.of("USER", "ADMIN");

    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) {
            return true;
        }
        return roles.stream().allMatch(ALLOWED_ROLES::contains);
    }
}
