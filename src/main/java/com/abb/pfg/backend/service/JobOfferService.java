package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.config.JobOfferCreationDto;
import com.abb.pfg.backend.dtos.AreaDto;
import com.abb.pfg.backend.dtos.CompanyDto;
import com.abb.pfg.backend.dtos.JobOfferDto;
import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.repositories.JobOfferRepository;

import jakarta.transaction.Transactional;
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
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AreaService areaService;

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
			Modality modality, String area, Integer minDuration,
			Integer maxDuration, String companyId, String name, Pageable pageable) {
		var jobOffers = jobOfferRepository.findByCityAndModalityAndArea_IdAndDurationAndCompany_Id(city, modality,
				area, minDuration, maxDuration, companyId, name,pageable);
		return jobOffers;
	}
	
	/**
	 * Get the job offer with the requested offerCode
	 *
	 * @param offerCode - job offer offerCode
	 * @return JobOfferDto - the requested job offer
	 */
	public JobOfferDto getJobOfferById(Long id) {
		var optionalJobOffer = jobOfferRepository.findById(id);
		var jobOffer = optionalJobOffer.isPresent() ? optionalJobOffer.get() : null;
		return convertToDto(jobOffer);
	}

	/**
	 * Creates a new job offer
	 *
	 * @param jobOfferDto - the new job offer
	 */
	@Transactional
	public void createJobOffer(JobOfferCreationDto jobOfferCreationDto) {
		var companyDto = companyService.getCompanyByUsernameAndCif(jobOfferCreationDto.getCompany(), null);
		var areaDto = areaService.getArea(jobOfferCreationDto.getAreaName());
		var jobOfferDto = setJobOfferDto(jobOfferCreationDto, companyDto, areaDto);
		jobOfferRepository.save(convertToEntity(jobOfferDto));
	}
	
	/**
	 * Sets the job offer dto to save it
	 * 
	 * @param jobOfferCreationDto - dto used to create the offer
	 * @param companyDto - job offer's company
	 * @param areaDto - job offer's area
	 * @return JobOfferDto - job offer's dto
	 */
	private JobOfferDto setJobOfferDto(JobOfferCreationDto jobOfferCreationDto, CompanyDto companyDto, AreaDto areaDto) {
		var jobOfferDto = new JobOfferDto();
		jobOfferDto.setArea(areaService.convertToEntity(areaDto));
		jobOfferDto.setAddress(jobOfferCreationDto.getAddress());
		jobOfferDto.setCity(jobOfferCreationDto.getCity());
		jobOfferDto.setCompany(companyService.convertToEntity(companyDto));
		jobOfferDto.setDescription(jobOfferCreationDto.getDescription());
		jobOfferDto.setEndDate(jobOfferCreationDto.getEndDate());
		jobOfferDto.setStartDate(jobOfferCreationDto.getStartDate());
		jobOfferDto.setModality(jobOfferCreationDto.getModality());
		jobOfferDto.setTitle(jobOfferCreationDto.getTitle());
		jobOfferDto.setSalary(jobOfferCreationDto.getSalary());
		jobOfferDto.setVacancies(jobOfferCreationDto.getVacancies());
		return jobOfferDto;
	}

	/**
	 * Updates an existing job offer
	 *
	 * @param jobOfferDto - the job offer that will be updated
	 */
	public void updateJobOffer(JobOfferDto jobOfferDto) {
		if(jobOfferRepository.existsById(jobOfferDto.getId())) {
			jobOfferRepository.save(convertToEntity(jobOfferDto));
			return;
		}
		log.debug("The jobOffer does not exists");
	}

	/**
	 * Deletes all provided job offers
	 *
	 * @param jobOffers - list of job offers to delete
	 */
	public void deleteJobOffers(Long id) {
		jobOfferRepository.deleteById(id);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param jobOffer - entity to convert
	 * @return JobOfferDto - data transfer object converted
	 */
	private JobOfferDto convertToDto(JobOffer jobOffer) {
		try {
			return modelMapper.map(jobOffer, JobOfferDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param jobOfferDto - data transfer object to convert
	 * @return JobOffer - entity converted
	 */
	public JobOffer convertToEntity(JobOfferDto jobOfferDto) {
		try {
			return modelMapper.map(jobOfferDto, JobOffer.class);
		} catch(Exception e) {
			return null;
		}
	}
}
