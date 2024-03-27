package com.abb.pfg.backend.dtos;

import java.time.LocalDate;
import java.util.Date;

import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.entities.Area;
import com.abb.pfg.backend.entities.Company;

import lombok.Data;

/**
 * Data transfer object for the JobOffer entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
public class JobOfferDto {

	private Long id;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private Modality modality;
	private Area area;
	private int vacancies;
	private Date timeStamp;
	private String address;
	private String city;
	private boolean state;
	private Company company;
}
