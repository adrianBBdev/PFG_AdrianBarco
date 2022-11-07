package abb.pfg.main.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class which represents a job offer in the web app
 * 
 * @author Adrian Barco Barona
 *
 */

@Entity
@Table(name="tbl_offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobOffer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long jobOfferId;
	
	@Column(length=40, nullable=false)
	private String title;
	
	@Column(length=500, nullable=false)
	private String description;
	
	@Positive
	@Column(nullable=false)
	private int vacancies;		//Number of vacancies
	
	@Positive
	private int duration;		//Months duration
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="companyId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Company company;
	
	public JobOffer(String title, String description, int vacancies, int duration, Company company) {
		this.title = title;
		this.description = description;
		this.vacancies = vacancies;
		this.duration = duration;
		this.company = company;
	}

}
