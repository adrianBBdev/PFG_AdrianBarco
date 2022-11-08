package abb.pfg.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entities.Company;
import abb.pfg.main.entities.JobOffer;

/**
 * JPA repository for job offers
 * 
 * @author Adrian Barco Barona
 *
 */
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>{
	public List<JobOffer> findByCompany (Company company);
	public void deleteByCompany(Company company);
}
