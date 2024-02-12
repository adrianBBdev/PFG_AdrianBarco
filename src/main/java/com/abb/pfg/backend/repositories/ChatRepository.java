package com.abb.pfg.backend.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Chat;

/**
 * Class which represents the chat's JPA repository 
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface ChatRepository extends JpaRepository<Chat,Long>{
	
	/**
	 * Finds all chats from a specified student
	 * 
	 * @param compayId - company's id
	 * @param studentId - student's id
	 * @param pageable - chat's pageable
	 * @return Page - list of chats
	 */
	@Query("SELECT ch FROM Chat ch"
			+ " WHERE (:companyId IS NULL or ch.company.id = :companyId)"
			+ " OR (:studentId IS NULL or ch.student.id = :studentId)")
	public Page<Chat> findByCompany_IdAndStudent_Id(@Param("companyId") Long companyId, @Param("studentId") Long studentId,
			Pageable pageable);
}