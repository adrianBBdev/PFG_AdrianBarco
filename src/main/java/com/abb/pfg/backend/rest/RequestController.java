package com.abb.pfg.backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import com.abb.pfg.backend.commons.RequestState;
import com.abb.pfg.backend.dtos.RequestDto;
import com.abb.pfg.backend.entities.Request;
import com.abb.pfg.backend.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the request objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@Slf4j
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
	public Page<Request> getAllRequestsByJobOfferAndStudentAndRequestState(@RequestParam(required=false) Long jobOfferId,
			@RequestParam(required=false) Long studentId,
			@RequestParam(required=false) RequestState requestState,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="3") Integer size) {
		log.trace("Call controller method getAll()");
		var pageable = PageRequest.of(page, size);
		var requestPage = requestService.listAllRequestsByJobOfferAndStudentAndRequestState(jobOfferId,
				studentId, requestState, pageable);
		return requestPage;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets a specific request from its id")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/{id}")
	public RequestDto getRequest(@PathVariable("id") Long id) {
		log.trace("Call controller method getRequest() with params: {}", id);
		return requestService.getRequest(id);
	}

	@PreAuthorize("hasAnyAuthority('STUDENT')")
	@Operation(method="POST", description="Creates a new request")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createRequest(@RequestBody RequestDto requestDto) {
		log.trace("Call controller method createChat() with params: {}", requestDto.getId());
		requestService.createRequest(requestDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="PUT", description="Updates an existing request")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateRequest(@RequestBody RequestDto requestDto) {
		log.trace("Call controller method updateChat() with params: {}", requestDto.getId());
		requestService.updateRequest(requestDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="DELETE", description="Deletes a list of requests")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRequests(@RequestBody List<Request> requests) {
		log.trace("Call controller method deleteChat() with params", requests.size());
		requestService.deleteRequests(requests);
	}
}
