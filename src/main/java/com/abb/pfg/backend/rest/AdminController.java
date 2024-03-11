package com.abb.pfg.backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.AdministratorDto;
import com.abb.pfg.backend.entities.Administrator;
import com.abb.pfg.backend.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the administrator objects
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Adminis.PATH)
@Tag(name="AdminsController", description="Controller to manage the admins of the web app")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Operation(method="GET", description="Gets all admins")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<Administrator> getAllAdmins(@RequestParam(defaultValue="0") Integer page, @RequestParam(defaultValue="3") Integer size) {
		log.trace("Call controller method getAllAdmins()");
		var pageable = PageRequest.of(page, size);
		var pageAdmin = adminService.listAllAdmins(pageable);
		log.debug("List of admins found: ´}", pageAdmin.getNumberOfElements());
		return pageAdmin;
	}
	
	@Operation(method="GET", description="Gets a specific admin from its id")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public AdministratorDto getAdmin(@PathVariable("id") Long id) {
		log.trace("Call controller method getAdmin() with params: {}", id);
		return adminService.getAdmin(id);
	}
	
	@Operation(method="POST", description="Creates a new admin")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createAdmin(@RequestBody AdministratorDto adminDto) {
		log.trace("Call controller method createAdmin() with params: {}", adminDto.getId());
		adminService.createAdmin(adminDto);
	}
	
	@Operation(method="PUT", description="Updates an existing admin")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping()
	public void updateAdmin(@RequestBody AdministratorDto adminDto) {
		log.trace("Call controller mehtod updateAdmin() with params: {}", adminDto.getId());
		adminService.updateAdmin(adminDto);
	}
	
	@Operation(method="DELETE", description="Deletes a list of specified admins")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping()
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAdmins(@RequestBody List<Administrator> admins) {
		log.trace("Call controller method deleteAdmins() with params: {}", admins.size());
		adminService.deleteAdmins(admins);
	}
}
