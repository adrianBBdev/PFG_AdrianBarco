package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.Role;

import lombok.Data;

/**
 * Data transfer object for the user entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
public class UserDto {

	private Long id;
	private String username;
	private String password;
	private Role role;
	private boolean enabled;

	public UserDto() {
		enabled = true;
	}

	public UserDto(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
		enabled = true;
	}
}
