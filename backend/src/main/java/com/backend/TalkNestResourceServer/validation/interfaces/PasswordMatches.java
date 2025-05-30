package com.backend.TalkNestResourceServer.validation.interfaces;

import com.backend.TalkNestResourceServer.validation.validator.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE}) // ClassLevel
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {

    String message() default "Confirm password not matches!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
