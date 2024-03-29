package com.abb.pfg.backend.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity associated with the multimedia objects.
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="Multimedia")
@Data
@NoArgsConstructor
public class Multimedia {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;

	@Column(length=20,nullable=false)
	private String name;

	@ManyToOne
	private User user;

//	@Column(columnDefinition="mediumblob",nullable=false)
//	private byte[] file;

	@Column(length=255, nullable=false)
	private String filePath;

	/**
	 * Default class constructor
	 *
	 * @param name - multimedia name file
	 * @param user - multimedia owner
	 * @param filePath - multimedia file path
	 */
	public Multimedia(String name, User user, String filePath) {
		this.name = name;
		this.user = user;
		this.filePath = filePath;
	}
}
