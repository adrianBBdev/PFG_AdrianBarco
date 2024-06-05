/**
 *
 */
package com.abb.pfg.backend.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
 * Entity associated with the resource objects.
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
	
	@Column(length=50,nullable=false)
	private String name;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private JobOffer jobOffer;
	
	public Resource (String name, JobOffer jobOffer) {
		this.name = name;
		this.jobOffer = jobOffer;
	}
}
