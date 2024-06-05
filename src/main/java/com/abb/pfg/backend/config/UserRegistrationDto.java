package com.abb.pfg.backend.config;

import com.abb.pfg.backend.dtos.CompanyDto;
import com.abb.pfg.backend.dtos.StudentDto;
import com.abb.pfg.backend.dtos.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer Object to sign up students
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
public class UserRegistrationDto {
	private UserDto userDto;
	private String roleName;
	private StudentDto studentDto;
	private CompanyDto companyDto;
}
