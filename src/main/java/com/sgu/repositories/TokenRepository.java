package com.sgu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgu.domain.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

	public Integer countByTokenAndTypeAndUserId(String token, Integer type, Integer user_id);

	public Token findByTokenAndTypeAndUserId(String token, Integer type, Integer user_id);

	public Token findByToken(String token);

	public Token findByTokenAndType(String token, Integer type);
}
