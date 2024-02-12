package com.abb.pfg.backend.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Area;
import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.dtos.JobOfferDto;
import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.service.JobOfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which represnets the job offers' controller
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.JobOffers.PATH)
@Tag(name="JobOfferController", description="Controller to manage the jobOffers of the web app")
public class JobOfferController {
	
	@Autowired
	private JobOfferService jobOfferService;
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE','ROLE_INVITADO')")
	@Operation(method="GET", description="Gets all job offers or filter by parameters")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<JobOffer> getAllJobOffersByCityAndModalityAndAreaAndDurationAndCompany(@RequestParam(required=false) String city, 
			@RequestParam(required=false) Modality modality, 
			@RequestParam(required=false) Area area, 
			@RequestParam(required=false) Integer minDuration, 
			@RequestParam(required=false) Integer maxDuration,
			@RequestParam(required=false) Long companyId,
			@RequestParam(defaultValue="0") int page, 
			@RequestParam(defaultValue="3")int size) {
		log.trace("Call controller method getAllJobOffers()");
		var pageable = PageRequest.of(page, size);
		var jobOfferPage = jobOfferService.listAllJobOffersByCityAndModalityAndAreaAndDurationAndCompany(city, 
				modality, area, minDuration, maxDuration, companyId,pageable);
		return jobOfferPage;
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE','ROLE_INVITADO')")
	@Operation(method="GET", description="Gets a specific job offer from its id")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public JobOfferDto getJobOffer(@PathVariable Long id) {
		log.trace("Call controller method getJobOffer() with params: {}", id);
		return jobOfferService.getJobOffer(id);
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA')")
	@Operation(method="POST", description="Creates a new job offer")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createJobOffer(@RequestBody JobOfferDto jobOfferDto) {
		log.trace("Call controller method createJobOffer() with params: {}", jobOfferDto.getId());
		jobOfferService.createJobOffer(jobOfferDto);
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA')")
	@Operation(method="PUT", description="Updates an existing job offer")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateJobOffer(@RequestBody JobOfferDto jobOfferDto) {
		log.trace("Call controller method createJobOffer() with params: {}", jobOfferDto.getId());
		jobOfferService.updateJobOffer(jobOfferDto);
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA')")
	@Operation(method="DELETE", description="Deletes a list of job offers")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteJobOffer(@RequestBody List<JobOffer> jobOffers) {
		log.trace("Call controller method deleteJobOffers with params: {}", jobOffers.size());
		jobOfferService.deleteJobOffers(jobOffers);
	}
}
