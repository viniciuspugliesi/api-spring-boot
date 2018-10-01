package com.viniciuspugliesi.apispringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciuspugliesi.apispringboot.domain.Address;
import com.viniciuspugliesi.apispringboot.repositories.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public Address create(Address address) {
		return addressRepository.save(address);
	}
}
