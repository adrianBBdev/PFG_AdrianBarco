package com.abb.pfg.backend.config;

import java.time.LocalDate;

import com.abb.pfg.backend.commons.Modality;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object which is used to create new job offers
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
public class JobOfferCreationDto {
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private Modality modality;
	private int vacancies;
	private double salary;
	private String address;
	private String city;
	private String areaName;
	private String company;
}
