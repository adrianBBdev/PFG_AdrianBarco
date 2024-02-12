
package com.abb.pfg.backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.StudentDto;
import com.abb.pfg.backend.entities.Student;
import com.abb.pfg.backend.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which represents the students' controller
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Students.PATH)
@Tag(name="StudentController", description="Controller to manage the students of the web app")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Operation(method="GET", description="Gets all students")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<Student> getAllStudentsByNameAndStudiesAndUser(@RequestParam(required=false) String name, @RequestParam(required=false) String studies, 
			@RequestParam(required=false) Long id,@RequestParam(defaultValue="0") Integer page, 
			@RequestParam(defaultValue="3") Integer size) {
		log.trace("Call controller method getAllStudentsByNameAndStudiesAndUser() with params: {}. {}", page, size);
		var pageable = PageRequest.of(page, size);
		var pageStudent = studentService.listAllStudentsByNameAndStudiesAndUser(name, studies, id, pageable);
		log.debug("List of students found: {}", pageStudent.getNumberOfElements());
		return pageStudent;
	}
	
	@Operation(method="GET", description="Gets a specific student from its id")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public StudentDto getStudent(@PathVariable("id") Long id) {
		log.trace("Call controller method getStudent() with params: {}", id);
		return studentService.getStudent(id);
	}
	
	@Operation(method="POST", description="Creates a new student")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createStudent(@RequestBody StudentDto studentDto) {
		log.trace("Call controller method createStudent() with params: {}", studentDto.getId());
		studentService.createStudent(studentDto);
	}
	
	@Operation(method="PUT", description="Updates an existing student")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateStudent(@RequestBody StudentDto studentDto) {
		log.trace("Call controller method updateStudent() with params: {}", studentDto.getId());
		studentService.updateStudent(studentDto);
	}
	
	@Operation(method="DELETE", description="Deletes a list of selected students")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStudents(@RequestBody List<Student> students) {
		log.trace("Call controller method deleteStudents() with params: {}", students.size());
		studentService.deleteStudents(students);
	}
}
