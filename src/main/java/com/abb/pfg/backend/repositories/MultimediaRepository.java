package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Multimedia;

/**
 * Multimedias JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface MultimediaRepository extends JpaRepository<Multimedia,Long>{
	
	/**
	 * Finds all multimedia objects from a specified user
	 * 
	 * @param id - user id
	 * @param pageable - multimedia objects pageable
	 * @return Page - list of multimedia
	 */
	@Query("SELECT mt FROM Multimedia mt"
			+ " WHERE (:userId IS NULL or mt.user.id = :userId)")
	public Page<Multimedia> findByUser_Id(@Param("userId") Long userId, Pageable pageable);
}