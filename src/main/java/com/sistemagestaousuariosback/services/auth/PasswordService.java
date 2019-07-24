package com.sistemagestaousuariosback.services.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemagestaousuariosback.domain.Token;
import com.sistemagestaousuariosback.domain.User;
import com.sistemagestaousuariosback.dto.ForgotPasswordDTO;
import com.sistemagestaousuariosback.dto.ResetPasswordDTO;
import com.sistemagestaousuariosback.mails.ForgotPasswordMail;
import com.sistemagestaousuariosback.mails.ResetPasswordMail;
import com.sistemagestaousuariosback.repositories.UserRepository;
import com.sistemagestaousuariosback.services.TokenService;
import com.sistemagestaousuariosback.services.email.EmailService;
import com.sistemagestaousuariosback.services.exceptions.MailException;
import com.sistemagestaousuariosback.services.util.DateTimeUtil;

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
		User user = userRepository.findByEmail(resetPasswordDTO.getEmail());

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
