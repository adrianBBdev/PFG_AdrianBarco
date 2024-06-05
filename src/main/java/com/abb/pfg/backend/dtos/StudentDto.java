package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the Student entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
@NoArgsConstructor
public class StudentDto {
	private Long id;
	private String name;
	private String dni;
	private String studies;
	private String description;
	private String phoneNumber;
	private String profilePicture;
	private User user;
}
