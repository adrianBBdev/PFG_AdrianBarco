package com.abb.pfg.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.config.UserRegistrationDto;
import com.abb.pfg.backend.dtos.UserDto;
import com.abb.pfg.backend.entities.User;
import com.abb.pfg.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the user objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Users.PATH)
@Tag(name="UserController", description="Controller to manage the users of the web app")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(method="GET", description="Gets all users")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<User> getAllUsers(@RequestParam(defaultValue="0") Integer page, @RequestParam(defaultValue="3") Integer size) {
		var pageable = PageRequest.of(page, size);
		var pageUser = userService.listAllUsers(pageable);
		return pageUser;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets a specific user from its username")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/user", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUserByUsername(@RequestParam(required=true) String username) {
		log.trace("Call controller method getUserByUsername() with params: {}", username);
		return userService.getUserByUsername(username);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(method="POST", description="Creates a new user")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
		userService.createUser(userRegistrationDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="PUT", description="Updates an existing user")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateUser(@RequestBody UserDto userDto) {
		log.trace("Call controller method updateUser() with params: {}", userDto.getUsername());
		userService.updateUser(userDto);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(method="DELETE", description="Deletes a list of selected users")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteUser(@RequestParam(required=true) String username) {
		userService.deleteUser(username);
	}
}
