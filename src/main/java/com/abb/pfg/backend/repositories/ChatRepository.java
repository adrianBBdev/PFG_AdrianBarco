package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Chat;

/**
 * Chat JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface ChatRepository extends JpaRepository<Chat,Long>{

	/**
	 * Finds all chats from a specified student
	 *
	 * @param compayId - company id
	 * @param studentId - student id
	 * @param pageable - chat pageable
	 * @return Page - list of chats
	 */
	@Query("SELECT ch FROM Chat ch"
			+ " WHERE (:companyId IS NULL or ch.company.user.username = :companyId)"
			+ " AND (:studentId IS NULL or ch.student.user.username = :studentId)"
			+ " AND (:name IS NULL or ch.student.name LIKE %:name% OR ch.company.name LIKE %:name%)"
			+ " AND (:studentName IS NULL or ch.student.name LIKE %:studentName%)"
			+ " AND (:companyName IS NULL or ch.company.name LIKE %:companyName%)")
	public Page<Chat> findByCompany_IdAndStudent_IdAndCompanyNameAndStudentName(@Param("companyId") String companyId, @Param("studentId") String studentId,
			@Param("name") String name, @Param("studentName") String studentName ,@Param("companyName") String companyName,Pageable pageable);
}