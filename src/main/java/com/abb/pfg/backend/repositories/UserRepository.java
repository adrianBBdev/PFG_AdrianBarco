package com.abb.pfg.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abb.pfg.backend.entities.User;

/**
 * Users JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Finds the user from its username
	 * 
	 * @param username - username associated with the users
	 * @return User - the requested user
	 */
	public User findByUsername(String username);
}
