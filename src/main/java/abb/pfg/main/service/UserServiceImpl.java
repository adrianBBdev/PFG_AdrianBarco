package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import abb.pfg.main.entities.Role;
import abb.pfg.main.entities.User;
import abb.pfg.main.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	/**
	 * Default constructor
	 * 
	 * @param userRepository - JPA users' repository
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public List<User> listAllUsers() {
		log.trace("Call service method listAllUsers");
		return userRepository.findAll();
	}

	@Override
	public User getUser(Long id) {
		log.trace("Call service method getUser with params: {}", id);
		Optional<User> optionalUser = userRepository.findById(id);
		User user = optionalUser.isPresent() ? optionalUser.get() : null;
		log.debug("Found user: {}" + user.getUsername());
		return user;
	}

	@Override
	public void createUser(User user) {
		log.trace("Call service method createUser with params: {}", user);
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user) {
		log.trace("Call service method updateUser with params: {}", user);
		User userDB = this.getUser(user.getUserId());
		/*if(userDB == null) {
			return null;
		}*/
		userDB.setUsername(user.getUsername());
		userDB.setRole(user.getRole());
		userDB.setPassword(user.getPassword());
		userRepository.save(userDB);
	}

	@Override
	public void deleteUser(Long id) {
		log.trace("Called service method deleteUser with params: {}", id);
		if(userRepository.existsById(id)){
			log.debug("Found user: {}", id);
			userRepository.deleteById(id);
		}
	}
	
	@Override
	public void deleteByRole(Role role) {
		log.trace("Called service method deletUsersByRole with params: {}", role);
		if(role != null) {
			userRepository.deleteByRole(role);
		} else {
			userRepository.deleteAll();
		}
	}

	@Override
	public List<User> findByRole(Role role) {
		log.trace("Called service method findByRole with params: {}", role);
		return userRepository.findByRole(role);
	}
}
