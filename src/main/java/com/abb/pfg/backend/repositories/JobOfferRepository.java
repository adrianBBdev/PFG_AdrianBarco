package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.entities.Area;
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
			+ " WHERE (:city IS NULL or jo.city = :city)"
			+ " AND (:modality IS NULL or jo.modality = :modality)"
			+ " AND (:areaId IS NULL or jo.area.id = :areaId)"
			+ " AND (:minDuration IS NULL or month(jo.endDate)-month(jo.startDate) >= :minDuration)"
			+ " AND (:maxDuration IS NULL or month(jo.endDate)-month(jo.startDate) <= :maxDuration)"
			+ " AND (:companyId IS NULL or jo.company.id = :companyId)")
	public Page<JobOffer> findByCityAndModalityAndAreaAndDurationAndCompany_Id(@Param("city") String city, 
			@Param("modality") Modality modality, @Param("area") Area areaId, @Param("minDuration") Integer minDuration, 
			@Param("maxDuration") Integer maxDuration, @Param("companyId") Long companyId, Pageable pageable);
}