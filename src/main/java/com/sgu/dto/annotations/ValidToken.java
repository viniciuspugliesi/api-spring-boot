package com.sgu.dto.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.sgu.dto.annotations.validations.ValidTokenValidator;

@Documented
@Constraint(validatedBy = ValidTokenValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidToken {
	
    public String message() default "";
    
    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}