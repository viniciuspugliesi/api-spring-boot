package com.viniciuspugliesi.apispringboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

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

	@NotEmpty(message="A cidade é obrigatória.")
	private Integer city_id;
	
	public AddressDTO() {
	}

	public AddressDTO(String street, String number, String complement, String district, String cep, Integer city_id) {
		super();
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.cep = cep;
		this.city_id = city_id;
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
		return city_id;
	}

	public void setCityId(Integer city_id) {
		this.city_id = city_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddressDTO [street=");
		builder.append(street);
		builder.append(", number=");
		builder.append(number);
		builder.append(", complement=");
		builder.append(complement);
		builder.append(", district=");
		builder.append(district);
		builder.append(", cep=");
		builder.append(cep);
		builder.append(", city_id=");
		builder.append(city_id);
		builder.append("]");
		return builder.toString();
	}
	
	
}
