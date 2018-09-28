package com.viniciuspugliesi.apispringboot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viniciuspugliesi.apispringboot.domain.User;
import com.viniciuspugliesi.apispringboot.services.UserService;

@RestController
@RequestMapping(value="users")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String index() {
		User user = userService.createInstanceUser("Vinicius", "vinicius_pugliesi@hotmail.com");
		return user.toString();
	}
}
