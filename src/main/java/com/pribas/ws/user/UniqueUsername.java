package com.pribas.ws.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueUsernameValidator.class})
public @interface UniqueUsername {
	String message() default "{pribas.constraint.username.UniqueUsername.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
