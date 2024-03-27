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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.config.JwtUtils;
import com.abb.pfg.backend.config.LoginResponse;
import com.abb.pfg.backend.dtos.CompanyDto;
import com.abb.pfg.backend.dtos.RoleDto;
import com.abb.pfg.backend.dtos.StudentDto;
import com.abb.pfg.backend.dtos.UserDto;
import com.abb.pfg.backend.service.CompanyService;
import com.abb.pfg.backend.service.RoleService;
import com.abb.pfg.backend.service.StudentService;
import com.abb.pfg.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the login of the web app
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@Slf4j
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
	private RoleService roleService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CompanyService companyService;

	@Operation(method="POST", description="Log in to an existing user")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/login")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        log.info("Call controller method login() with params: {}, {}", username, password);
        return new LoginResponse(jwtUtils.generateToken(authentication));
    }

	@Operation(method="POST", description="Signs up a new user")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/signUp")
	@ResponseStatus(HttpStatus.CREATED)
	public void signUp(@RequestBody UserDto userDto) {
		log.trace("Call controller method signUp with params: {}", userDto.getUsername());
		userService.createUser(userDto);
    }

	@Operation(method="GET", description="Gets a specific role")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@GetMapping(value="/role",produces=MediaType.APPLICATION_JSON_VALUE)
	public RoleDto getRole(@RequestParam String name) {
		log.trace("Call controller method getRole with params: {}", name);
		return roleService.getRoleByName(name);
	}

	@Operation(method="GET", description="Gets a specific user from its username")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/user", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUserByUsername(@RequestParam(required=true) String username) {
		log.trace("Call controller method getUserByUsername() with params: {}", username);
		return userService.getUserByUsername(username);
	}
	@Operation(method="GET", description="Gets a specific student from its dni")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/student", produces=MediaType.APPLICATION_JSON_VALUE)
	public StudentDto getStudentByDni(@RequestParam(required=true) String dni) {
		log.trace("Call controller method getStudentByDni() with params: {}", dni);
		return studentService.getStudentByDni(dni);
	}

	@Operation(method="POST", description="Signs up a new student")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/student")
	@ResponseStatus(HttpStatus.CREATED)
	public void signUpStudent(@RequestBody StudentDto studentDto) {
		log.trace("Call controller method signUpStudent() with params: {}", studentDto.getName());
		studentService.createStudent(studentDto);
	}

	@Operation(method="POST", description="Signs up a new company")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/company")
	@ResponseStatus(HttpStatus.CREATED)
	public void signUpCompany(@RequestBody CompanyDto companyDto) {
		log.trace("Call controller method signUpCompany() with params: {}", companyDto.getName());
		companyService.createCompany(companyDto);
	}

	@Operation(method="GET", description="Gets a specific company from its cif")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/company", produces=MediaType.APPLICATION_JSON_VALUE)
	public CompanyDto getCompanyByCif(@RequestParam(required=true) String cif) {
		log.trace("Call controller method getCompanyByCif() with params: {}", cif);
		return companyService.getCompanyByCif(cif);
	}
}
