package com.abb.pfg.backend.dtos;

import java.time.Instant;
import java.util.Date;

import com.abb.pfg.backend.entities.Company;
import com.abb.pfg.backend.entities.Student;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the Chat entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
@NoArgsConstructor
public class ChatDto {
	private Long id;
	private Date timeStamp = Date.from(Instant.now());;
	private Company company;
	private Student student;
}
