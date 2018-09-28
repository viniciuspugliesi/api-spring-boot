package com.viniciuspugliesi.apispringboot.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserCreateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="O nome é obrigatório.")
	private String name;

	@NotEmpty(message="O email é obrigatório.")
	@Email(message="O email é inválido.")
	private String email;
	
	public UserCreateDTO() {
	}

	public UserCreateDTO(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
