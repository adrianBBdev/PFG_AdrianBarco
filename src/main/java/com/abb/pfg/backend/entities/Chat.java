package com.abb.pfg.backend.entities;



import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * Class which represents a chat between users in the web app
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="Chat")
@Data
@NoArgsConstructor
public class Chat {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date timeStamp;
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private Student student;
	
	/**
	 * Default class constructor
	 * 
	 * @param company - company involved in the chat
	 * @param student - student involved in the chat
	 */
	public Chat(Company company, Student student) {
		this.company = company;
		this.student = student;
		this.timeStamp = Date.from(Instant.now());
	}
}
