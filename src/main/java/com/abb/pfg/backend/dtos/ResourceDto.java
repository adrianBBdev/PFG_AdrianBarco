package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.JobOffer;

import lombok.Data;

/**
 * Data transfer object for a Resource entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
public class ResourceDto {

	private Long id;
	private String name;
	private String filePath;
	private JobOffer jobOffer;
}
