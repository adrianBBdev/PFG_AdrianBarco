package com.abb.pfg.backend.dtos;

import java.time.Instant;
import java.util.Date;

import com.abb.pfg.backend.commons.RequestStatus;
import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.entities.Student;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the Request entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
public class RequestDto {
	private Long id;
	private String content;
	private Date timeStamp = Date.from(Instant.now());
	private RequestStatus requestStatus;
	private Student student;
	private JobOffer jobOffer;
}
