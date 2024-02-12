package com.abb.pfg.backend.service;

import java.util.List;

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
 * Class which represnts the student's service
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
	 * @param pageable - student's pageable
	 * @return Page - list all students
	 */
	public Page<Student>listAllStudentsByNameAndStudiesAndUser(String name, String studies, Long id, Pageable pageable) {
		log.trace("Call service method listAllStudentsByNameAndStudiesAndUser()");
		var pageStudents = studentRepository.findByNameAndStudiesAndUser_Id(name, studies, id, pageable);
		log.debug("List of students found: {}", pageStudents.getContent().size());
		return pageStudents;
	}
	
	/**
	 * Gets the student with the requested id
	 * 
	 * @param id - student's id
	 * @return StudentDto - the requested student
	 */
	public StudentDto getStudent(Long id) {
		log.trace("Call service method getStudent() with params: {}", id);
		var optionalStudent = studentRepository.findById(id);
		var student = optionalStudent.isPresent() ? optionalStudent.get() : null;
		log.debug("User found: {}", student.getId());
		return convertToDto(student);
	}
	
	/**
	 * Creates a new student
	 * 
	 * @param studentDto - student that will be created
	 */
	public void createStudent(StudentDto studentDto) {
		log.trace("Call service method createStudent() with params: {}", studentDto.getId());
		if(!studentRepository.existsById(studentDto.getId())) {
			log.debug("New student: {}", studentDto.getId());
			studentRepository.save(convertToEntity(studentDto));
		} else {
			log.debug("The student already exists");
		}
	}
	
	/**
	 * Updates an existing student
	 * 
	 * @param studentDto - the student that will be updated
	 */
	public void updateStudent(StudentDto studentDto) {
		log.trace("Call service method updateStudent() with params: {}", studentDto.getId());
		if(studentRepository.existsById(studentDto.getId())) {
			log.debug("New student: {}", studentDto.getId());
			studentRepository.save(convertToEntity(studentDto));
		} else {
			log.debug("The student already exists");
		}
	}
	
	/**
	 * Deletes all provided students
	 * 
	 * @param students - list of students to delete
	 */
	public void deleteStudents(List<Student> students) {
		log.trace("Call service method deleteStudents() with {} students", students.size());
		studentRepository.deleteAllInBatch(students);
	}
	
	/**
	 * Converts the entity into a data transfer object
	 * 
	 * @param student - entity to convert
	 * @return StudentDto - data transfer object converted
	 */
	private StudentDto convertToDto(Student student) {
		var studentDto = modelMapper.map(student, StudentDto.class);
		return studentDto;
	}
	
	/**
	 * Converts the data transfer object into an entity
	 * 
	 * @param studentDto - data transfer objetct to convert
	 * @return Student - entity converted
	 */
	private Student convertToEntity(StudentDto studentDto) {
		var student = modelMapper.map(studentDto, Student.class);
		return student;
	}
}
