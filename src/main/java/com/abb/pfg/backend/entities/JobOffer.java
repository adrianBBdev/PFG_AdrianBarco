	package com.abb.pfg.backend.entities;


import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Positive;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

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
 * Entity associated with the job offers.
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

	@Column(length=50, nullable=false)
	private String title;

	@Column(length=500, nullable=false)
	private String description;

	@Positive
	@Column(nullable=false)
	private int vacancies;		//Number of vacancies
	
	@Positive
	@Column(nullable=false)
	private double salary;

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Modality modality;

	@ManyToOne
	private Area area;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private LocalDate startDate;

	@Column(nullable=false,columnDefinition="DATE")
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private LocalDate endDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date timeStamp;

	@Column(length=100,nullable=false)
	private String address;

	@Column(length=50,nullable=false)
	private String city;

	@Column(columnDefinition="tinyint(1) default 1")
	private boolean status;  //1 is open, 0 is closed

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Company company;

	/**
	 * Default class constructor
	 *
	 * @param title - job offer title
	 * @param description - job offer description
	 * @param vacancies - job offer vacancies
	 * @param duration - job offer duration
	 * @param address - address where the job is offered
	 * @param city - city where the job is offered
	 * @param company - company which offers the job
	 */
	public JobOffer(String title, String description, int vacancies, double salary,LocalDate startDate, LocalDate endDate,
			Modality modality, Area area, String address, String city, Company company) {
		this.title = title;
		this.description = description;
		this.vacancies = vacancies;
		this.salary = salary;
		this.startDate = startDate;
		this.endDate = endDate;
		this.modality = modality;
		this.area = area;
		this.timeStamp = Date.from(Instant.now());
		this.address = address;
		this.city = city;
		this.status = true;
		this.company = company;
	}
}
