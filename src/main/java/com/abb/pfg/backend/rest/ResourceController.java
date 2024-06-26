package com.abb.pfg.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import jakarta.transaction.Transactional;

/**
 * Controller associated with the resource objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.Resource.PATH)
@Tag(name="ResourceController", description="Controller to manage the resource files of the web app")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets all resource files or filter by parameters")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Resource> getAllResourcesByJobOfferIdAndName(@RequestParam(required=false) Long jobOfferCode,
			@RequestParam(required=false) String name,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="3")Integer size) {
		var pageable = PageRequest.of(page, size);
		var resourcePage = resourceService.listAllResourcesByJobOfferIdAndName(jobOfferCode, name, pageable);
		return resourcePage;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets an existing resource file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@GetMapping(value="/resource")
	public ResourceDto getResource(@RequestParam(required=true) Long resourceCode) {
		return resourceService.getResource(resourceCode);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="POST", description="Creates a new resource file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createResource(@RequestBody ResourceDto resourceDto) {
		resourceService.createResource(resourceDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="PUT", description="Updates an existing resource file")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateResource(@RequestBody ResourceDto resourceDto) {
		resourceService.updateResource(resourceDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','COMPANY')")
	@Operation(method="DELETE", description="Deletes a list of resource files")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteMultimedias(@RequestParam(required=true) Long code) {
		resourceService.deleteResource(code);
	}
}
