package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.commons.RequestStatus;
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
	 * @param userId - user's username
	 * @param requestStatus - status of the request
	 * @param pageable - requests pageable
	 * @return List - list of requests
	 */
	@Query("SELECT rq FROM Request rq"
			+ " WHERE (:jobOfferId IS NULL or rq.jobOffer.id = :jobOfferId)"
			+ " AND (:userId IS NULL or rq.student.user.username = :userId"
			+ " OR :userId IS NULL or rq.jobOffer.company.user.username = :userId)"
			+ " AND (:name IS NULL or rq.student.name LIKE %:name%"
			+ " OR rq.jobOffer.company.name LIKE %:name%"
			+ " OR rq.jobOffer.title LIKE %:name%)"
			+ " AND (:requestStatus IS NULL or rq.requestStatus = :requestStatus)")
	public Page<Request> findByJobOffer_IdAndStudent_IdAndRequestStatus(@Param("jobOfferId") Long jobOfferId,
			@Param("userId") String userId, @Param("name") String name,
			@Param("requestStatus") RequestStatus requestStatus,
			Pageable pageable);
}