package abb.pfg.main.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import abb.pfg.main.commons.Constants;
import abb.pfg.main.entitys.Role;
import abb.pfg.main.entitys.User;
import abb.pfg.main.service.UserService;
import io.swagger.models.HttpMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle users
 * 
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Users.PATH)
@Tag(name="UserController", description="Controller to manage users from the web app")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * Gets all users
	 * 
	 * @param role - role of the users we want to list
	 * @return List of users
	 */
	@Operation(method="GET", description="Gets all users")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> listUsers(@RequestParam(name="role", required = false) Role role){
		log.trace(String.format(Constants.Controllers.Users.PATH, HttpMethod.GET.name()));
		List<User> users = new ArrayList<>();
		if(role == null) {
			users = userService.listAllUsers();
		} else {
			users = userService.findByRole(role);
		}
		return ResponseEntity.ok(users);
	}
	
	/**
	 * Gets a specific user from its id
	 * 
	 * @param id - user's id
	 * @return a user
	 */
	@Operation(method="GET", description="Gets a specific user from its id")
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		log.trace(String.format(Constants.Controllers.Users.PATH, HttpMethod.GET.name()));
		User userDB = userService.getUser(id);
		return ResponseEntity.ok(userDB);
	}
	
	/**
	 * Creates a new user
	 * 
	 * @param user - new user to create
	 */
	@Operation(method="POST", description="Creates a new user")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Created") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@Valid @RequestBody User user) {
		log.trace(String.format(Constants.Controllers.Users.PATH, HttpMethod.POST.name()));
		userService.createUser(user);
	}
	
	/**
	 * Updates an existing user from its id
	 * 
	 * @param id - user's id to be updated
	 * @param user - user's parameters to update
	 */
	@Operation(method="PUT", description="Updates an existing user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok") })
	@PutMapping(value="/{id}")
	public void updateUser(@PathVariable("id") Long id, @RequestBody User user){
		log.trace(String.format(Constants.Controllers.Users.PATH, HttpMethod.PUT.name()));
		user.setUserId(id);
		userService.updateUser(user);
	}
	
	/**
	 * Deletes a specific user from its id
	 * 
	 * @param id - user's id
	 */
	@Operation(method="DELETE", description="Deletes a specific user from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") Long id){
		log.trace(String.format(HttpMethod.DELETE.name(), Constants.Controllers.Users.PATH));
		userService.deleteUser(id);
	}
}

