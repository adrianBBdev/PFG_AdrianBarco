package com.abb.pfg.backend.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

//	import com.abb.pfg.backend.commons.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity associated with the users.
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
	
	@Column(length=50,unique=true,nullable=false)
	@Email
	private String username;
	
	@Column(length=20,nullable=false)
	@Size(min=12)
	private String password;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Role role;
	
	private boolean enabled;
	
	/**
	 * Default class constructor
	 * 
	 * @param username - personal username
	 * @param password - personal password
	 * @param role - role associated with the user
	 */
	public User (String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = true;	
	}
}
