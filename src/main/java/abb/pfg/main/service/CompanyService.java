package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entitys.Company;

/**
 * 	Companies service interface. Shows which operations are available
 * 
 * @author Adrian Barco Barona
 *
 */

public interface CompanyService {
	
	/**
	 * Gets all companies
	 * 
	 * @return List of companies
	 */
	public List<Company> listAllCompanys();
	
	/**
	 * Get the company from its id
	 * 
	 * @param id - the id of the company 
	 * @return Company - the selected company 
	 */
	public Company getCompany(Long id);
	
	/**
	 * Creates a new company 
	 * 
	 * @param company - the company which is going to be created
	 * @return Company - the new company
	 */
	public Company createCompany(Company company);
	
	/**
	 * Updates the attributes of a company which already exists.
	 * 
	 * @param company - the company which is going to be updated
	 * @return Company
	 */
	public Company updateCompany(Company company);
	
	/**
	 * Deletes a company from its id
	 * 
	 * @param id - the id of the company which is going to be deleted
	 */
	public void deleteCompany(Long id);
}
