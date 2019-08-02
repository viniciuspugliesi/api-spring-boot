package com.sistemagestaousuariosback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemagestaousuariosback.domain.Token;
import com.sistemagestaousuariosback.domain.User;
import com.sistemagestaousuariosback.domain.enuns.TokenType;
import com.sistemagestaousuariosback.repositories.TokenRepository;
import com.sistemagestaousuariosback.security.JWTUtil;
import com.sistemagestaousuariosback.security.UserSecurity;
import com.sistemagestaousuariosback.security.exception.UnauthorizedException;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private JWTUtil jwtUtil;
	
	public Token createByRecoverPassword(User user) {
		Token token = new Token(jwtUtil.generateToken(user.getEmail()), TokenType.RECOVER_PASSWORD, user);
		return tokenRepository.save(token);
	}

	public Token createByRegistration(User user) {
		Token token = new Token(jwtUtil.generateToken(user.getEmail()), TokenType.REGISTRATION, user);
		return tokenRepository.save(token);
	}
	
	public Token createByAuthentication(UserSecurity userSecurity, User user) {
		Token token = new Token(userSecurity.getToken(), TokenType.AUTHENTICATION, user);
		return tokenRepository.save(token);
	}

	public boolean existsAuthentication(UserSecurity userSecurity) {
		return tokenRepository.countByTokenAndTypeAndUserId(userSecurity.getToken(), TokenType.AUTHENTICATION.value(), userSecurity.getId()) > 0;
	}

	public Token existsVerification(String token) {
		return tokenRepository.findByTokenAndType(token, TokenType.REGISTRATION.value());
	}

	public Token existsRecoverPassword(String token) {
		return tokenRepository.findByTokenAndType(token, TokenType.RECOVER_PASSWORD.value());
	}
	
	public void expiresAuthentication(UserSecurity userSecurity) {
		Token token = tokenRepository.findByTokenAndTypeAndUserId(userSecurity.getToken(), TokenType.AUTHENTICATION.value(), userSecurity.getId());
		tokenRepository.delete(token);
	}

	public void expires(String token) {
		Token tokenEntity = tokenRepository.findByToken(token);
		if (tokenEntity == null) {
			throw new UnauthorizedException("O token é inválido ou não existe no sistema.");
		}
		
		tokenRepository.delete(tokenRepository.findByToken(token));
	}
}
