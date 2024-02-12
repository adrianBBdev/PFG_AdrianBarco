package com.abb.pfg.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abb.pfg.backend.entities.User;

/**
 * Class which represents the user's JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Finds the user from its username
	 * 
	 * @param username - user's username
	 * @return User - the requested user
	 */
	public User findByEmail(String email);
}
