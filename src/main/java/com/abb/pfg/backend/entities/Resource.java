/**
 * 
 */
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
 * Class which represents a job offers' resource in the web app 
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Entity
@Table(name="Resource")
@Data
@NoArgsConstructor
public class Resource {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@Column(length=20,nullable=false)
	private String name;

	@Column(length=255,nullable=false)
	private String filePath;
	
	@ManyToOne
	private JobOffer jobOffer;
	
	public Resource(Long id, String name, JobOffer jobOffer, String filePath) {
		this.id = id;
		this.name = name;
		this.jobOffer = jobOffer;
		this.filePath = filePath;
	}
}
