package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entities.Student;
import abb.pfg.main.entities.User;

/**
 * Students' service interface. Shows which operations are available
 * 
 * @author Adrian Barco Barona
 *
 */
public interface StudentService {
	
	/**
	 * Gets all students
	 * 
	 * @return List of StudentDTO
	 */
	public List<Student> listAllStudents();
	
	/**
	 * Gets a specific student from its id
	 * 
	 * @param id - student's id
	 * @return StudentDTO - the selected student
	 */
	public Student getStudent(Long id);
	
	/**
	 * Creates a new student
	 * 
	 * @param student - new student's parameters
	 */
	public void createStudent(Student student);
	
	/**
	 * Updates an existing student
	 * 
	 * @param student - student's new parameters
	 */
	public void updateStudent(Student student);
	
	/**
	 * Deletes an existing student from its id
	 * 
	 * @param id - student's id
	 */
	public void deleteStudent(Long id);
	
	/**
	 * Deletes all students
	 * 
	 */
	public void deleteAllStudents();
	
	/**
	 * Gets a student from its user
	 * 
	 * @param user - student's user
	 * @return StudentDTO - the selected student
	 */
	public Student findByUser(User user);
}
