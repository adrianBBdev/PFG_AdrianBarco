package com.abb.pfg.backend.dtos;

import lombok.Data;

/**
 * Class which represents the data transfer object of the user entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
public class UserDto {
	
	private Long id;
	private String email;
	private String password;
}
