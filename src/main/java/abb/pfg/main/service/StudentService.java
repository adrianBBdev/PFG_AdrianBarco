package abb.pfg.main.service;

import java.util.List;

import abb.pfg.main.entitys.Student;
import abb.pfg.main.entitys.User;

public interface StudentService {
	public List<Student> listAllStudents();
	public Student getStudent(Long id);
	public Student createStudent(Student student);
	public Student updateStudent(Student student);
	public void deleteStudent(Long id);
	public Student findByUser(User user);
}
