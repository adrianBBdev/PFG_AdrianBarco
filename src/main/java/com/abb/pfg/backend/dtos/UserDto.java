package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the user entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String username;
	private String password;
	private Role role;
	private boolean enabled = true;
}
