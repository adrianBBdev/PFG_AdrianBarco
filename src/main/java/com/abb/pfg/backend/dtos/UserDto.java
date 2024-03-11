package com.abb.pfg.backend.dtos;

import java.util.Set;

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
	private Set<Role> roles;
	private boolean enabled;
}
