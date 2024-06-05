package com.abb.pfg.backend.entities;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
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
 * Entity associated with the students
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

	@Size(max=100, message="Los estudios deben tener como máximo 100 caracteres")
	@Column(length=100,nullable=true)
	private String studies;

	@Column(length=500,nullable=true)
	private String description;

	@Column(length=9,nullable=true)
	@Size(min=9,max=9,message="Los números de teléfono deben tener 9 dígitos")
	private String phoneNumber;

	@Column(length=255,nullable=true)
	private String profilePicture;

	@OneToOne(cascade=CascadeType.REMOVE)
	private User user;

	/**
	 * Default class constructor
	 *
	 * @param name - student name
	 * @param dni - students DNI
	 * @param studies - degree that the student has completed or is finishing
	 * @param description - stduent personal description
	 * @param profilePicture - student profilePicture
	 * @param user - student auth user object
	 */
	public Student(String name, String dni, String studies, String phoneNumber,
			String description, String profilePicture, User user) {
		this.name = name;
		this.dni = dni;
		this.studies = studies;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.profilePicture = profilePicture;
		this.user = user;
	}
}
