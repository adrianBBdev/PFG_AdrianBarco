package com.abb.pfg.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity associated with the job offer area.
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 * 
 */
@Entity
@NoArgsConstructor
@Data
public class Area {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@Column(length=50,nullable=false, unique=true)
	private String name;
	
	/**
	 * Default class constructor
	 * 
	 * @param name - area name
	 */
	public Area (String name) {
		this.name = name;
	}

}
