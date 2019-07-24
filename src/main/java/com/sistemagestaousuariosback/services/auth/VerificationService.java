package com.sistemagestaousuariosback.services.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemagestaousuariosback.domain.Token;
import com.sistemagestaousuariosback.domain.User;
import com.sistemagestaousuariosback.mails.RegistrationMail;
import com.sistemagestaousuariosback.repositories.UserRepository;
import com.sistemagestaousuariosback.security.SecurityContext;
import com.sistemagestaousuariosback.security.UserSecurity;
import com.sistemagestaousuariosback.security.exception.UnauthorizedException;
import com.sistemagestaousuariosback.services.TokenService;
import com.sistemagestaousuariosback.services.email.EmailService;
import com.sistemagestaousuariosback.services.exceptions.MailException;
import com.sistemagestaousuariosback.services.util.DateTimeUtil;

@Service
public class VerificationService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	public void verify(String token) {
		Token userToken = tokenService.existsVerification(token);
		if (userToken == null) {
			throw new UnauthorizedException("O token não existe.");
		}
		
		User user = userToken.getUser();
		user.setEmailVerifiedAt(DateTimeUtil.getDate());
		
		userRepository.save(user);
		tokenService.expires(token);
	}
	
	public void resend() {
		UserSecurity userSecurity = SecurityContext.getUserSecurity();
		User user = userRepository.findByEmail(userSecurity.getEmail());
		Token token = tokenService.createByRegistration(user);

		try {
			emailService.send(new RegistrationMail(user, token));
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}
}
