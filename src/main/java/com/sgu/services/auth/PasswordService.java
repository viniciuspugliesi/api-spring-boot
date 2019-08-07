package com.sgu.services.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgu.domain.Token;
import com.sgu.domain.User;
import com.sgu.dto.ForgotPasswordDTO;
import com.sgu.dto.ResetPasswordDTO;
import com.sgu.mails.ForgotPasswordMail;
import com.sgu.mails.ResetPasswordMail;
import com.sgu.repositories.UserRepository;
import com.sgu.services.TokenService;
import com.sgu.services.email.EmailService;
import com.sgu.services.exceptions.InvalidParameterException;
import com.sgu.services.exceptions.MailException;
import com.sgu.services.util.DateTimeUtil;

@Service
public class PasswordService {

    @Value("${default.user.passwordExpiresAtDays}")
	private Integer passwordExpiresAtDays;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Async
	public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
		User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
		Token token = tokenService.createByRecoverPassword(user);

		try {
			emailService.send(new ForgotPasswordMail(user, token));
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
		Token token = tokenService.existsRecoverPassword(resetPasswordDTO.getToken());
		
		if (token == null) {
			throw new InvalidParameterException("token", "O token é inválido.");
		}
		
		User user = token.getUser();

		user.setPassword(bCrypt.encode(resetPasswordDTO.getPassword()));
		user.setPasswordExpiresAt(DateTimeUtil.getDateWithAddDays(passwordExpiresAtDays));
		userRepository.save(user);
		
		try {
			emailService.send(new ResetPasswordMail(user));
			tokenService.expires(resetPasswordDTO.getToken());
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}
}
