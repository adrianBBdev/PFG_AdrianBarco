package abb.pfg.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import abb.pfg.main.entitys.Student;
import abb.pfg.main.entitys.User;

/**
 * JPA repository for students
 * 
 * @author Adrian Barco Barona
 *
 */

public interface StudentRepository extends JpaRepository<Student, Long>{
	public Student findByUser(User user);
}
