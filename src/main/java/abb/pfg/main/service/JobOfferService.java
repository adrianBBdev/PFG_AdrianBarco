package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entitys.Company;
import abb.pfg.main.entitys.JobOffer;

/**
 * Job offers service interface. Shows which operations are available
 * 
 * @author Adrian Barco Barona
 *
 */

public interface JobOfferService {
	
	/**
	 * Gets all job offers
	 * 
	 * @return List of job offers
	 */
	public List<JobOffer> listAllJobOffer();
	
	/**
	 * Gets the job offer from its id
	 * 
	 * @param id - the id of the job offer
	 * @return JobOffer - the selected job offer
	 */
	public JobOffer getJobOffer(Long id);
	
	/**
	 * Creates a new job offer 
	 * 
	 * @param jobOffer - the job offer which is going to be created
	 * @return JobOffer - the new job offer
	 */
	public JobOffer createJobOffer(JobOffer jobOffer);
	
	/**
	 * Updates the attributes of a job offer which already exists.
	 * 
	 * @param jobOffer - the job offer which is going to be updated
	 * @return JobOffer 
	 */
	public JobOffer updateJobOffer(JobOffer jobOffer);
	
	/**
	 * Deletes a job offer from its id
	 * 
	 * @param id - the id of the job offer which is going to be deleted
	 */
	public void deleteJobOffer(Long id);
	
	/**
	 * Gets a set of job offers published by a specific company
	 * 
	 * @param company - the company which has published the job offers
	 * @return List of job offers
	 */
	public List<JobOffer> findByCompany(Company company);
}
