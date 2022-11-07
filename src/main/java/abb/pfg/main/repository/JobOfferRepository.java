package abb.pfg.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entitys.Company;
import abb.pfg.main.entitys.JobOffer;

/**
 * JPA repository for job offers
 * 
 * @author Adrian Barco Barona
 *
 */
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>{
	public List<JobOffer> findByCompany (Company company);
}
