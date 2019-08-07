package com.sgu.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sgu.domain.User;
import com.sgu.repositories.custom.UserCustomRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserCustomRepository {

	@Transactional(readOnly = true)
	public User findByEmail(String email);

	public List<User> findAllByEmailVerifiedAtIsNullAndCreatedAtBetween(Date startDate, Date endDate);
}