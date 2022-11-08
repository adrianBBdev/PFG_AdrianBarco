package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entities.Company;
import abb.pfg.main.entities.User;

/**
 * 	Companies' service interface. Shows which operations are available
 * 
 * @author Adrian Barco Barona
 *
 */

public interface CompanyService {
	
	/**
	 * Gets all companies
	 * 
	 * @return List of CompanyDTO
	 */
	public List<Company> listAllCompanys();
	
	/**
	 * Gets the company from its id
	 * 
	 * @param id - the company's id
	 * @return CompanyDTO - the selected company 
	 */
	public Company getCompany(Long id);
	
	/**
	 * Creates a new company 
	 * 
	 * @param company - new company's parameters
	 */
	public void createCompany(Company company);
	
	/**
	 * Updates an existing company 
	 * 
	 * @param company - company's new parameters
	 */
	public void updateCompany(Company company);
	
	/**
	 * Deletes an existing company from its id
	 * 
	 * @param id - the company's id
	 */
	public void deleteCompany(Long id);
	
	/**
	 * Deletes all companies
	 * 
	 */
	public void deleteAllCompanies();
	
	/**
	 * Gets a company from its user
	 * 
	 * @param user - student's user
	 * @return CompanyDTO - the selected company
	 */
	public Company findByUser(User user);
}
