package com.abb.pfg.backend.entities;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class which represents the student rol in the web app
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="Student")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@NotBlank(message="El nombre no puede estar vacío")
	@Column(length=50,nullable=false)
	private String name;
	
	@Pattern(regexp="[0-9]{8}[A-Za-z]",message="El DNI debe tener 8 dígitos seguidos de una letra")
	@Column(length=9,nullable=false,unique=true)
	private String dni;
	
	@Size(max= 50, message="Los estudios deben tener como máximo 50 caracteres")
	@Column(length=50,nullable=true)
	private String studies;
	
	@Column(length=255,nullable=true)
	private String description;
	
	@OneToOne
	private User user;
	
	/**
	 * Default class constructor
	 * 
	 * @param id - student's id
	 * @param name - student's name
	 * @param dni - students's dni
	 * @param studies - degree that the student has completed or is finishing
	 * @param description - stduent's personal description
	 */
	public Student(String name, String dni, String studies, String description, User user) {
		this.name = name;
		this.dni = dni;
		this.studies = studies;
		this.description = description;
		this.user = user;
	}
}
