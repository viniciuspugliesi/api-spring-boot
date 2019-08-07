package com.sgu.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgu.domain.Token;
import com.sgu.domain.User;
import com.sgu.dto.RegisterDTO;
import com.sgu.dto.UserCreateDTO;
import com.sgu.dto.UserSearchDTO;
import com.sgu.dto.UserUpdateDTO;
import com.sgu.mails.NotifyDeletedAccountMail;
import com.sgu.mails.NotifyInactiveAccountMail;
import com.sgu.mails.RegistrationMail;
import com.sgu.repositories.UserRepository;
import com.sgu.security.SecurityContext;
import com.sgu.security.UserSecurity;
import com.sgu.services.email.EmailService;
import com.sgu.services.exceptions.DataIntegrityException;
import com.sgu.services.exceptions.InvalidParameterException;
import com.sgu.services.exceptions.MailException;
import com.sgu.services.exceptions.ObjectNotFountException;
import com.sgu.services.util.DateTimeUtil;
import com.sgu.services.util.PaginationUtil;

@Service
public class UserService {
	public static Logger LOGGER = Logger.getLogger(UserService.class);

    @Value("${default.user.passwordExpiresAtDays}")
	private Integer passwordExpiresAtDays;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TokenService tokenService;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> 
			new ObjectNotFountException("Objecto com ID ["+ id + "] não encontrado.")
		);
	}

	public Page<User> paginate(UserSearchDTO userSearchDTO) {
		Integer page = PaginationUtil.normalizePage(userSearchDTO.getPage());
		Integer linesPerPage = userSearchDTO.getItemsPerPage();
		Direction direction = Direction.valueOf((userSearchDTO.getDirection())); 
		String orderBy = userSearchDTO.getOrderBy(); 
		
		Pageable pageable = PageRequest.of(page, linesPerPage, direction, orderBy);

		try {
			return userRepository.search(pageable, userSearchDTO.getFilters(), direction, orderBy);
		} catch (ParseException e) {
			throw new InvalidParameterException("createdBetween", "O campo ['createdBetween'] é inválido.");
		}
	}

	public User create(UserCreateDTO userCreateDTO) {
		User user = new User(userCreateDTO);
		
		user.setPassword(bCrypt.encode(userCreateDTO.getPassword()));
		
		if (user.getPasswordExpiresAt() == null) {
			user.setPasswordExpiresAt(DateTimeUtil.getDateWithAddDays(passwordExpiresAtDays));
		}
		
		userRepository.save(user);
		return sendMailRegistration(user);
	}

	public User create(RegisterDTO registerDTO) {
		User user = new User(registerDTO);
		
		user.setPassword(bCrypt.encode(registerDTO.getPassword()));
		user.setPasswordExpiresAt(DateTimeUtil.getDateWithAddDays(passwordExpiresAtDays));
		
		userRepository.save(user);
		return sendMailRegistration(user);
	}
	
	private User sendMailRegistration(User user) {
		Token token = tokenService.createByRegistration(user);

		try {
			emailService.send(new RegistrationMail(user, token));
		} catch (MessagingException e) {
			throw new MailException(e);
		}
		
		return user;
	}

	public User update(Integer id, UserUpdateDTO userUpdateDTO) {
		User user = findById(id);
		
		user.setName(userUpdateDTO.getName());
		
		return userRepository.save(user);
	}

	public void delete(Integer id) {
		User user = findById(id);
		UserSecurity userSecurity = SecurityContext.getUserSecurity();
		
		if (userSecurity.equals(user)) {
			throw new DataIntegrityException("Não é possível excluir seu próprio usuário.");
		}
		
		userRepository.delete(user);
	}

	public void handlesInactiveAccountsEightDaysAgo() {
		Date eightDaysAgo = DateTimeUtil.getDateWithAddDays(-8);
		Date startDate = DateTimeUtil.clearTime(eightDaysAgo);
		Date endDate = DateTimeUtil.maxTime(eightDaysAgo);
		
		List<User> users = userRepository.findAllByEmailVerifiedAtIsNullAndCreatedAtBetween(startDate, endDate);

		for (User user : users){
			Token token = tokenService.createByNotificationInactiveAccount(user);

			try {
				LOGGER.info("Notificando o usuário de conta inativa há 8 dias: " + user.getName());
				emailService.send(new NotifyInactiveAccountMail(user, token));
			} catch (MessagingException e) {
				throw new MailException(e);
			}
		}
	}

	public void handlesInactiveAccountsTenDaysAgo() {
		Date tenDaysAgo = DateTimeUtil.getDateWithAddDays(-10);
		Date startDate = DateTimeUtil.clearTime(tenDaysAgo);
		Date endDate = DateTimeUtil.maxTime(tenDaysAgo);
		
		List<User> users = userRepository.findAllByEmailVerifiedAtIsNullAndCreatedAtBetween(startDate, endDate);

		for (User user : users){
			Token token = tokenService.createByNotificationInactiveAccount(user);

			try {
				LOGGER.info("Notificando o usuário de conta que foi excluída: " + user.getName());
				emailService.send(new NotifyDeletedAccountMail(user, token));
			} catch (MessagingException e) {
				throw new MailException(e);
			} finally {
				userRepository.delete(user);
			}
		}
	}
}