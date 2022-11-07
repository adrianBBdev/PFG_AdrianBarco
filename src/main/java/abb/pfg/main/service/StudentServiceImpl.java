package abb.pfg.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import abb.pfg.main.entitys.Student;
import abb.pfg.main.entitys.User;
import abb.pfg.main.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Adrian Barco Barona
 *
 */

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	
	private final StudentRepository studentRepository;
	
	@Override
	public List<Student> listAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudent(Long id) {
		Optional<Student> optionalStudent = studentRepository.findById(id);
		Student studentDB = optionalStudent.isPresent() ? optionalStudent.get() : null;
		return studentDB;
	}

	@Override
	public Student createStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student updateStudent(Student student) {
		Student studentDB = getStudent(student.getStudentId());
		if(studentDB == null) {
			return null;
		}
		studentDB.setName(student.getName());
		studentDB.setSurname(student.getSurname());
		studentDB.setDni(student.getDni());
		studentDB.setDescription(student.getDescription());
		studentDB.setEmail(student.getEmail());
		studentDB.setStudies(student.getStudies());
		studentDB.setUser(student.getUser());
		return studentRepository.save(studentDB);
	}

	@Override
	public void deleteStudent(Long id) {
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
		}
	}

	@Override
	public Student findByUser(User user) {
		return studentRepository.findByUser(user);
	}

}
