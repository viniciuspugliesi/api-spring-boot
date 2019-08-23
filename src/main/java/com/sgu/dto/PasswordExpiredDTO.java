package com.sgu.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.sgu.dto.annotations.EqualFields;

@EqualFields(message = "A senha e sua confirmação não coincidem.", baseField = "password", matchField = "passwordConfirmation")
public class PasswordExpiredDTO implements Serializable {
	private static final long serialVersionUID = 7139894243200707543L;
	
	@NotEmpty(message = "A senha é obrigatória.")
	private String password;

	@NotEmpty(message = "A confirmação da senha é obrigatória.")
	private String passwordConfirmation;
	
	public PasswordExpiredDTO() {
	}

	public PasswordExpiredDTO(String password, String passwordConfirmation) {
		super();
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmation == null) ? 0 : passwordConfirmation.hashCode());
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
		PasswordExpiredDTO other = (PasswordExpiredDTO) obj;
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
		return true;
	}
}
