package abb.pfg.main.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Class which represents a user of the web app
 * 
 * @author Adrián Barco Barona
 *
 */

@Entity
@Table(name="tbl_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long userId;
	
	@Column(length=20, unique=true, nullable=false)
	private String username; 
	
	@Column(length=20,  nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Lob
	@Type(type="org.hibernate.type.ImageType")
	private byte[] profilePicture;
	
	/**
	 * Default constructor for user
	 * 
	 * @param user_name - username of the user
	 * @param password - password of the user
	 * @param role - role of the user
	 * @param profilePictrue - profile picture of the user
	 */
	public User(String username, String password, Role role, byte[] profilePicture) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.profilePicture = profilePicture;
	}
}
