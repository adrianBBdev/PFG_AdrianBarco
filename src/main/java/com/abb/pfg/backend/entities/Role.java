package com.abb.pfg.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity associated with the roles.
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Entity
@NoArgsConstructor
@Data
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable=false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Default class constrctor
     *
     * @param name - role name
     */
    public Role (String name) {
    	this.name = name;
    }
}
