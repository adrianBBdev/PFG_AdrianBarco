package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.StudentDto;
import com.abb.pfg.backend.entities.Student;
import com.abb.pfg.backend.repositories.StudentRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the students
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class StudentService {

	@Autowired
    private ModelMapper modelMapper;


	@Autowired
	private StudentRepository studentRepository;

	/**
	 * Gets all students
	 *
	 * @param pageable - students pageable
	 * @return Page - list all students
	 */
	public Page<Student>listAllStudentsByNameAndStudiesAndUser(String name, String studies, Long id, Pageable pageable) {
		var pageStudents = studentRepository.findByNameAndStudiesAndUser_Id(name, studies, id, pageable);
		return pageStudents;
	}
	
	/**
	 * Gets the student with the requested username
	 *
	 * @param username - student username
	 * @param dni - student's dni
	 * @return StudentDto - the requested student
	 */
	public StudentDto getStudentByUsernameAndDni(String username, String dni) {
		if(username == null) {
			return getStudentByDni(dni);
		}
		var optionalStudent = studentRepository.findByUsername(username);
		var student = optionalStudent.isPresent() ? optionalStudent.get() : null;
		return convertToDto(student);
	}
	

	/**
	 * Gets the student with the requested dni
	 *
	 * @param dni - student dni
	 * @return StudentDto - the requested student
	 */
	public StudentDto getStudentByDni(String dni) {
		var optionalStudent = studentRepository.findByDni(dni);
		var student = optionalStudent.isPresent() ? optionalStudent.get() : null;
		return convertToDto(student);
	}

	/**
	 * Creates a new student
	 *
	 * @param studentDto - student that will be created
	 */
	public void createStudent(StudentDto studentDto) {
		if(!studentRepository.existsByDni(studentDto.getDni())) {
			studentRepository.save(convertToEntity(studentDto));
			return;
		}
		log.debug("The student already exists");
	}

	/**
	 * Updates an existing student
	 *
	 * @param studentDto - the student that will be updated
	 */
	public void updateStudent(StudentDto studentDto) {
		if(studentRepository.existsByDni(studentDto.getDni())) {
			studentRepository.save(convertToEntity(studentDto));
			return;
		}
		log.debug("The student does not exist");
	}

	/**
	 * Deletes all provided students
	 *
	 * @param students - list of students to delete
	 */
	public void deleteStudent(String username) {
		var user = studentRepository.findByUsername(username).get().getUser();
		studentRepository.deleteByUser(user);
	}

	/**
	 * Converts the entity into a data transfer object
	 *
	 * @param student - entity to convert
	 * @return StudentDto - data transfer object converted
	 */
	private StudentDto convertToDto(Student student) {
		try {
			return modelMapper.map(student, StudentDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts the data transfer object into an entity
	 *
	 * @param studentDto - data transfer object to convert
	 * @return Student - entity converted
	 */
	public Student convertToEntity(StudentDto studentDto) {
		try {
			return modelMapper.map(studentDto, Student.class);
		} catch(Exception e) {
			return null;
		}
	}
}
