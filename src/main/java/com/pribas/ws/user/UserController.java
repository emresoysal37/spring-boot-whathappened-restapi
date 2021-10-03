package com.pribas.ws.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pribas.ws.shared.CurrentUser;
import com.pribas.ws.shared.GenericResponse;
import com.pribas.ws.user.dto.UserDto;
import com.pribas.ws.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("User created");
	}
	
	@GetMapping("/users")
	Page<UserDto> getUsers(Pageable page, @CurrentUser User user) {
		return userService.getUsers(page, user).map(UserDto::new);
	}
	
	@GetMapping("/users/{username}")
	UserDto getUser(@PathVariable String username) {
		User user = userService.getByUsername(username);
		return new UserDto(user);
	}
	
	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	UserDto updateUser(@Valid @RequestBody UserUpdateDto updatedUser, @PathVariable String username) {
		User user = userService.updateUser(username, updatedUser);
		return new UserDto(user);
	}
	
	@DeleteMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return new GenericResponse("User is removed");
	}
}
