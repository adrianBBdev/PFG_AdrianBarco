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
import com.abb.pfg.backend.commons.RequestStatus;
import com.abb.pfg.backend.config.RequestCreationDto;
import com.abb.pfg.backend.dtos.RequestDto;
import com.abb.pfg.backend.entities.Request;
import com.abb.pfg.backend.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

/**
 * Controller associated with the request objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.Requests.PATH)
@Tag(name="RequestController", description="Controller to manage the requests of the web app")
public class RequestController {

	@Autowired
	private RequestService requestService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets all requests or filter by parameters")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Request> getAllRequestsByJobOfferAndStudentAndRequestStatus(@RequestParam(required=false) Long offerCode,
			@RequestParam(required=false) String userId,
			@RequestParam(required=false) String name,
			@RequestParam(required=false) RequestStatus requestStatus,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="3") Integer size) {
		var pageable = PageRequest.of(page, size);
		var requestPage = requestService.listAllRequestsByJobOfferAndStudentAndRequestStatus(offerCode,
				userId, name, requestStatus, pageable);
		return requestPage;
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets a specific request from its request code")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/request",produces=MediaType.APPLICATION_JSON_VALUE)
	public RequestDto getRequestByRequestCode(@RequestParam(required=true) Long requestCode) {
		return requestService.getRequest(requestCode);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="POST", description="Creates a new request")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createRequest(@RequestBody RequestCreationDto requestDto) {
		requestService.createRequest(requestDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="PUT", description="Updates an existing request")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateRequest(@RequestBody RequestDto requestDto) {
		requestService.updateRequest(requestDto);
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="DELETE", description="Deletes a list of requests")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteRequest(@RequestParam Long requestCode) {
		requestService.deleteRequest(requestCode);
	}
}
