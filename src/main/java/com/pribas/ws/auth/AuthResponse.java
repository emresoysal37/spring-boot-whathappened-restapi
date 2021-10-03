package com.pribas.ws.auth;

import com.pribas.ws.user.dto.UserDto;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String token;
	
	private UserDto user;
}
