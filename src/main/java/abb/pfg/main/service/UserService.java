package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entities.Role;
import abb.pfg.main.entities.User;

/**
 * Users' service interface. Shows which operations are available
 * 
 * @author Adrian Barco Barona
 *
 */

public interface UserService {
	
	/**
	 * Gets all users
	 * 
	 * @return List of UserDTO
	 */
	public List<User> listAllUsers();
	
	/**
	 * Gets a specific user from its id
	 * 
	 * @param id - user's id
	 * @return UserDTO - the selected user
	 */
	public User getUser(Long id);
	
	/**
	 * Creates a new user
	 * 
	 * @param user - new user's parameters
	 */
	public void createUser(User user);
	
	/**
	 * Updates an existing user
	 * 
	 * @param user - user's new parameters
	 */
	public void updateUser(User user);
	
	/**
	 * Deletes an existing user from its id
	 * 
	 * @param id - user's id
	 */
	public void deleteUser(Long id);
	
	/**
	 * Deletes all users from an specific role, or every users
	 * 
	 */
	public void deleteByRole(Role role);
	
	/**
	 * Gets all users of a specific role
	 * 
	 * @param role - role to filter
	 * @return List of UserDTO
	 */
	public List<User> findByRole(Role role);
}
