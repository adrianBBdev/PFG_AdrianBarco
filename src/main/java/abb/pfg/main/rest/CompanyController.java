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
import abb.pfg.main.service.CompanyService;
import io.swagger.models.HttpMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle companies
 * 
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Companies.PATH)
@Tag(name="CompanyController", description="Controller to manage the companies of the web app")
public class CompanyController {

	private CompanyService companyService;
	
	/**
	 * Default constructor
	 * 
	 * @param companyService - company's service interface
	 */
	@Autowired
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	/**
	 * Gets all companies
	 * 
	 * @return List of companies
	 */
	@Operation(method="GET", description="Gets all companies")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping
	public List<Company> listCompany() {
		log.trace(String.format(Constants.Controllers.Companies.PATH, HttpMethod.GET.name()));
		List<Company> companys = new ArrayList<>();
		companys = companyService.listAllCompanys();
		return companys;
	}
	
	/**
	 * Gets a specific company from its id
	 * 
	 * @param id - company's id
	 * @return a company
	 */
	@Operation(method="GET", description="Gets a specific company from its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(value="/{id}")
	public Company getCompany(@PathVariable("id") Long id){
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.GET.name()));
		Company companyDB = companyService.getCompany(id);
		return companyDB;
	}
	
	/**
	 * Creates a new company
	 * 
	 * @param company - new company to create
	 */
	@Operation(method="POST", description="Creates a new company")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Created") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createCompany(@Valid @RequestBody Company company) {
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.POST.name()));
		companyService.createCompany(company);
	}
	
	/**
	 * Updates an existing company from its id
	 * 
	 * @param id - company's id 
	 * @param company - company's parameters to update
	 */
	@Operation(method="PUT", description="Updates an existing company from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok") })
	@PutMapping(value="/{id}")
	public void updateCompany(@PathVariable("id") Long id, @RequestBody Company company){
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.PUT.name()));
		company.setCompanyId(id);
		companyService.updateCompany(company);
	}
	
	/**
	 * Deletes a company from its id
	 * 
	 * @param id - company's id 
	 */
	@Operation(method="DELETE", description="Deletes a specific company from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCompany(@PathVariable Long id) {
		log.trace(String.format(Constants.Controllers.Students.PATH), HttpMethod.DELETE.name());
		companyService.deleteCompany(id);
	}
}
