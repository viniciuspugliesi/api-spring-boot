package com.viniciuspugliesi.apispringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciuspugliesi.apispringboot.domain.Address;
import com.viniciuspugliesi.apispringboot.domain.City;
import com.viniciuspugliesi.apispringboot.domain.User;
import com.viniciuspugliesi.apispringboot.dto.AddressDTO;
import com.viniciuspugliesi.apispringboot.dto.UserCreateDTO;
import com.viniciuspugliesi.apispringboot.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CityService cityService;

	public User create(UserCreateDTO userCreateDTO) {
		User user = createByDTO(userCreateDTO);
		userRepository.save(user);
		return user;
	}

	protected User createByDTO(UserCreateDTO userCreateDTO) {
		AddressDTO addressDTO = userCreateDTO.getAddress();
		City city = cityService.findById(addressDTO.getCityId());
		
		Address address = new Address(null, addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getComplement(), addressDTO.getDistrict(), addressDTO.getCep(), city);
		
		User user = new User(null, userCreateDTO.getName(), userCreateDTO.getEmail(), userCreateDTO.getPassword(),
				userCreateDTO.getCpf(), userCreateDTO.getPhone(), false, address);
		
		System.out.println(user);
		
		return user;
	}
}
