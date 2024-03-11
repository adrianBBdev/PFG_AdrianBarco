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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.ResourceDto;
import com.abb.pfg.backend.entities.Resource;
import com.abb.pfg.backend.service.ResourceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the resource objects
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE')")
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Resource.PATH)
@Tag(name="ResourceController", description="Controller to manage the resource files of the web app")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@Operation(method="GET", description="Gets all resource files or filter by parameters")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Resource> getAllResourcesByJobOffer(@RequestParam(required=false) Long id, 
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="3")Integer size) {
		log.trace("Call controller method getAllResourcesByJobOffer()");
		var pageable = PageRequest.of(page, size);
		var resourcePage = resourceService.listAllResourcesByJobOffer(id, pageable);
		return resourcePage;
	}
	
	@Operation(method="POST", description="Gets an existing resource file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@GetMapping(value="/{id}")
	public ResourceDto getResource(@PathVariable Long id) {
		log.trace("Call controller method getResource() with params: {}", id);
		return resourceService.getResource(id);
	}
	
	@Operation(method="POST", description="Creates a new resource file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createResource(ResourceDto resourceDto) {
		log.trace("Call controller method createResource() with params: {}", resourceDto.getId());
		resourceService.createResource(resourceDto);
	}
	
	@Operation(method="PUT", description="Updates an existing resource file")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateResource(ResourceDto resourceDto) {
		log.trace("Call controller method updateResource() with params: {}", resourceDto.getId());
		resourceService.updateMultimedia(resourceDto);
	}
	
	@Operation(method="DELETE", description="Deletes a list of resource files")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMultimedias(List<Resource> resources) {
		log.trace("Call controller method deleteResources() with params: {}", resources.size());
		resourceService.deleteMultimedias(resources);
	}
}
