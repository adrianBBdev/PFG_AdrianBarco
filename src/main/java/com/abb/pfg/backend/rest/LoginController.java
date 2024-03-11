package com.abb.pfg.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.config.JwtUtils;
import com.abb.pfg.backend.config.LoginResponse;

import io.swagger.v3.oas.annotations.Operation;
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
	JwtUtils jwtUtils; 
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Operation(method="POST", description="Log in to an existing user")
	@ApiResponses(value = {@ApiResponse(responseCode="201", description="Created")})
	@PostMapping(value="/login")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        log.info("Call controller method login() with params: {}, {}", username, password);
        return new LoginResponse(jwtUtils.generateToken(authentication));
    }}
