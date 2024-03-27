package com.abb.pfg.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abb.pfg.backend.entities.Role;

/**
 * Roles JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * Finds the role from its name
	 *
	 * @param name - name associated with the role
	 * @return Role - the requested role
	 */
	public Role findByName(String name);
}