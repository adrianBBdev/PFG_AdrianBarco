package com.abb.pfg.backend.entities;

import java.time.Instant;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

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
 * Entity associated with the chat.
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

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date timeStamp;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Company company;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
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
