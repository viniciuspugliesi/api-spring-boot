package com.sistemagestaousuariosback.services;

import java.text.ParseException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemagestaousuariosback.domain.Token;
import com.sistemagestaousuariosback.domain.User;
import com.sistemagestaousuariosback.dto.RegisterDTO;
import com.sistemagestaousuariosback.dto.UserCreateDTO;
import com.sistemagestaousuariosback.dto.UserSearchDTO;
import com.sistemagestaousuariosback.dto.UserUpdateDTO;
import com.sistemagestaousuariosback.mails.RegistrationMail;
import com.sistemagestaousuariosback.repositories.UserRepository;
import com.sistemagestaousuariosback.security.SecurityContext;
import com.sistemagestaousuariosback.security.UserSecurity;
import com.sistemagestaousuariosback.services.email.EmailService;
import com.sistemagestaousuariosback.services.exceptions.DataIntegrityException;
import com.sistemagestaousuariosback.services.exceptions.InvalidParameterException;
import com.sistemagestaousuariosback.services.exceptions.MailException;
import com.sistemagestaousuariosback.services.exceptions.ObjectNotFountException;
import com.sistemagestaousuariosback.services.util.DateTimeUtil;
import com.sistemagestaousuariosback.services.util.PaginationUtil;

@Service
public class UserService {

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
}