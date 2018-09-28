package com.viniciuspugliesi.apispringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciuspugliesi.apispringboot.domain.City;
import com.viniciuspugliesi.apispringboot.repositories.CityRepository;
import com.viniciuspugliesi.apispringboot.services.exceptions.ObjectNotFountException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public City findById(Integer id) {
		Optional<City> obj =  cityRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFountException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + City.class.getName()));
	}
}
