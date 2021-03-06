package com.sgu.controllers.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sgu.dto.RegisterDTO;
import com.sgu.dto.UserDTO;
import com.sgu.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO registerDTO) {
		return ResponseEntity.ok().body(new UserDTO(userService.create(registerDTO)));
	}
}
