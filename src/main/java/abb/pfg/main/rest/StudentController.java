package abb.pfg.main.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import abb.pfg.main.commons.Constants;
import abb.pfg.main.entities.Student;
import abb.pfg.main.service.StudentService;
import io.swagger.models.HttpMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle students
 * 
 * @author Adrian Barco Barona
 *
 */

@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Students.PATH)
@Tag(name="StudentController", description="Controller to manage the students of the web app")
public class StudentController {
	
	private StudentService studentService;
	
	/**
	 * Default constructor
	 * 
	 * @param studentService - student's service interface
	 */
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	/**
	 * Gets all students
	 * 
	 * @return List of students
	 */
	@Operation(method="GET", description="Gets all students")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Student> listStudent() {
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.GET.name()));
		List<Student> students = new ArrayList<>();
		students = studentService.listAllStudents();
		return students;
	}
	
	/**
	 * Gets a specific student from its id
	 * 
	 * @param id - student's id
	 * @return a student
	 */
	@Operation(method="GET", description="Gets a specific student from its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Student getStudent(@PathVariable("id") Long id){
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.GET.name()));
		Student studentDB = studentService.getStudent(id);
		return studentDB;
	}
	
	/**
	 * Creates a new student
	 * 
	 * @param student - new student to create
	 */
	@Operation(method="POST", description="Creates a new student")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Created") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createStudent(@Valid @RequestBody Student student) {
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.POST.name()));
		studentService.createStudent(student);
	}
	
	/**
	 * Updates an existing student from its id
	 * 
	 * @param id - student's id
	 * @param student - stuednt's parameters to update
	 */
	@Operation(method="PUT", description="Updates an existing student from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok") })
	@PutMapping(value="/{id}")
	public void updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
		log.trace(String.format(Constants.Controllers.Students.PATH, HttpMethod.PUT.name()));
		student.setStudentId(id);
		studentService.updateStudent(student);
	}
	
	@Operation(method="DELETE", description="Deletes a specific student from its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStudent(@PathVariable Long id) {
		log.trace(String.format(Constants.Controllers.Students.PATH), HttpMethod.DELETE.name());
		studentService.deleteStudent(id);
	}
	
	@Operation(method="DELETE", description="Deletes all students")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllStudents() {
		log.trace(String.format(Constants.Controllers.Students.PATH), HttpMethod.DELETE.name());
		studentService.deleteAllStudents();
	}
}
