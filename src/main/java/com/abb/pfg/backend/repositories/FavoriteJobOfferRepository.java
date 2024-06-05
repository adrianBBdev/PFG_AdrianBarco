package com.abb.pfg.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.FavoriteJobOffer;

/**
 * JPA repository associated with the favorite job offer entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface FavoriteJobOfferRepository extends JpaRepository<FavoriteJobOffer,Long>{
	
	/**
	 * Finds all favorite job offers from a specified student
	 *
	 * @param studentId - student id
	 * @param pageable - requests pageable
	 * @return List - list of requests
	 */
	@Query("SELECT fj FROM FavoriteJobOffer fj"
			+ " WHERE (:username IS NULL or fj.student.user.username = :username)")
	public Page<FavoriteJobOffer> findByStudent_Username(@Param("username") String username,
			Pageable pageable);
	
	/**
	 * Finds a favorite job offer from a specified student and offer code
	 * 
	 * @param username - student id
	 * @param offerCode - specified offer code
	 * @return FavoriteJobOffer - the requested favorite job offer
	 */
	@Query("SELECT fj FROM FavoriteJobOffer fj"
			+ " WHERE (:username IS NULL or fj.student.user.username = :username)"
			+ " AND (:jobOfferId IS NULL or fj.jobOffer.id = :jobOfferId)")
	public Optional<FavoriteJobOffer> findByStudent_UsernameAndJobOfferId(@Param("username") String username, 
			@Param("jobOfferId") Long id);
}
