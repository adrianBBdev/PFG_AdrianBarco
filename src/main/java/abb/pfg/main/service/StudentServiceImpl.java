package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import abb.pfg.main.entities.Student;
import abb.pfg.main.entities.User;
import abb.pfg.main.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
	
	private final StudentRepository studentRepository;
	
	/**
	 * Default constructor
	 * 
	 * @param studentRepository - JPA students' repository
	 */
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Override
	public List<Student> listAllStudents() {
		log.trace("Call service method listAllStudents");
		return studentRepository.findAll();
	}

	@Override
	public Student getStudent(Long id) {
		log.trace("Call service method getStudent with params: {}", id);
		Optional<Student> optionalStudent = studentRepository.findById(id);
		Student studentDB = optionalStudent.isPresent() ? optionalStudent.get() : null;
		log.debug("Found student: {}" + studentDB.getEmail());
		return studentDB;
	}

	@Override
	public void createStudent(Student student) {
		log.trace("Call service method createStudent with params: {}", student);
		studentRepository.save(student);
	}

	@Override
	public void updateStudent(Student student) {
		log.trace("Call service method updateStudent with params: {}", student);
		Student studentDB = getStudent(student.getStudentId());
		/*if(studentDB == null) {
			return null;
		}*/
		studentDB.setName(student.getName());
		studentDB.setSurname(student.getSurname());
		studentDB.setDni(student.getDni());
		studentDB.setDescription(student.getDescription());
		studentDB.setEmail(student.getEmail());
		studentDB.setStudies(student.getStudies());
		studentDB.setUser(student.getUser());
		studentRepository.save(studentDB);
	}

	@Override
	public void deleteStudent(Long id) {
		log.trace("Called service method deleteStudent with params: {}", id);
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
		}
	}
	
	@Override
	public void deleteAllStudents() {
		log.trace("Called service method deleteAllStudents");
		studentRepository.deleteAll();
	}

	@Override
	public Student findByUser(User user) {
		log.trace("Called service method findByUser with params: {}", user);
		return studentRepository.findByUser(user);
	}
}
