package com.abb.pfg.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Administrator;
import com.abb.pfg.backend.entities.User;

/**
 * Administrator JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface AdminRepository extends JpaRepository<Administrator, Long> {
	
	@Query("SELECT ad FROM Administrator ad"
			+ " WHERE (ad.user.username = :username)")
	public Optional<Administrator> findByUsername(@Param("username") String username);
	
	public void deleteByUser(User users);
	
	public boolean existsByUser(User user);
}