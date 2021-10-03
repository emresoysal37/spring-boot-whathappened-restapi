package com.pribas.ws.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	@NotNull
	@Size(max = 255)
	@Pattern(regexp = "^(.+)@(.+)$", message="{pribas.constraint.email.Pattern.message}")
	private String email;
	
	@NotNull
	@Size(min = 8, max = 255)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{pribas.constraint.password.Pattern.message}")
	private String password;
}
