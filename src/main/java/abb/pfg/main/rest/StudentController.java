package abb.pfg.main.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import abb.pfg.main.entitys.Student;
import abb.pfg.main.service.StudentService;

/**
 * Controller to handle students
 * 
 * @author Adrian Barco Barona
 *
 */

@RestController
@RequestMapping(value="/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public ResponseEntity<List<Student>> listStudent() {
		List<Student> students = new ArrayList<>();
	
		students = studentService.listAllStudents();
		if(students.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(students);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable("id") Long id){
		Student studentDB = studentService.getStudent(id);
		
		if(studentDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(studentDB);
	}
	
	@PostMapping
	public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
		if(studentService.listAllStudents().contains(student)) {
			return ResponseEntity.badRequest().build();
		}
		Student createdStudent = studentService.createStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
		//return ResponseEntity.ok(createdStudent);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
		student.setStudentId(id);
		Student studentDB = studentService.updateStudent(student);
		if(studentDB == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(studentDB);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
	}
}
