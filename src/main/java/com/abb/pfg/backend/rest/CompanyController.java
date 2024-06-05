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
import com.abb.pfg.backend.dtos.CompanyDto;
import com.abb.pfg.backend.entities.Company;
import com.abb.pfg.backend.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

/**
 * Controller associated with the company objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.Companies.PATH)
@Tag(name="CommpanyController", description="Controller to manage the companies of the web app")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="GET", description="Gets all companies")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<Company> getAllCompaniesByNameAndCountryAndUser(@RequestParam(required=false) String name,
			@RequestParam(required=false) String country, 
			@RequestParam(required=false) Long id,
			@RequestParam(defaultValue="0") Integer page, 
			@RequestParam(defaultValue="3") Integer size) {
		var pageable = PageRequest.of(page, size);
		var pageCompany = companyService.listAllCompaniesByNameAndCountryAndUser(name, country, id, pageable);
		return pageCompany;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets a specific company from its id")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/company", produces=MediaType.APPLICATION_JSON_VALUE)
	public CompanyDto getCompanyByUsernameAndDni(@RequestParam(required=false) String username, @RequestParam(required=false) String cif) {
		return companyService.getCompanyByUsernameAndCif(username, cif);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(method="POST", description="Creates a new company")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createCompany(@RequestBody CompanyDto companyDto) {
		companyService.createCompany(companyDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="PUT", description="Updates an existing company")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateCompany(@RequestBody CompanyDto companyDto) {
		companyService.updateCompany(companyDto);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(method="DELETE", description="Deletes a list of selected companies")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteCompanies(@RequestParam(required=true) String username) {
		companyService.deleteCompany(username);
	}
}
