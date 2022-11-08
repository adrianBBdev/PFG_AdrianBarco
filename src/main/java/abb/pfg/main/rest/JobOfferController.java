package abb.pfg.main.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import abb.pfg.main.commons.Constants;
import abb.pfg.main.entities.Company;
import abb.pfg.main.entities.JobOffer;
import abb.pfg.main.service.JobOfferService;
import io.swagger.models.HttpMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle job offers
 * 
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.JobOffers.PATH)
@Tag(name="JobOfferController", description="Controller to manage the job offers of the web app")
public class JobOfferController {
	
	private final JobOfferService jobOfferService;
	
	/**
	 * Default constructor
	 * 
	 * @param jobOfferService - job offer's service interface
	 */
	@Autowired
	public JobOfferController(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
	
	/**
	 * Gets all the job offers
	 * 
	 * @return List of job offers
	 */
	@Operation(method="GET", description="Gets all the job offers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping
	public List<JobOffer> listJobOffer() {
		log.trace(String.format(Constants.Controllers.JobOffers.PATH, HttpMethod.GET.name()));
		
		List<JobOffer> jobOffers = new ArrayList<>();
		jobOffers = jobOfferService.listAllJobOffer();
		return jobOffers;
	}
	
	/**
	 * Gets a specific job offer from its id
	 * 
	 * @param id - job offer's id
	 * @return a job offer
	 */
	@Operation(method="GET", description="Gets a specific job offer from its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(value="/{id}")
	public JobOffer getJobOffer(@PathVariable("id") Long id){
		log.trace(String.format(Constants.Controllers.JobOffers.PATH, HttpMethod.GET.name()));
		JobOffer jobOfferDB = jobOfferService.getJobOffer(id);
		return jobOfferDB;
	}
	
	/**
	 * Creates a new job offer
	 * 
	 * @param jobOffer - new job offer to create
	 */
	@Operation(method="POST", description="Creates a new job offer")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Created") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createJobOffer(@Valid @RequestBody JobOffer jobOffer) {
		log.trace(String.format(Constants.Controllers.JobOffers.PATH, HttpMethod.POST.name()));
		jobOfferService.createJobOffer(jobOffer);
	}
	
	/**
	 * Updates an existing job offer from its id
	 * 
	 * @param id - job offer's id to be updated
	 * @param jobOffer - job offer's parameters to update
	 */
	@Operation(method="PUT", description="Updates an existing user from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok") })
	@PutMapping(value="/{id}")
	public void updateJobOffer(@PathVariable("id") Long id, @RequestBody JobOffer jobOffer){
		log.trace(String.format(Constants.Controllers.JobOffers.PATH, HttpMethod.PUT.name()));
		jobOffer.setJobOfferId(id);
		jobOfferService.updateJobOffer(jobOffer);
	}
	
	/**
	 * Deletes a specific job offer from its id
	 * 
	 * @param id - job offer's id
	 */
	@Operation(method="DELETE", description="Deletes a specific job offer from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteJobOffer(@PathVariable Long id) {
		log.trace(String.format(Constants.Controllers.Companies.PATH), HttpMethod.DELETE.name());
		jobOfferService.deleteJobOffer(id);
	}
	
	/**
	 * Deletes a set of job offers from a specified company, or every job offers
	 * 
	 * @param company - the company that owns the job offer
	 */
	@Operation(method="DELETE", description="Deletes a set of job offers from a specified company, or every job offers")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllJobOffersByCompany(@Valid @RequestBody Company company) {
		log.trace(String.format(Constants.Controllers.Companies.PATH), HttpMethod.DELETE.name());
		jobOfferService.deleteByCompany(company);
	}
}
