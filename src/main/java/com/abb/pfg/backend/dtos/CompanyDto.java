package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.User;

import lombok.Data;

/**
 * Data transfer object for the Company entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
public class CompanyDto {
	
	private Long id;
	private String name;
	private String cif;
	private String country;
	private String description;
	private User user;
}
