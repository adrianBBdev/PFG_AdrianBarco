package com.abb.pfg.backend.service;

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
		var admins = adminRepository.findAll(pageable);
		return admins;
	}

	/**
	 * Gets the admin with the requested id
	 *
	 * @param id - admin id
	 * @return AdministratorDtp - the requested admin
	 */
	public AdministratorDto getAdmin(Long id) {
		var optionalAdmin = adminRepository.findById(id);
		var admin = optionalAdmin.isPresent() ? optionalAdmin.get() : null;
		return convertToDto(admin);
	}

	/**
	 * Creates a new admin
	 *
	 * @param adminDto - the new admin
	 */
	public void createAdmin(AdministratorDto adminDto) {
		if(!adminRepository.existsByUser(adminDto.getUser())) {
			adminRepository.save(convertToEntity(adminDto));
			return;
		}
		log.debug("The admin already exists");
	}

	/**
	 * Updates an existing admin
	 *
	 * @param adminDto - the admin that will be updated
	 */
	public void updateAdmin(AdministratorDto adminDto) {
		if(adminRepository.existsById(adminDto.getId())) {
			adminRepository.save(convertToEntity(adminDto));
			return;
		}
		log.debug("The admin does not exist");
	}

	/**
	 * Deletes all provided admins
	 *
	 * @param admins - list of admins to delete
	 */
	public void deleteAdmin(String username) {
		var user = adminRepository.findByUsername(username).get().getUser();
		adminRepository.deleteByUser(user);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param admin - entity to convert
	 * @return AdministratorDto - data transfer object converted
	 */
	private AdministratorDto convertToDto(Administrator admin) {
		try {
			var adminDto = modelMapper.map(admin, AdministratorDto.class);
			return adminDto;
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param adminDto - data transfer object to convert
	 * @return Administrator - entity converted
	 */
	private Administrator convertToEntity(AdministratorDto adminDto) {
		try {
			return modelMapper.map(adminDto, Administrator.class);
		} catch(Exception e) {
			return null;
		}
	}
}
