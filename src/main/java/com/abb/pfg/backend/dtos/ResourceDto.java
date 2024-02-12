package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.JobOffer;

import lombok.Data;

/**
 * Class which represents the data transfer objetct of a resource entity
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
