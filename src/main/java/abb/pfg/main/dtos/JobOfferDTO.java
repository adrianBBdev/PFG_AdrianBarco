package abb.pfg.main.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Adrian Barco Barona
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDTO {
	
	private Long jobOfferId;
	
	private String title;
	
	private String description;
	
	private int vacancies;
	
	private int duration;
	
	private CompanyDTO company;
}
