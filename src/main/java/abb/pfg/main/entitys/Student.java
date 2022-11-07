package abb.pfg.main.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
//import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class which represents the student rol in the web app
 * 
 * @author Adrián Barco Barona
 *
 */

@Entity
@Table(name="tbl_students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long studentId;
	
	@Column(length=40, nullable=false)
	private String name;
	
	@Column(length=40, nullable=false)
	private String surname;
	
	@Column(length=50, unique=true, nullable=false)
	@Email
	private String email;
	
	@Column(length=9, unique=true, nullable=false)
	private String dni;
	
	@Column(length=40, nullable=false)
	private String studies;
	
	@Column(length=500, nullable=false)
	private String description;
	
	/*@Lob
	private byte[] data;*/
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	/**
	 * Default constructor for Student
	 * 
	 * @param name - name of the student
	 * @param surname - surname of the student
	 * @param email - email of the student
	 * @param dni - DNI of the student
	 * @param studies - studies of the student
	 * @param description - description of the student
	 * @param user - user assigned to the student
	 */
	public Student(String name, String surname, String email, String dni, String studies, String description, User user) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.dni = dni;
		this.studies = studies;
		this.description = description;
		this.user = user;
	}
}
