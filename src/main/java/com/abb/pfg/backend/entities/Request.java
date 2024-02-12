package com.abb.pfg.backend.entities;


import java.time.Instant;
import java.util.Date;

import com.abb.pfg.backend.commons.RequestState;

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
 * Class which represents a request in the web app
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="Request")
@Data
@NoArgsConstructor
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@Column(length=20,nullable=false)
	private String title;
	
	@Column(length=300,nullable=false)
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private RequestState requestState;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private JobOffer jobOffer;
	
	/**
	 * Default class constructor
	 * @param title - request's title
	 * @param content - content of the request
	 * @param student - student involved in the request
	 * @param jobOffer - job offer related to the request
	 */
	public Request(String title, String content, Student student, JobOffer jobOffer) {
		
		this.title = title;
		this.content = content;
		this.student = student;
		this.jobOffer = jobOffer;
		this.timeStamp = Date.from(Instant.now());
		this.requestState = RequestState.PENDING;
	}
}
