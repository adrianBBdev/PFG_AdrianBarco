package com.abb.pfg.backend.service;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.CompanyDto;
import com.abb.pfg.backend.entities.Company;
import com.abb.pfg.backend.repositories.CompanyRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Class which represents the company's service
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Slf4j
@Service
public class CompanyService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	/**
	 * Gets all companies
	 * 
	 * @param pageable pageable of companies
	 * @return List - list with the requested companies
	 */
	public Page<Company> listAllCompaniesByNameAndCountryAndUser(String name, String country, Long id, Pageable pageable) {
		log.trace("Call service method listAllCompanies()");
		var pageCompany = companyRepository.findByNameAndCountryAndUser_Id(name, country, id, pageable);
		log.debug("List of companies found: {}", pageCompany.getNumberOfElements());
		return pageCompany;
	}
	
	/**
	 * Gets the company with the requested id 
	 * 
	 * @param id - company's id
	 * @return CompanyDto - the requested company
	 */
	public CompanyDto getCompany(Long id) {
		log.trace("Call service method getCompany() with params: {}", id);
		var optionalCompany = companyRepository.findById(id);
		var company = optionalCompany.isPresent() ? optionalCompany.get() : null;
		log.debug("Company found: {}", company.getId());
		return convertToDto(company);
	}
	
	/**
	 * Creates a new company
	 * 
	 * @param companyDto - the company that will be created
	 */
	public void createCompany(CompanyDto companyDto) {
		log.trace("Call service method createCompany() with params: {}", companyDto.getId());
		if(!companyRepository.existsById(companyDto.getId())){
			log.debug("New company: {}", companyDto.getId());
			companyRepository.save(convertToEntity(companyDto));
		} else {
			log.debug("The company already exists");
		}
	}
	
	/**
	 * Updates an existing company
	 * 
	 * @param companyDto - the company that will be updated
	 */
	public void updateCompany(CompanyDto companyDto) {
		log.trace("Call service method updateCompany() with params: {}", companyDto.getId());
		if(companyRepository.existsById(companyDto.getId())){
			log.debug("Company updated: {}", companyDto.getId());
			companyRepository.save(convertToEntity(companyDto));
		} else {
			log.debug("The company does not exist");
		}
	}
	
	/**
	 * Deletes all provided companies
	 * 
	 * @param companies - list of companies to delete
	 */
	public void deleteCompanies(List<Company> companies) {
		log.trace("Call service method deleteCompanies() with {} companies", companies.size());
		companyRepository.deleteAllInBatch(companies);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param company - company to convert
	 * @return CompanyDto - data transfer objetct converted
	 */
	private CompanyDto convertToDto(Company company) {
		var companyDto = modelMapper.map(company, CompanyDto.class);
		return companyDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param companyDto - data transfer objetct to convert
	 * @return Company - company converted
	 */
	private Company convertToEntity(CompanyDto companyDto) {
		var company = modelMapper.map(companyDto, Company.class);
		return company;
	}
}
