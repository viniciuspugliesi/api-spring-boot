package com.sgu.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.sgu.dto.annotations.EqualFields;
import com.sgu.dto.annotations.Exists;
import com.sgu.dto.annotations.ValidToken;

@EqualFields(message = "A senha e sua confirmação não coincidem.", baseField = "password", matchField = "passwordConfirmation")
public class ResetPasswordDTO implements Serializable {
	private static final long serialVersionUID = 7139894243200707543L;
	
	@NotEmpty(message = "A senha é obrigatória.")
	private String password;

	@NotEmpty(message = "A confirmação da senha é obrigatória.")
	private String passwordConfirmation;
	
	@NotEmpty(message = "O token é obrigatório.")
	@ValidToken(message = "O token é inválido.")
	@Exists(message = "O token não existe no sistema.", table = "tokens", collumn = "token"/*, conditions = "email = ${email}"*/)
	private String token;
	
	public ResetPasswordDTO() {
	}

	public ResetPasswordDTO(String password, String passwordConfirmation, String token) {
		super();
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmation == null) ? 0 : passwordConfirmation.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResetPasswordDTO other = (ResetPasswordDTO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordConfirmation == null) {
			if (other.passwordConfirmation != null)
				return false;
		} else if (!passwordConfirmation.equals(other.passwordConfirmation))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
}
