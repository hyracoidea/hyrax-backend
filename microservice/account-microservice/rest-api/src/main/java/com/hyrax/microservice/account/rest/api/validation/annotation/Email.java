package com.hyrax.microservice.account.rest.api.validation.annotation;

import com.hyrax.microservice.account.rest.api.validation.annotation.validator.EmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailConstraintValidator.class)
public @interface Email {

    String regex() default "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    String message() default "Email must be consist of characters, digits, special characters and must contain only one @ symbol";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
