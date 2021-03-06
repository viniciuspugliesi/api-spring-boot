package com.sgu.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sgu.domain.User;
import com.sgu.dto.PaginationDTO;
import com.sgu.dto.UserCreateDTO;
import com.sgu.dto.UserDTO;
import com.sgu.dto.UserSearchDTO;
import com.sgu.dto.UserUpdateDTO;
import com.sgu.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<PaginationDTO<UserDTO>> search(@Valid @RequestBody UserSearchDTO userSearchDTO) {

		Page<User> users = userService.paginate(userSearchDTO);
		PaginationDTO<UserDTO> pagination = new PaginationDTO<UserDTO>(users);

		return ResponseEntity.ok().body(pagination);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> show(@PathVariable Integer id) {
		return ResponseEntity.ok().body(new UserDTO(userService.findById(id)));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
		return ResponseEntity.ok().body(new UserDTO(userService.create(userCreateDTO)));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
		return ResponseEntity.ok().body(new UserDTO(userService.update(id, userUpdateDTO)));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> update(@PathVariable Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}