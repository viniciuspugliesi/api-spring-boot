package com.sistemagestaousuariosback.controllers.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sistemagestaousuariosback.dto.RegisterDTO;
import com.sistemagestaousuariosback.dto.UserDTO;
import com.sistemagestaousuariosback.services.UserService;

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
