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

import abb.pfg.main.entitys.Company;
import abb.pfg.main.service.CompanyService;

/**
 * Controller to handle companies
 * 
 * @author Adrian Barco Barona
 *
 */

@RestController
@RequestMapping(value="/companys")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public ResponseEntity<List<Company>> listCompany() {
		List<Company> companys = new ArrayList<>();
	
		companys = companyService.listAllCompanys();
		if(companys.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(companys);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Company> getCompany(@PathVariable("id") Long id){
		Company companyDB = companyService.getCompany(id);
		return ResponseEntity.ok(companyDB);
	}
	
	@PostMapping
	public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
		if(companyService.listAllCompanys().contains(company)) {
			return ResponseEntity.badRequest().build();
		}
		Company createdCompany = companyService.createCompany(company);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Company> updateCompany(@PathVariable("id") Long id, @RequestBody Company company){
		company.setCompanyId(id);
		Company companyDB = companyService.updateCompany(company);
		if(companyDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(companyDB);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCompany(@PathVariable Long id) {
		companyService.deleteCompany(id);
	}
}
