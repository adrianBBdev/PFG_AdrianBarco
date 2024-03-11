package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.User;

import lombok.Data;

/**
 * Data transfer object for Mutimedia entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
public class MultimediaDto {
	private Long id;
	private String name;
	private User user;
	private String filePath;

}
