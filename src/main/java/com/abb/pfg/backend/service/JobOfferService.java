package com.abb.pfg.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.dtos.JobOfferDto;
import com.abb.pfg.backend.entities.Area;
import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.repositories.JobOfferRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the job offers
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Slf4j
@Service
public class JobOfferService{

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private JobOfferRepository jobOfferRepository;

	/**
	 * Gets all job offers or filter by parameters
	 *
	 * @param city - job offers city
	 * @param modality - job offer modality
	 * @param areaId - area id
	 * @param minDuration - job offer minumin duration
	 * @param maxDuration - job offer maximum duration
	 * @param companyId - company id
	 * @param pageable - job offers pageable
	 * @return Page - list with the requested job offers
	 */
	public Page<JobOffer> listAllJobOffersByCityAndModalityAndAreaAndDurationAndCompany(String city,
			Modality modality, Area areaId, Integer minDuration,
			Integer maxDuration, Long companyId,Pageable pageable) {
		log.trace("Call service method listAllJobOffers()");
		var jobOffers = jobOfferRepository.findByCityAndModalityAndAreaAndDurationAndCompany_Id(city, modality,
				areaId, minDuration, maxDuration, companyId,pageable);
		log.debug("List of job offers found: {}", jobOffers.getNumberOfElements());
		return jobOffers;
	}

	/**
	 * Get the job offer with the requested id
	 *
	 * @param id - job offer id
	 * @return JobOfferDto - the requested chat
	 */
	public JobOfferDto getJobOffer(Long id) {
		log.trace("Call service method getJobOffer() with params: {}", id);
		var optionalJobOffer = jobOfferRepository.findById(id);
		var jobOffer = optionalJobOffer.isPresent() ? optionalJobOffer.get() : null;
		log.debug("Job offer found: {}", jobOffer.getId());
		return convertToDto(jobOffer);
	}

	/**
	 * Creates a new job offer
	 *
	 * @param jobOfferDto - the new job offer
	 */
	public void createJobOffer(JobOfferDto jobOfferDto) {
		log.trace("Call service method createJobOffer() with params: {}", jobOfferDto.getId());
		if(!jobOfferRepository.existsById(jobOfferDto.getId())) {
			log.debug("New jobOffer: {}", jobOfferDto.getId());
			jobOfferRepository.save(convertToEntity(jobOfferDto));
		} else {
			log.debug("The jobOffer already exists");
		}
	}

	/**
	 * Updates an existing job offer
	 *
	 * @param jobOfferDto - the job offer that will be updated
	 */
	public void updateJobOffer(JobOfferDto jobOfferDto) {
		log.trace("Call service method updateJobOffer() with params: {}", jobOfferDto.getId());
		if(jobOfferRepository.existsById(jobOfferDto.getId())) {
			log.debug("JobOffer updated: {}", jobOfferDto.getId());
			jobOfferRepository.save(convertToEntity(jobOfferDto));
		} else {
			log.debug("The jobOffer does not exists");
		}
	}

	/**
	 * Deletes all provided job offers
	 *
	 * @param jobOffers - list of job offers to delete
	 */
	public void deleteJobOffers(List<JobOffer> jobOffers) {
		log.trace("Call service method deleteJobOffers() with {} job offers", jobOffers.size());
		jobOfferRepository.deleteAllInBatch(jobOffers);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param jobOffer - entity to convert
	 * @return JobOfferDto - data transfer object converted
	 */
	private JobOfferDto convertToDto(JobOffer jobOffer) {
		var jobOfferDto = modelMapper.map(jobOffer, JobOfferDto.class);
		return jobOfferDto;
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param jobOfferDto - data transfer object to convert
	 * @return JobOffer - entity converted
	 */
	private JobOffer convertToEntity(JobOfferDto jobOfferDto) {
		var jobOffer = modelMapper.map(jobOfferDto, JobOffer.class);
		return jobOffer;
	}
}
