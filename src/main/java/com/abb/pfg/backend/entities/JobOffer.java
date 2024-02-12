	package com.abb.pfg.backend.entities;


import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Positive;

import com.abb.pfg.backend.commons.Area;
import com.abb.pfg.backend.commons.Modality;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class which represents a job offer in the web app
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="JobOffer")
@Data
@NoArgsConstructor
public class JobOffer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@Column(length=40, nullable=false)
	private String title;
	
	@Column(length=500, nullable=false)
	private String description;
	
	@Positive
	@Column(nullable=false)
	private int vacancies;		//Number of vacancies
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Modality modality;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private Area area;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private LocalDate startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private LocalDate endDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	
	@Column(length=30,nullable=false)
	private String address;
	
	@Column(length=20,nullable=false)
	private String city;
	
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean state;  //1 is open, 0 is closed
	
	@ManyToOne
	private Company company;
	
	/**
	 * Default class constructor
	 * 
	 * @param title - job offer's title
	 * @param description - job offer's description
	 * @param vacancies - job offer's vacancies
	 * @param duration - job offer's duration
	 * @param address - address where the job is offered
	 * @param city - city where the job is offered
	 * @param company - company which offers the job
	 */
	public JobOffer(String title, String description, int vacancies, LocalDate startDate, LocalDate endDate, 
			Modality modality, Area area, String address, String city, Company company) {
		this.title = title;
		this.description = description;
		this.vacancies = vacancies;
		this.startDate = startDate;
		this.endDate = endDate;
		this.modality = modality;
		this.area = area;
		this.timeStamp = Date.from(Instant.now());
		this.address = address;
		this.city = city;
		this.state = true;
		this.company = company;
	}
}
