package com.pribas.ws.user.dto;

import com.pribas.ws.user.User;

import lombok.Data;

@Data
public class UserDto {
	
	private String username;
	
	private String email;
	
	public UserDto(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
	}
}
