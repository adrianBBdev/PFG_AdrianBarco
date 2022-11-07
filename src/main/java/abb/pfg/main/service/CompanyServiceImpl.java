/**
 * 
 */
package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import abb.pfg.main.entitys.Company;
import abb.pfg.main.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements all operations over companies
 * 
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
	
	private final CompanyRepository companyRepository;

	@Override
	public List<Company> listAllCompanys() {
		return companyRepository.findAll();
	}

	@Override
	public Company getCompany(Long id) {
		Optional<Company> optionalCompany = companyRepository.findById(id);
		Company companyDB = optionalCompany.isPresent() ? optionalCompany.get() : null;
		return companyDB;
	}

	@Override
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Company company) {
		log.trace("Called service method updateCompany for company {}", company.getName());
		Company companyDB = getCompany(company.getCompanyId());
		if(companyDB == null) {
			log.debug("Company with id {} not finded", company.getCompanyId());
			return null;
		}
		companyDB.setCif(company.getCif());
		companyDB.setDescription(company.getDescription());
		companyDB.setEmail(company.getEmail());
		companyDB.setName(company.getName());
		companyDB.setUser(company.getUser());
		log.debug("Company with id {} finded", company.getCompanyId());
		return companyRepository.save(companyDB);
	}

	@Override
	public void deleteCompany(Long id) {
		log.trace("Called service method deleteCompany for id {}", id);
		if(companyRepository.existsById(id)) {
			log.debug("Company with id {} finded", id);
			companyRepository.deleteById(id);
		}
	}
}
