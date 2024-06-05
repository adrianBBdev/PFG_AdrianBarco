package com.abb.pfg.backend.service;

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
 * Service associated with the companies
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
		var pageCompany = companyRepository.findByNameAndCountryAndUser_Id(name, country, id, pageable);
		return pageCompany;
	}
	
	/**
	 * Gets the company with the requested id
	 * 
	 * @param username - company's username
	 * @param cif - company's cif
	 * @return CompanyDto - the requested company if exists, or null
	 */
	public CompanyDto getCompanyByUsernameAndCif(String username, String cif) {
		if(username == null) {
			return getCompanyByCif(cif);
		}
		var optionalCompany = companyRepository.findByUsername(username);
		var company = optionalCompany.isPresent() ? optionalCompany.get() : null;
		return convertToDto(company);
	}

	/**
	 * Gets the company with the requested CIF
	 *
	 * @param cif - required CIF of the specified company
	 * @return CompanyDto - the requested company if exists, or null
	 */
	public CompanyDto getCompanyByCif(String cif) {
		var optionalCompany = companyRepository.findByCif(cif);
		var company = optionalCompany.isPresent() ? optionalCompany.get() : null;
		return convertToDto(company);
	}

	/**
	 * Creates a new company
	 *
	 * @param companyDto - the company that will be created
	 */
	public void createCompany(CompanyDto companyDto) {
		if(!companyRepository.existsByCif(companyDto.getCif())){
			companyRepository.save(convertToEntity(companyDto));
			return;
		}
		log.debug("The company already exists");
	}

	/**
	 * Updates an existing company
	 *
	 * @param companyDto - the company that will be updated
	 */
	public void updateCompany(CompanyDto companyDto) {
		if(companyRepository.existsByCif(companyDto.getCif())){
			companyRepository.save(convertToEntity(companyDto));
			return;
		}
		log.debug("The company does not exist");
	}

	/**
	 * Deletes all provided companies
	 *
	 * @param username - company's username to delete
	 */
	public void deleteCompany(String username) {
		var user = companyRepository.findByUsername(username).get().getUser();
		companyRepository.deleteByUser(user);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param company - company to convert
	 * @return CompanyDto - data transfer object converted
	 */
	private CompanyDto convertToDto(Company company) {
		try {
			return modelMapper.map(company, CompanyDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param companyDto - data transfer object to convert
	 * @return Company - company converted
	 */
	public Company convertToEntity(CompanyDto companyDto) {
		try {
			return modelMapper.map(companyDto, Company.class);
		} catch (Exception e) {
			return null;
		}
	}
}
