package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the Company entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
@NoArgsConstructor
public class CompanyDto {

	private Long id;
	private String name;
	private String cif;
	private String country;
	private String description;
	private String logo;
	private User user;

	public CompanyDto(String name, String cif, String country, String description, String logo, User user) {
		this.name = name;
		this.cif = cif;
		this.country = country;
		this.description = description;
		this.logo = logo;
		this.user = user;
	}
}
