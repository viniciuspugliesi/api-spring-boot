package com.viniciuspugliesi.apispringboot.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.viniciuspugliesi.apispringboot.domain.Address;

public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="O logradouro é obrigatório.")
	private String street;

	@NotEmpty(message="O número é obrigatório.")
	private String number;

	private String complement;

	@NotEmpty(message="O bairro é obrigatório.")
	private String district;

	@NotEmpty(message="O CEP é obrigatório.")
	private String cep;

	@NotNull(message="A cidade é obrigatória.")
	@Min(value=0L, message="A cidade é inválida.")
	private Integer cityId;
	
	public AddressDTO() {
	}

	public AddressDTO(String street, String number, String complement, String district, String cep, Integer cityId) {
		super();
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.cep = cep;
		this.cityId = cityId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}
