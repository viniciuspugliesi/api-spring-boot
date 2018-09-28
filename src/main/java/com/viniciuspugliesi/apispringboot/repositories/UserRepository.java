package com.viniciuspugliesi.apispringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viniciuspugliesi.apispringboot.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
}
