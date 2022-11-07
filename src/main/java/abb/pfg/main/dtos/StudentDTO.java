/**
 * 
 */
package abb.pfg.main.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Adrian Barco Barona
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
	
	private Long studentId;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String dni;
	
	private String studies;
	
	private String description;
	
	private UserDTO user;

}
