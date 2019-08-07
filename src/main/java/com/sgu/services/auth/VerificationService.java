package com.sgu.services.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgu.domain.Token;
import com.sgu.domain.User;
import com.sgu.mails.RegistrationMail;
import com.sgu.repositories.UserRepository;
import com.sgu.security.SecurityContext;
import com.sgu.security.UserSecurity;
import com.sgu.services.TokenService;
import com.sgu.services.email.EmailService;
import com.sgu.services.exceptions.InvalidParameterException;
import com.sgu.services.exceptions.MailException;
import com.sgu.services.util.DateTimeUtil;

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
			throw new InvalidParameterException("token", "O token não existe.");
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
