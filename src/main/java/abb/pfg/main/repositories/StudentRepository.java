package abb.pfg.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entities.Student;
import abb.pfg.main.entities.User;

/**
 * JPA repository for students
 * 
 * @author Adrian Barco Barona
 *
 */

public interface StudentRepository extends JpaRepository<Student, Long>{
	public Student findByUser(User user);
}
