package abb.pfg.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entitys.Role;
import abb.pfg.main.entitys.User;

/**
 * JPA repository for users
 * 
 * @author Adrian Barco Barona
 *
 */

public interface UserRepository extends JpaRepository<User,Long>{
	public List<User> findByRole(Role role);
}
