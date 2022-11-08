package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entities.Company;
import abb.pfg.main.entities.JobOffer;

/**
 * Job offers' service interface. Shows which operations are available
 * 
 * @author Adrian Barco Barona
 *
 */

public interface JobOfferService {
	
	/**
	 * Gets all job offers
	 * 
	 * @return List of JobOfferDTO
	 */
	public List<JobOffer> listAllJobOffer();
	
	/**
	 * Gets the job offer from its id
	 * 
	 * @param id - the job offer's id
	 * @return JobOfferDTO - the selected job offer
	 */
	public JobOffer getJobOffer(Long id);
	
	/**
	 * Creates a new job offer 
	 * 
	 * @param jobOffer - the new job offer's parameters
	 */
	public void createJobOffer(JobOffer jobOffer);
	
	/**
	 * Updates an existing job offer.
	 * 
	 * @param jobOffer - the job offer's new parameters
	 */
	public void updateJobOffer(JobOffer jobOffer);
	
	/**
	 * Deletes a job offer from its id
	 * 
	 * @param id - the job offer's id
	 */
	public void deleteJobOffer(Long id);
	
	/**
	 * Deletes all job offers from a company, or every job offers
	 * 
	 * @param company - the company that owns the job offer
	 */
	public void deleteByCompany(Company company);
	
	/**
	 * Gets a set of job offers published by a specific company
	 * 
	 * @param company - the company which has published the job offers
	 * @return List of job offers
	 */
	public List<JobOffer> findByCompany(Company company);
}
