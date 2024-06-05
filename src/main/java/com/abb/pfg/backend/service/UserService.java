package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.config.UserRegistrationDto;
import com.abb.pfg.backend.dtos.AdministratorDto;
import com.abb.pfg.backend.dtos.UserDto;
import com.abb.pfg.backend.entities.User;
import com.abb.pfg.backend.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the users
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class UserService {

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Gets all users
	 *
	 * @param pageable - users pageable
	 * @return Page - list of users
	 */
	public Page<User> listAllUsers(Pageable pageable){
		var userPage = userRepository.findAll(pageable);
		return userPage;
	}

	/**
	 * Gets a specific user from its id
	 *
	 * @param username - username associated with the user
	 * @return UserDto - the requested user
	 */
	public UserDto getUserByUsername(String username) {
		var user = userRepository.findByUsername(username);
		return convertToDto(user);
	}

	/**
	 * Creates a new user
	 *
	 * @param userDto - the new user
	 */
	@Transactional
	public void createUser(UserRegistrationDto userRegistrationDto) {
		var roleDto = roleService.getRoleByName(userRegistrationDto.getRoleName());
		var userDto = userRegistrationDto.getUserDto();
		userDto.setRole(roleService.convertToEntity(roleDto));
		if(userRepository.existsByUsername(userDto.getUsername())) {
			return;
		}
		userRepository.save(convertToEntity(userDto));
		verifyRoleBeforeUserCreation(userRegistrationDto, userDto, roleDto.getName());
	}
	
	private void verifyRoleBeforeUserCreation(UserRegistrationDto userRegistrationDto, UserDto userDto, String role) {
		switch(role) {
			case "STUDENT":
				var studentDto = userRegistrationDto.getStudentDto();
				studentDto.setUser(userRepository.findByUsername(userDto.getUsername()));
				studentService.createStudent(studentDto);
				break;
			case "COMPANY":
				var companyDto = userRegistrationDto.getCompanyDto();
				companyDto.setUser(userRepository.findByUsername(userDto.getUsername()));
				companyService.createCompany(companyDto);
				break;
			default:	//"ADMIN"
				var adminDto = new AdministratorDto();
				adminDto.setUser(userRepository.findByUsername(userDto.getUsername()));
				adminService.createAdmin(adminDto);
		}
	}

	/**
	 * Updates an existing user
	 *
	 * @param userDto - the user that will be updated
	 */
	public void updateUser(UserDto userDto) {
		if(userRepository.existsById(userDto.getId())) {
			userRepository.save(convertToEntity(userDto));
			return;
		}
		log.debug("The user does not exist");
	}

	/**
	 * Deletes all provided users
	 *
	 * @param users - list of users to delete
	 */
	public void deleteUser(String username) {
		userRepository.deleteByUsername(username);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param user - entity to convert
	 * @return UserDto - data transfer object converted
	 */
	private UserDto convertToDto(User user) {
		try {
			return modelMapper.map(user, UserDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param userDto - data transfer object to convert
	 * @return User - entity converted
	 */
	private User convertToEntity(UserDto userDto) {
		String hashPassword = passwordEncoder.encode(userDto.getPassword());
		try {
			userDto.setPassword(hashPassword);
			return modelMapper.map(userDto, User.class);
		} catch(Exception e) {
			return null;
		}
	}
}
