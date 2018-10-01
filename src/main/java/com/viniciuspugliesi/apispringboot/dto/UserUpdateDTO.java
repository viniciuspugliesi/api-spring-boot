package com.viniciuspugliesi.apispringboot.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="O nome é obrigatório.")
	private String name;

	@NotEmpty(message="O email é obrigatório.")
	@Email(message="O email é inválido.")
	private String email;

	@NotEmpty(message="A senha é obrigatória.")
	private String password;

	@NotEmpty(message="O CPF é obrigatório.")
	private String cpf;

	@NotEmpty(message="O telefone é obrigatório.")
	private String phone;
	
    @Valid
	private AddressDTO address;
	
	public UserUpdateDTO() {
	}

	public UserUpdateDTO(String name, String email, String password, String cpf, String phone, AddressDTO address) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.cpf = cpf;
		this.phone = phone;
		this.address = address;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	
	
}
