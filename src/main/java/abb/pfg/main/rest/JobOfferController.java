package abb.pfg.main.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import abb.pfg.main.entitys.JobOffer;
import abb.pfg.main.service.JobOfferService;

/**
 * Controller to handle job offers
 * 
 * @author Adrian Barco Barona
 *
 */

@RestController
@RequestMapping(value="/JobOffers")
public class JobOfferController {
	
	@Autowired
	private JobOfferService jobOfferService;
	
	
	@GetMapping
	public ResponseEntity<List<JobOffer>> listJobOffer() {
		List<JobOffer> jobOffers = new ArrayList<>();
	
		jobOffers = jobOfferService.listAllJobOffer();
		if(jobOffers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(jobOffers);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<JobOffer> getJobOffer(@PathVariable("id") Long id){
		JobOffer jobOfferDB = jobOfferService.getJobOffer(id);
		return ResponseEntity.ok(jobOfferDB);
	}
	
	@PostMapping
	public ResponseEntity<JobOffer> createJobOffer(@Valid @RequestBody JobOffer jobOffer) {
		if(jobOfferService.listAllJobOffer().contains(jobOffer)) {
			return ResponseEntity.badRequest().build();
		}
		JobOffer createdJobOffer = jobOfferService.createJobOffer(jobOffer);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdJobOffer);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<JobOffer> updateJobOffer(@PathVariable("id") Long id, @RequestBody JobOffer jobOffer){
		jobOffer.setJobOfferId(id);
		JobOffer jobOfferDB = jobOfferService.updateJobOffer(jobOffer);
		if(jobOfferDB == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(jobOfferDB);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteJobOffer(@PathVariable Long id) {
		jobOfferService.deleteJobOffer(id);
	}
}
