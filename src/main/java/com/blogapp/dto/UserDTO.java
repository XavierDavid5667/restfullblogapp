package com.blogapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private int id;
	
	@NotEmpty
	@Size(min = 4,message = "User name must be greater than 4 characters")
	private String name;
	@Email(message = "Email is not valid!")
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String about;

}
