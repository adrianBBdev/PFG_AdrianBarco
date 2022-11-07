package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entitys.Role;
import abb.pfg.main.entitys.User;

/**
 * Users service interface. Shows which operations are available
 * 
 * @author Usuario
 *
 */

public interface UserService {
	
	public List<User> listAllUsers();
	public User getUser(Long id);
	public User createUser(User user);
	public User updateUser(User user);
	public void deleteUser(Long id);
	public List<User> findByRole(Role role);
}
