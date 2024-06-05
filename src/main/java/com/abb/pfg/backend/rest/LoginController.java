package com.abb.pfg.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.config.JwtUtils;
import com.abb.pfg.backend.config.LoginResponse;
import com.abb.pfg.backend.config.UserRegistrationDto;
import com.abb.pfg.backend.dtos.CompanyDto;
import com.abb.pfg.backend.dtos.StudentDto;
import com.abb.pfg.backend.dtos.UserDto;
import com.abb.pfg.backend.service.CompanyService;
import com.abb.pfg.backend.service.StudentService;
import com.abb.pfg.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller associated with the login of the web app
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.Authorization.PATH)
public class LoginController {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CompanyService companyService;

	@Operation(method="POST", description="Log in to an existing user")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/login")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginResponse login(@RequestParam(required=false) String username, @RequestParam(required=false) String password) {
		if(username!= null && password !=null) {
			var role = userService.getUserByUsername(username).getRole().getName();
			var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        return new LoginResponse(jwtUtils.generateToken(authentication), role);
		}
        return new LoginResponse(jwtUtils.generateGuestToken(), "GUEST");
    }

	@Operation(method="POST", description="Signs up a new user")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/signUp")
	@ResponseStatus(HttpStatus.CREATED)
	public void signUp(@RequestBody UserRegistrationDto userRegistrationDto) {
		userService.createUser(userRegistrationDto);
	}
	
	@Operation(method="GET", description="Gets a specific user from its username")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/user", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUser(@RequestParam(required=true) String username) {
		return userService.getUserByUsername(username);
	}
	
	@Operation(method="GET", description="Gets a specific student from its dni")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/student", produces=MediaType.APPLICATION_JSON_VALUE)
	public StudentDto getStudentByDni(@RequestParam(required=true) String dni) {
		return studentService.getStudentByDni(dni);
	}

	@Operation(method="GET", description="Gets a specific company from its cif")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/company", produces=MediaType.APPLICATION_JSON_VALUE)
	public CompanyDto getCompanyByCif(@RequestParam(required=true) String cif) {
		return companyService.getCompanyByCif(cif);
	}
	
	@Operation(method="GET", description="Gets authorization if the token is valid")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public boolean isTokenValid(@RequestHeader("Authorization") String authtoken) {
		if(jwtUtils.validateAndParseToken(authtoken) != null) {
			return true;
		}
		return false;
	}
}
