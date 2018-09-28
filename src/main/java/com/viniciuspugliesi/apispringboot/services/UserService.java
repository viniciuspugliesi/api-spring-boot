package com.viniciuspugliesi.apispringboot.services;

import org.springframework.stereotype.Service;

import com.viniciuspugliesi.apispringboot.domain.User;

@Service
public class UserService {

	public User createInstanceUser(String name, String email) {
		User user = new User(null, name, email);
		return user;
	}
}
