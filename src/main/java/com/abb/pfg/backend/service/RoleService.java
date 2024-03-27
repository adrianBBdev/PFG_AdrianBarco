/**
 *
 */
package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.RoleDto;
import com.abb.pfg.backend.entities.Role;
import com.abb.pfg.backend.repositories.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the roles
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class RoleService {

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Gets all roles
	 *
	 * @param pageable - roles pageable
	 * @return Page - list of roles
	 */
	public Page<Role> listAllRoles(Pageable pageable){
		log.trace("Call service method listAllRoles()");
		var rolePage = roleRepository.findAll(pageable);
		log.debug("List of roles found: {}", rolePage.getNumberOfElements());
		return rolePage;
	}

	/**
	 * Gets a specific user from its id
	 *
	 * @param username - username associated with the user
	 * @return UserDto - the requested user
	 */
	public RoleDto getRoleByName(String name) {
		log.trace("Call service method getRoleByName() with params: {name}");
		var role = roleRepository.findByName(name);
		log.debug("Role found: {}", role.getName());
		return convertToDto(role);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param role - entity to convert
	 * @return RoleDto - data transfer object converted
	 */
	private RoleDto convertToDto(Role role) {
		var roleDto = modelMapper.map(role, RoleDto.class);
		return roleDto;
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param roleDto - data transfer object to convert
	 * @return Role - entity converted
	 */
	private Role convertToEntity(RoleDto roleDto) {
		var role = modelMapper.map(roleDto, Role.class);
		return role;
	}
}
