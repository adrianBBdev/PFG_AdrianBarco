package com.abb.pfg.backend.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.abb.pfg.backend.commons.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class which represents a user in the web app
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 * 
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;
	
	@Column(length=30,unique=true,nullable=false)
	@Email
	private String email;
	
	@Column(length=20,nullable=false)
	@Size(min=12)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public User (String email, String password, Role role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
