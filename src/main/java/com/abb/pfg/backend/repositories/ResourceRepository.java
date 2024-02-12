package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Resource;

/**
 * Class which represents the Resource's JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface ResourceRepository extends JpaRepository<Resource,Long>{
	
	@Query("SELECT rs FROM Resource rs"
			+ " WHERE (:jobOfferId IS NULL or rs.jobOffer.id = :jobOfferId)")
	public Page<Resource> findByJobOffer_Id(@Param("jobOfferId") Long jobOfferId, Pageable pageable);
}
