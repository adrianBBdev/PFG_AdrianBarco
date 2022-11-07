package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import abb.pfg.main.entitys.Role;
import abb.pfg.main.entitys.User;
import abb.pfg.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public List<User> listAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(Long id) {
		log.debug("HEMOS ENTRADO EN getUser(Long id) del servicio");
		Optional<User> optionalUser = userRepository.findById(id);
		User user = optionalUser.isPresent() ? optionalUser.get() : null;
		log.debug("HEMOS OBTENIDO a este usuario: " + user);
		return user;
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		User userDB = this.getUser(user.getUserId());
		if(userDB == null) {
			return null;
		}
		userDB.setUsername(user.getUsername());
		userDB.setRole(user.getRole());
		userDB.setPassword(user.getPassword());
		return userRepository.save(userDB);
	}

	@Override
	public void deleteUser(Long id) {
		log.trace("Called service method deleteUser for id {}", id);
		if(userRepository.existsById(id)){
			log.debug("Company with id {} finded", id);
			userRepository.deleteById(id);
		}
	}

	@Override
	public List<User> findByRole(Role role) {
		return userRepository.findByRole(role);
	}

}
