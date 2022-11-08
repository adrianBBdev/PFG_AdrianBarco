/**
 * 
 */
package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import abb.pfg.main.entities.Company;
import abb.pfg.main.entities.JobOffer;
import abb.pfg.main.repositories.JobOfferRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@Service
public class JobOfferServiceImpl implements JobOfferService {
	
	final private JobOfferRepository jobOfferRepository;
	
	@Autowired
	public JobOfferServiceImpl(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

	@Override
	public List<JobOffer> listAllJobOffer() {
		log.trace("Call service method listAllJobOffers");
		return jobOfferRepository.findAll();
	}

	@Override
	public JobOffer getJobOffer(Long id) {
		log.trace("Call service method getJobOffer with params: {}", id);
		Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(id);
		JobOffer jobOfferDB = optionalJobOffer.isPresent() ? optionalJobOffer.get() : null;
		log.debug("Found jobOffer: {}" + jobOfferDB.getTitle());
		return jobOfferDB;
	}

	@Override
	public void createJobOffer(JobOffer jobOffer) {
		log.trace("Call service method createJobOffer with params: {}", jobOffer);
		jobOfferRepository.save(jobOffer);
	}

	@Override
	public void updateJobOffer(JobOffer jobOffer) {
		log.trace("Call service method updateJobOffer with params: {}", jobOffer);
		JobOffer jobOfferDB = getJobOffer(jobOffer.getJobOfferId());
		/*if(jobOfferDB == null) {
			return null;
		}*/
		jobOfferDB.setTitle(jobOffer.getTitle());
		jobOfferDB.setDescription(jobOffer.getDescription());
		jobOfferDB.setCompany(jobOffer.getCompany());
		jobOfferDB.setDuration(jobOffer.getDuration());
		jobOfferDB.setVacancies(jobOffer.getVacancies());
		jobOfferRepository.save(jobOfferDB);
	}

	@Override
	public void deleteJobOffer(Long id) {
		log.trace("Called service method deleteJobOffer with params: {}", id);
		if(jobOfferRepository.existsById(id)) {
			log.debug("Found jobOffer: {}", id);
			jobOfferRepository.deleteById(id);
		}
	}
	
	@Override
	public void deleteByCompany(Company company) {
		log.trace("Called service method deleteAllJobOffers with params: {}", company.getName());
		if(company == null) {
			jobOfferRepository.deleteAll();
		} else {
			jobOfferRepository.deleteByCompany(company);
		}
	}

	@Override
	public List<JobOffer> findByCompany(Company company) {
		log.trace("Called service method findByCompany with params: {}", company.getName());
		return jobOfferRepository.findByCompany(company);
	}
}
