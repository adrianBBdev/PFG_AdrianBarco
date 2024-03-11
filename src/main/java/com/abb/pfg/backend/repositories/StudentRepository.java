package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Student;

/**
 * Students JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface StudentRepository extends JpaRepository<Student,Long> {
	
	/**
	 * Finds all students from the specified filters
	 * 
	 * @param name - student name
	 * @param studies - student studies
	 * @param userId - user id associated with the student
	 * @param pageable - students pageable
	 * @return Page<Students> - list of students
	 */
	@Query("SELECT st FROM Student st"
			+ " WHERE (:name IS NULL or st.name LIKE %:name%)"
			+ " AND (:studies IS NULL or st.studies LIKE %:studies%)"
			+ " AND (:userId IS NULL or st.user.id = :userId)")
	public Page<Student> findByNameAndStudiesAndUser_Id(@Param("name") String name, @Param("studies") String studies, 
			@Param("userId") Long userId, Pageable pageable);
}