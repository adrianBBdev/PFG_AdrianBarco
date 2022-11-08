package abb.pfg.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entities.Role;
import abb.pfg.main.entities.User;

/**
 * JPA repository for users
 * 
 * @author Adrian Barco Barona
 *
 */

public interface UserRepository extends JpaRepository<User,Long>{
	public List<User> findByRole(Role role);
	public void deleteByRole(Role role);
}
