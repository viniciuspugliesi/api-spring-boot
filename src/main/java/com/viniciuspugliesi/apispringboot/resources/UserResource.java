package com.viniciuspugliesi.apispringboot.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="users")
public class UserResource {

	@RequestMapping(value="",method=RequestMethod.GET)
	public String index() {
		return "Rest funcionando.";
	}
}
