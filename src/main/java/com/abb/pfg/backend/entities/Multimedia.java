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
	
	@Column(length=50,nullable=false)
	private String name;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	/**
	 * Default class constructor
	 * 
	 * @param name - media's name
	 * @param user - media's user owner
	 */
	public Multimedia(String name, User user) {
		this.name = name;
		this.user = user;
	}
}
