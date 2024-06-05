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
 * Represents a job offer that has been selected as favorite by a student
 * 
 * @author Adrián Barco Barona
 * @version. 
 *
 */
@Entity
@Table(name="FavoriteJobOffer")
@Data
@NoArgsConstructor
public class FavoriteJobOffer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Student student;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private JobOffer jobOffer;
	
	/**
	 * Default class constructor
	 * 
	 * @param student - student that selected the job offer as favorite
	 * @param jobOffer - job offer selected by the student
	 */
	public FavoriteJobOffer(Student student, JobOffer jobOffer) {
		this.student = student;
		this.jobOffer = jobOffer;
	}
}
