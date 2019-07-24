package com.sistemagestaousuariosback.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

    @Value("${security.jwt.expiration}")
    private Long expiration;

    @Value("${security.jwt.secret}")
    private String secret;

	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public Boolean tokenValid(String token) {
		Claims claims = getClaims(token);
		
		if (claims == null) {
			return false;
		}
		
		String email = claims.getSubject();
		Date expirationDate = claims.getExpiration();
		Date now = new Date(System.currentTimeMillis());
		
		return (email != null && expirationDate != null && now.before(expirationDate));
	}
	
	public String getEmail(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			return claims.getSubject();
		}
		
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();			
		} catch (Exception e) {
			return null;
		}
	}
}
