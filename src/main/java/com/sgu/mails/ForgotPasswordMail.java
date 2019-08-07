package com.sgu.mails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sgu.domain.Token;
import com.sgu.domain.User;

public class ForgotPasswordMail implements Mail {

	private User user;
	
	private Token token;
	
	public ForgotPasswordMail(User user, Token token) {
		this.user = user;
		this.token = token;
	}
	
	@Override
	public String getTemplate() {
		return "emails/forgot-password";
	}

	@Override
	public String getTo() {
		return user.getEmail();
	}

	@Override
	public String getSubject() {
		return "Recuperar senha";
	}

	@Override
	public Date getSentDate() {
		return new Date(System.currentTimeMillis());
	}
	
	@Override
	public Map<String, Object> getVariables() {
		Map<String, Object> variables = new HashMap<String, Object>();
		
		variables.put("user", user);
		variables.put("token", token);
		
		return variables;
	}
}
