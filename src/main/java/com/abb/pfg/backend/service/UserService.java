package com.abb.pfg.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.UserDto;
import com.abb.pfg.backend.entities.User;
import com.abb.pfg.backend.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
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
	
	/**
	 * Gets all users
	 * 
	 * @param pageable - user's pageable
	 * @return Page - list of users
	 */
	public Page<User> listAllUsers(Pageable pageable){
		log.trace("Call service method listAllUsers()");
		var userPage = userRepository.findAll(pageable);
		log.debug("List of users found: {}", userPage.getNumberOfElements());
		return userPage;
	}
	
	/**
	 * Gets a specific user from its id
	 * 
	 * @param username - user's username
	 * @return UserDto - the requested user
	 */
	public UserDto getUserByEmail(String email) {
		log.trace("Call service method getUserByEmail() with params: {email}");
		var user = userRepository.findByEmail(email);
		log.debug("User found: {}", user.getEmail());
		return convertToDto(user);
	}
	
	/**
	 * Gets a specific user from its id
	 * 
	 * @param id - user's id
	 * @return UserDto - the requested user
	 */
	public UserDto getUser(Long id) {
		log.trace("Call service method getUser() with params: {id}");
		var optionalUser = userRepository.findById(id);
		var user = optionalUser.isPresent() ? optionalUser.get() : null;
		log.debug("User found: {}", user.getId());
		return convertToDto(user);
	}
	
	/**
	 * Creates a new user
	 * 
	 * @param userDto - the new user
	 */
	public void createUser(UserDto userDto) {
		log.trace("Call service method createUser() with params: {}", userDto.getId());
		if(!userRepository.existsById(userDto.getId())) {
			log.debug("New user: {}", userDto.getId());
			userRepository.save(convertToEntity(userDto));
		} else {
			log.debug("The user already exists");
		}
	}
	
	/**
	 * Updates an existing user
	 * 
	 * @param userDto - the user that will be updated
	 */
	public void updateUser(UserDto userDto) {
		log.trace("Call service method createUser() with params: {}", userDto.getId());
		if(userRepository.existsById(userDto.getId())) {
			log.debug("User updated: {}", userDto.getId());
			userRepository.save(convertToEntity(userDto));
		} else {
			log.debug("The user does not exist");
		}
	}
	
	/**
	 * Deletes all provided users
	 * 
	 * @param users - list of users to delete
	 */
	public void deleteUsers(List<User> users) {
		log.trace("Call service method deleteUsers() with params: {}", users.size());
		userRepository.deleteAllInBatch(users);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param user - entity to convert
	 * @return UserDto - data transfer objetct converted
	 */
	private UserDto convertToDto(User user) {
		var userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param userDto - data transfer objetct to convert
	 * @return User - entity converted
	 */
	private User convertToEntity(UserDto userDto) {
		var user = modelMapper.map(userDto, User.class);
		return user;
	}
}
