package com.viniciuspugliesi.apispringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciuspugliesi.apispringboot.domain.Address;
import com.viniciuspugliesi.apispringboot.domain.City;
import com.viniciuspugliesi.apispringboot.domain.User;
import com.viniciuspugliesi.apispringboot.dto.AddressDTO;
import com.viniciuspugliesi.apispringboot.dto.UserCreateDTO;
import com.viniciuspugliesi.apispringboot.dto.UserUpdateDTO;
import com.viniciuspugliesi.apispringboot.repositories.UserRepository;
import com.viniciuspugliesi.apispringboot.services.exceptions.ObjectNotFountException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CityService cityService;

	@Autowired
	private AddressService addressService;

	public User findById(Integer id) {
		Optional<User> obj =  userRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFountException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
	}
	
	public User create(UserCreateDTO userCreateDTO) {
		User user = createByDTO(userCreateDTO);
		userRepository.save(user);
		return user;
	}
	
	public User update(Integer id, UserUpdateDTO userUpdateDTO) {
		User user = findById(id);
		fill(user, userUpdateDTO);
		return user;
	}
	
	protected User fill(User user, UserUpdateDTO userUpdateDTO) {
		user.setEmail(userUpdateDTO.getEmail());
		user.setCpf(userUpdateDTO.getCpf());
		user.setName(userUpdateDTO.getName());
		user.setPhone(userUpdateDTO.getPhone());
		user.setPassword(userUpdateDTO.getPassword());
		return user;
	}

	protected User createByDTO(UserCreateDTO userCreateDTO) {
		AddressDTO addressDTO = userCreateDTO.getAddress();
		City city = cityService.findById(addressDTO.getCityId());
		
		Address address = addressService.create(new Address(null, addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getComplement(), addressDTO.getDistrict(), addressDTO.getCep(), city));
		
		User user = new User(null, userCreateDTO.getName(), userCreateDTO.getEmail(), userCreateDTO.getPassword(),
				userCreateDTO.getCpf(), userCreateDTO.getPhone(), false, address);
		
		return user;
	}
}
