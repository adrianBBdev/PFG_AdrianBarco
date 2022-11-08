package abb.pfg.main.entities;

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
 * Class which represents the Company Role in the web app
 * 
 * @author Adrián Barco Barona
 *
 */

@Entity
@Table(name="tbl_companys")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long companyId;
	
	@Column(length=40, nullable=false)
	private String name;
	
	@Column(length=50, unique=true, nullable=false)
	@Email
	private String email;
	
	@Column(length=9, unique=true, nullable=false)
	private String cif;
	
	@Column(length=500, nullable=false)
	private String description;
	
	/*@Lob
	private byte[] data;*/
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	/**
	 * Default constructor for Company
	 * 
	 * @param name - name of the company
	 * @param email - email of the company
	 * @param cif - CIF of the company
	 * @param description - description of the company
	 * @param user - user assigned to the company
	 */
	public Company(String name, String email, String cif, String description, User user) {
		this.name = name;
		this.email = email;
		this.cif = cif;
		this.description = description;
		this.user = user;
	}
}
