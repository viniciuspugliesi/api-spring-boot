package com.sgu.controllers.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sgu.domain.User;
import com.sgu.dto.ForgotPasswordDTO;
import com.sgu.dto.PasswordExpiredDTO;
import com.sgu.dto.ResetPasswordDTO;
import com.sgu.dto.UserDTO;
import com.sgu.services.auth.PasswordService;

@RestController
@RequestMapping(value = "/auth")
public class PasswordController {

	@Autowired
	private PasswordService passwordService;
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
		passwordService.forgotPassword(forgotPasswordDTO);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
		passwordService.resetPassword(resetPasswordDTO);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/password-expired", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> passwordExpired(@Valid @RequestBody PasswordExpiredDTO passwordExpiredDTO) {
		User user = passwordService.passwordExpired(passwordExpiredDTO);
		return ResponseEntity.ok(new UserDTO(user));
	}
}
