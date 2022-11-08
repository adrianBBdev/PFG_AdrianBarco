/**
 * 
 */
package abb.pfg.main.dtos;

import abb.pfg.main.entities.Role;
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
public class UserDTO {

	private Long userId;
	
	private String username;
	
	private String password;
	
	private Role role;
}
