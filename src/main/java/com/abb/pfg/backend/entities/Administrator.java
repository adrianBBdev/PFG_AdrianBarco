package com.abb.pfg.backend.entities;


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
 * Entity associated with the administrator.
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Entity
@Table(name="Administrator")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Administrator{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;

	@OneToOne
	private User user;

	/**
	 * Default class constructor
	 *
	 * @param user - user associated to the administrator
	 */
	public Administrator(User user) {
		this.user = user;
	}
}
