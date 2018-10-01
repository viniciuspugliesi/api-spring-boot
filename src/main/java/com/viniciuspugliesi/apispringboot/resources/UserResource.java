package com.viniciuspugliesi.apispringboot.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viniciuspugliesi.apispringboot.domain.User;
import com.viniciuspugliesi.apispringboot.dto.UserCreateDTO;
import com.viniciuspugliesi.apispringboot.services.UserService;

@RestController
@RequestMapping(value="users")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseEntity<User> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
		User user = userService.create(userCreateDTO);
		return ResponseEntity.ok().body(user);
	}
}
