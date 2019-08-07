package com.sgu.services.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgu.domain.User;
import com.sgu.dto.LoginDTO;
import com.sgu.mails.DeleteAccountMail;
import com.sgu.repositories.UserRepository;
import com.sgu.security.JWTAuthenticationFilter;
import com.sgu.security.JWTAuthorizationFilter;
import com.sgu.security.JWTUtil;
import com.sgu.security.SecurityContext;
import com.sgu.security.UserSecurity;
import com.sgu.security.exception.UnauthorizedException;
import com.sgu.services.TokenService;
import com.sgu.services.email.EmailService;
import com.sgu.services.exceptions.MailException;

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
	private JWTAuthorizationFilter jwtAuthorizationFilter; 
	
	@Autowired
	private JWTUtil jwtUtil;

	public Boolean check(String token) {
		try {
			jwtAuthorizationFilter.getAuthentication(token);
			return true;
		} catch (UnauthorizedException e) {
			return false;
		}
	}
	
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
