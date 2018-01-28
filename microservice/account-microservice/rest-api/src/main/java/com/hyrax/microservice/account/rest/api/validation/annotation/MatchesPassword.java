package com.hyrax.microservice.account.rest.api.validation.annotation;

import com.hyrax.microservice.account.rest.api.validation.annotation.validator.MatchesPasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchesPasswordConstraintValidator.class)
public @interface MatchesPassword {

    String baseField() default "password";

    String matchField() default "passwordConfirmation";

    String message() default "The password fields must match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
