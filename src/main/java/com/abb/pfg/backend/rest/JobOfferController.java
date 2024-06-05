package com.abb.pfg.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.commons.Modality;
import com.abb.pfg.backend.config.JobOfferCreationDto;
import com.abb.pfg.backend.dtos.JobOfferDto;
import com.abb.pfg.backend.entities.JobOffer;
import com.abb.pfg.backend.service.JobOfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

/**
 * Controller associated with the job offer objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.JobOffers.PATH)
@Tag(name="JobOfferController", description="Controller to manage the job offers of the web app")
public class JobOfferController {

	@Autowired
	private JobOfferService jobOfferService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY','GUEST')")
	@Operation(method="GET", description="Gets all job offers or filter by parameters")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<JobOffer> getAllJobOffersByCityAndModalityAndAreaAndDurationAndCompany(@RequestParam(required=false) String city,
			@RequestParam(required=false) Modality modality,
			@RequestParam(required=false) String area,
			@RequestParam(required=false) Integer minDuration,
			@RequestParam(required=false) Integer maxDuration,
			@RequestParam(required=false) String companyId,
			@RequestParam(required=false) String name,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="3")int size) {
		var pageable = PageRequest.of(page, size);
		var jobOfferPage = jobOfferService.listAllJobOffersByCityAndModalityAndAreaAndDurationAndCompany(city,
				modality, area, minDuration, maxDuration, companyId, name,pageable);
		return jobOfferPage;
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY','GUEST')")
	@Operation(method="GET", description="Gets a specific job offer from its offer code")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/jobOffer",produces=MediaType.APPLICATION_JSON_VALUE)
	public JobOfferDto getJobOfferByOfferId(@RequestParam(required=true) Long offerCode) {
		return jobOfferService.getJobOfferById(offerCode);
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="POST", description="Creates a new job offer")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createJobOffer(@RequestBody JobOfferCreationDto jobOfferDto) {
		jobOfferService.createJobOffer(jobOfferDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="PUT", description="Updates an existing job offer")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateJobOffer(@RequestBody JobOfferDto jobOfferDto) {
		jobOfferService.updateJobOffer(jobOfferDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="DELETE", description="Deletes a list of job offers")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteJobOffer(@RequestParam Long jobOfferDto) {
		jobOfferService.deleteJobOffers(jobOfferDto);
	}
}
