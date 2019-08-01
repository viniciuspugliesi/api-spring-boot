package com.sistemagestaousuariosback.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sistemagestaousuariosback.security.JWTAuthorizationFilter;

@Component
public class AuthenticatedInterceptor implements HandlerInterceptor {

	@Autowired
	private JWTAuthorizationFilter jwtAuthorizationFilter; 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (! request.getMethod().equals("OPTIONS")) {
			jwtAuthorizationFilter.doFilter(request, response, handler);	
		}
		
		return true;
	}
}