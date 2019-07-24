package com.sistemagestaousuariosback.services.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemagestaousuariosback.domain.User;
import com.sistemagestaousuariosback.dto.LoginDTO;
import com.sistemagestaousuariosback.mails.DeleteAccountMail;
import com.sistemagestaousuariosback.repositories.UserRepository;
import com.sistemagestaousuariosback.security.JWTAuthenticationFilter;
import com.sistemagestaousuariosback.security.JWTUtil;
import com.sistemagestaousuariosback.security.SecurityContext;
import com.sistemagestaousuariosback.security.UserSecurity;
import com.sistemagestaousuariosback.security.exception.UnauthorizedException;
import com.sistemagestaousuariosback.services.TokenService;
import com.sistemagestaousuariosback.services.email.EmailService;
import com.sistemagestaousuariosback.services.exceptions.MailException;

@Service
public class AuthService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private JWTUtil jwtUtil;
	
	public UserSecurity login(LoginDTO loginDTO) {
		User user = userRepository.findByEmail(loginDTO.getEmail());
		
		UserSecurity userSecurity = jwtAuthenticationFilter.attemptAuthentication(user, loginDTO.getPassword());
		tokenService.createByAuthentication(userSecurity, user);
		
		return userSecurity;
	}

	public String refreshToken() {
		UserSecurity userSecurity = SecurityContext.getUserSecurity();
		User user = userRepository.findByEmail(userSecurity.getEmail());
		
		logout();
		
		String token = jwtUtil.generateToken(userSecurity.getEmail());
		userSecurity.setToken(token);
		tokenService.createByAuthentication(userSecurity, user);
		
		return token;
	}
	
	public void logout() {
		UserSecurity userSecurity = SecurityContext.getUserSecurity();
		tokenService.expiresAuthentication(userSecurity);
	}
	
	public UserSecurity loadUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UnauthorizedException("O usuário não existe no sistema.");
		}
		
		return new UserSecurity(user);
	}

	public void deleteAccount() {
		UserSecurity userSecurity = SecurityContext.getUserSecurity();
		User user = userRepository.findByEmail(userSecurity.getEmail());

		try {
			emailService.send(new DeleteAccountMail(user));
		} catch (MessagingException e) {
			throw new MailException(e);
		} finally {
			userRepository.delete(user);
		}
	}
}
