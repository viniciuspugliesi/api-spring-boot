package com.sistemagestaousuariosback.dto.annotations.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistemagestaousuariosback.dto.annotations.ValidToken;
import com.sistemagestaousuariosback.security.JWTUtil;

public class ValidTokenValidator implements ConstraintValidator<ValidToken, String> {

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return jwtUtil.tokenValid(value);
	}
}
