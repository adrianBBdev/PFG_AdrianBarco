package abb.pfg.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entitys.Company;


/**
 * JPA repository for companies
 * 
 * @author Adrian Barco Barona
 *
 */
public interface CompanyRepository extends JpaRepository<Company, Long>{
	
}	
