package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Resource;

/**
 * Resources JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface ResourceRepository extends JpaRepository<Resource,Long>{

	/**
	 * Finds all resources from the specified filters
	 *
	 * @param jobOfferId - job offer id
	 * @param pageable - job offers pageable
	 * @return Page<Resource> - list of resources
	 */
	@Query("SELECT rs FROM Resource rs"
			+ " WHERE (:jobOfferId IS NULL or rs.jobOffer.id = :jobOfferId)"
			+ " AND (:name IS NULL or rs.name LIKE %:name%)")
	public Page<Resource> findByJobOfferIdAndName(@Param("jobOfferId") Long jobOfferId,
			@Param("name") String name, Pageable pageable);
}
