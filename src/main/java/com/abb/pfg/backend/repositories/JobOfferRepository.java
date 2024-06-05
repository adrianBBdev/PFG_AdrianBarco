package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.entities.JobOffer;

/**
 * Job offers JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface JobOfferRepository extends JpaRepository<JobOffer,Long>{

	/**
	 * Finds all job offers with the specified filters
	 *
	 * @param city - location of the job offer
	 * @param modality - modality of the job offer
	 * @param areaId - area of the job offer
	 * @param minDuration - minimum duration of the job offer
	 * @param maxDuration - maximum duration of the job offer
	 * @param companyId - company id
	 * @return List - list of job offers
	 */
	@Query("SELECT jo FROM JobOffer jo"
			+ " WHERE (:city IS NULL or jo.city LIKE %:city%)"
			+ " AND (:modality IS NULL or jo.modality = :modality)"
			+ " AND (:area IS NULL or jo.area.name = :area)"
			+ " AND (:minDuration IS NULL or month(jo.endDate)-month(jo.startDate) >= :minDuration)"
			+ " AND (:maxDuration IS NULL or month(jo.endDate)-month(jo.startDate) <= :maxDuration)"
			+ " AND (:name IS NULL or jo.company.name LIKE %:name%"
			+ " OR jo.title LIKE %:name%)"
			+ " AND (:companyId IS NULL or jo.company.user.username = :companyId)")
	public Page<JobOffer> findByCityAndModalityAndArea_IdAndDurationAndCompany_Id(@Param("city") String city,
			@Param("modality") Modality modality, @Param("area") String area, @Param("minDuration") Integer minDuration,
			@Param("maxDuration") Integer maxDuration, @Param("companyId") String companyId, @Param("name") String name, Pageable pageable);
}