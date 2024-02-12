package com.abb.pfg.backend.dtos;

import java.util.Date;

import com.abb.pfg.backend.commons.RequestState;
import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.entities.Student;

import lombok.Data;

/**
 * Class which represents the data transfer object of the Request entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
public class RequestDto {
	private Long id;
	private String title;
	private String content;
	private Date timeStamp;
	private RequestState requestState;
	private Student student;
	private JobOffer jobOffer;
}
