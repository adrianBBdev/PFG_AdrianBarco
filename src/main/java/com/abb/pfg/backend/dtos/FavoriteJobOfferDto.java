package com.abb.pfg.backend.dtos;

import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.entities.Student;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object associated with the Favorite job offer entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
public class FavoriteJobOfferDto {
	private Long id;
	private Student student;
	private JobOffer jobOffer;
}
