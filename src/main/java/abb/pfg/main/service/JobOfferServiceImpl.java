/**
 * 
 */
package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import abb.pfg.main.entitys.Company;
import abb.pfg.main.entitys.JobOffer;
import abb.pfg.main.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class JobOfferServiceImpl implements JobOfferService {
	
	final private JobOfferRepository jobOfferRepository;

	@Override
	public List<JobOffer> listAllJobOffer() {
		return jobOfferRepository.findAll();
	}

	@Override
	public JobOffer getJobOffer(Long id) {
		Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(id);
		JobOffer jobOfferDB = optionalJobOffer.isPresent() ? optionalJobOffer.get() : null;
		return jobOfferDB;
	}

	@Override
	public JobOffer createJobOffer(JobOffer jobOffer) {
		return jobOfferRepository.save(jobOffer);
	}

	@Override
	public JobOffer updateJobOffer(JobOffer jobOffer) {
		JobOffer jobOfferDB = getJobOffer(jobOffer.getJobOfferId());
		if(jobOfferDB == null) {
			return null;
		}
		jobOfferDB.setTitle(jobOffer.getTitle());
		jobOfferDB.setDescription(jobOffer.getDescription());
		jobOfferDB.setCompany(jobOffer.getCompany());
		jobOfferDB.setDuration(jobOffer.getDuration());
		jobOfferDB.setVacancies(jobOffer.getVacancies());
		return jobOfferRepository.save(jobOfferDB);
	}

	@Override
	public void deleteJobOffer(Long id) {
		log.trace("Called service method deleteJobOffer for id {}", id);
		if(jobOfferRepository.existsById(id)) {
			log.debug("Job Offer with id {} finded", id);
			jobOfferRepository.deleteById(id);
		}
	}

	@Override
	public List<JobOffer> findByCompany(Company company) {
		return jobOfferRepository.findByCompany(company);
	}

}
