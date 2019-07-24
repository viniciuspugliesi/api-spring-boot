package com.sistemagestaousuariosback.dto.annotations.validations;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistemagestaousuariosback.dto.annotations.Unique;
import com.sistemagestaousuariosback.dto.annotations.validations.utils.NativeQueryUtil;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

	@Autowired
	private NativeQueryUtil nativeQueryUtil;
	
    private String table;
    
    private String collumn;
    
    private String conditions;

	@Override
	public void initialize(Unique unique) {
		table = unique.table();
		collumn = unique.collumn();
		conditions = unique.conditions();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<?> list = nativeQueryUtil.queryBuilder(table, collumn, value, conditions);
		
		return list.isEmpty();
	}

}