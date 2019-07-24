package com.sistemagestaousuariosback.repositories.custom;

import java.text.ParseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.sistemagestaousuariosback.domain.User;
import com.sistemagestaousuariosback.dto.UserFilterDTO;

public interface UserCustomRepository {

	public Page<User> search(Pageable pageable, UserFilterDTO filters, Direction direction, String orderBy) throws ParseException;
}
