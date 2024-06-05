package com.abb.pfg.backend.entities;

import java.time.Instant;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.abb.pfg.backend.commons.RequestStatus;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity associated with the requests
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Entity
@Table(name="Request")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;

	@Column(length=500,nullable=false)
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date timeStamp;

	@Enumerated(EnumType.STRING)
	@Column(length=15,nullable=false)
	private RequestStatus requestStatus;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Student student;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private JobOffer jobOffer;

	/**
	 * Default class constructor
	 *
	 * @param title - request title
	 * @param content - content of the request
	 * @param student - student involved in the request
	 * @param jobOffer - job offer related to the request
	 */
	public Request(String content, Student student, JobOffer jobOffer) {
		this.content = content;
		this.student = student;
		this.jobOffer = jobOffer;
		this.timeStamp = Date.from(Instant.now());
		this.requestStatus = RequestStatus.PENDING;
	}
}
