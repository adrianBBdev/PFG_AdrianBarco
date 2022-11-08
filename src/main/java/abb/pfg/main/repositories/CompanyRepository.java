package abb.pfg.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entities.Company;
import abb.pfg.main.entities.User;

/**
 * JPA repository for companies
 * 
 * @author Adrian Barco Barona
 *
 */
public interface CompanyRepository extends JpaRepository<Company, Long>{
	public Company findByUser(User user);
}	
