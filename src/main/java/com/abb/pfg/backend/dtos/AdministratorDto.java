package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.User;

import lombok.Data;

/**
 * Data transfer object for the Administrator entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
public class AdministratorDto {
	
	private Long id;
	private User user;
}
