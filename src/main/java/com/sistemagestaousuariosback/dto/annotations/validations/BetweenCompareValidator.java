package com.sistemagestaousuariosback.dto.annotations.validations;

import java.text.ParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sistemagestaousuariosback.dto.annotations.BetweenCompare;
import com.sistemagestaousuariosback.lang.Between;

public class BetweenCompareValidator implements ConstraintValidator<BetweenCompare, Between<?>> {

	private String dateFormat;
	
	@Override
	public void initialize(BetweenCompare betweenCompare) {
		dateFormat = betweenCompare.dateFormat();
	}

	@Override
	public boolean isValid(Between<?> between, ConstraintValidatorContext context) {
		try {
			return between.validate(dateFormat);
		} catch (ParseException e) {
			return false;
		}
	}
}
