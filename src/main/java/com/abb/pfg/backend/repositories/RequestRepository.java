package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.commons.RequestState;
import com.abb.pfg.backend.entities.Request;

/**
 * Requests JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface RequestRepository extends JpaRepository<Request,Long>{
	
	/**
	 * Finds all requests from a specified job offer
	 * 
	 * @param jobOfferId - job offer id
	 * @param studentId - student id
	 * @param requestState - state of the request
	 * @param pageable - requests pageable
	 * @return List - list of requests
	 */
	@Query("SELECT rq FROM Request rq"
			+ " WHERE (:jobOfferId IS NULL or rq.jobOffer.id = :jobOfferId)"
			+ " AND (:studentId IS NULL or rq.student.id = :studentId)"
			+ " AND (:requestState IS NULL or rq.requestState = :requestState)")
	public Page<Request> findByJobOffer_IdAndStudent_IdAndRequestState(@Param("jobOfferId") Long jobOfferId, 
			@Param("studentId") Long studentId, 
			@Param("requestState") RequestState requestState,
			Pageable pageable);
}