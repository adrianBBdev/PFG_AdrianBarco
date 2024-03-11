package com.abb.pfg.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.AdministratorDto;
import com.abb.pfg.backend.entities.Administrator;
import com.abb.pfg.backend.repositories.AdminRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the administrator entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class AdminService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired	
	private AdminRepository adminRepository;
	
	/**
	 * Gets all admins
	 * 
	 * @param pageable - pageable of admins
	 * @return Page - list with the requested admins
	 */
	public Page<Administrator> listAllAdmins(Pageable pageable){
		log.trace("Call service method listAllAdmins()");
		var admins = adminRepository.findAll(pageable);
		log.debug("List of admins found: {}", admins.getNumberOfElements());
		return admins;
	}
	
	/**
	 * Gets the admin with the requested id
	 * 
	 * @param id - admin id
	 * @return AdministratorDtp - the requested admin
	 */
	public AdministratorDto getAdmin(Long id) {
		log.trace("Call service method getArea() with params: {}", id);
		var optionalAdmin = adminRepository.findById(id);
		var admin = optionalAdmin.isPresent() ? optionalAdmin.get() : null;
		log.debug("Admin found: {}", admin.getId());
		return convertToDto(admin);
	}
	
	/**
	 * Creates a new admin
	 * 
	 * @param adminDto - the new admin
	 */
	public void createAdmin(AdministratorDto adminDto) {
		log.trace("Call service method createAdmin() with params: {}", adminDto.getId());
		if(!adminRepository.existsById(adminDto.getId())) {
			log.debug("New admin: {}", adminDto.getId());
			adminRepository.save(convertToEntity(adminDto));
		} else {
			log.debug("The admin already exists");
		}
	}
	
	/**
	 * Updates an existing admin
	 * 
	 * @param adminDto - the admin that will be updated
	 */
	public void updateAdmin(AdministratorDto adminDto) {
		log.trace("Call service method updateAdmin() with params: {}", adminDto.getId());
		if(adminRepository.existsById(adminDto.getId())) {
			log.debug("Admin updated: {}", adminDto.getId());
			adminRepository.save(convertToEntity(adminDto));
		} else {
			log.debug("The admin does not exist");
		}
	}
	
	/**
	 * Deletes all provided admins
	 * 
	 * @param admins - list of admins to delete
	 */
	public void deleteAdmins(List<Administrator> admins) {
		log.trace("Call service method deleteAllAdmins() with params: {}", admins.size());
		adminRepository.deleteAllInBatch(admins);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param admin - entity to convert
	 * @return AdministratorDto - data transfer object converted
	 */
	private AdministratorDto convertToDto(Administrator admin) {
		var adminDto = modelMapper.map(admin, AdministratorDto.class);
		return adminDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param adminDto - data transfer object to convert
	 * @return Administrator - entity converted
	 */
	private Administrator convertToEntity(AdministratorDto adminDto) {
		var admin = modelMapper.map(adminDto, Administrator.class);
		return admin;
	}
}
