package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Company;

/**
 * Company JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface CompanyRepository extends JpaRepository<Company,Long>{
	
	/**
	 * Finds a specified list of companies from different parameters
	 * 
	 * @param name - company name
	 * @param country - country of origin of the company
	 * @param userId - user id associated with the company
	 * @param pageable - specific pageable configured for the result
	 * @return Page<Company> - Specific list of companies that meet the above parameters
	 */
	@Query("SELECT cm FROM Company cm"
			+ " WHERE (:name IS NULL or cm.name LIKE %:name%)"
			+ " AND (:country IS NULL or cm.country = :country)"
			+ " AND (:userId IS NULL or cm.user.id = :userId)")
	public Page<Company> findByNameAndCountryAndUser_Id(@Param("name") String name, 
			@Param("country") String country, 
			@Param("userId") Long userId,
			Pageable pageable);
}