package com.abb.pfg.backend.entities;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
 * Entity associated with the companies.
 *
 * @author Adrián Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="Company")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class Company{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;

	@NotBlank(message="El nombre no puede estar vacío")
	@Column(length=50,nullable=false,unique=true)
	private String name;

	@Pattern(regexp ="[A-Za-z0-9]{9}",message="El CIF debe tener 9 caracteres alfanuméricos")
	@Column(length=9,nullable=false,unique=true)
	private String cif;

	@NotBlank(message="El país no puede estar vacío")
	@Column(length=20,nullable=false)
	private String country;

	@Column(length=255,nullable=true)
	private String description;
	
	@Column(length=255,nullable=true)
	private String logo;

	@OneToOne
	private User user;

	/**
	 * Default class constructor
	 *
	 * @param name - company name
	 * @param cif - company cif
	 * @param country - company origin country
	 * @param description - company description
	 * @param user - user associated with the company
	 */
	public Company(String name, String cif, String country, String description, String logo,User user) {
		this.name = name;
		this.cif = cif;
		this.country = country;
		this.description = description;
		this.logo = logo;
		this.user = user;
	}
}
