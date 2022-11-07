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
public class CompanyDTO {
	
	private Long companyId;
	
	private String name;
	
	private String email;
	
	private String cif;
	
	private String description;
	
	private UserDTO user;
}
