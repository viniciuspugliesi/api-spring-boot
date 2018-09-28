package com.viniciuspugliesi.apispringboot.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viniciuspugliesi.apispringboot.domain.User;

@RestController
@RequestMapping(value="users")
public class UserResource {

	@RequestMapping(value="",method=RequestMethod.GET)
	public String index() {
		User user = new User(null, "Vinicius", "viniciuspugliesi20@gmail.com");
		return user.toString();
	}
}
