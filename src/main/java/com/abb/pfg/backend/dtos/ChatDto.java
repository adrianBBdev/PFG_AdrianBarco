package com.abb.pfg.backend.dtos;

import java.util.Date;

import com.abb.pfg.backend.entities.Company;
import com.abb.pfg.backend.entities.Student;

import lombok.Data;

/**
 * Data transfer object for the Chat entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
public class ChatDto {

	private Long id;
	private Date timeStamp;
	private Company company;
	private Student student;
}
