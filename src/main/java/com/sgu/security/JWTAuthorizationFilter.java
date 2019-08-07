package com.sgu.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sgu.security.exception.UnauthorizedException;
import com.sgu.services.TokenService;
import com.sgu.services.auth.AuthService;

@Component
public class JWTAuthorizationFilter {

    @Value("${security.jwt.header}")
    private String header;

    @Value("${security.jwt.prefix}")
    private String prefix;

    @Autowired
	private JWTUtil jwtUtil;

    @Autowired
	private AuthService authService;
	
    @Autowired
    private TokenService tokenService;

	public void doFilter(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String header = request.getHeader(this.header);
		
		if (header == null || ! header.startsWith(prefix + " ")) {
			throw new UnauthorizedException("O Authorization Header é obrigatório.");
		}
		
		UserSecurity userSecurity = getAuthentication(header.substring(7));
		SecurityContext.setUserSecurity(userSecurity);
	}

	public UserSecurity getAuthentication(String token) {
		if (! jwtUtil.tokenValid(token)) {
			throw new UnauthorizedException("O Authorization Header é inválido.");
		}

		UserSecurity userSecurity = authService.loadUserByEmail(jwtUtil.getEmail(token));
		userSecurity.setToken(token);
		
		if (! tokenService.existsAuthentication(userSecurity)) {
			throw new UnauthorizedException("O Authorization Header é inválido.");
		}
		
		return userSecurity;
	}
}
