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
	private String profilePicture;
	private User user;
}
