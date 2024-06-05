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
import com.abb.pfg.backend.dtos.MultimediaDto;
import com.abb.pfg.backend.entities.Multimedia;
import com.abb.pfg.backend.service.MultimediaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

/**
 * Controller associated with the multimedia objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.Multimedia.PATH)
@Tag(name="MultimediaController", description="Controller to manage the multimedia files of the web app")
public class MultimediaController {

	@Autowired
	private MultimediaService multimediaService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets all multimedia files or filter by parameters")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Multimedia> getAllMultimediaByUser(@RequestParam(required=false) String name,
			@RequestParam(required=false) String username,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="5")Integer size) {
		var pageable = PageRequest.of(page, size);
		var multimediaPage = multimediaService.listAllMultimediaByUser(name, username, pageable);
		return multimediaPage;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets an existing multimedia file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@GetMapping(value="/media")
	public MultimediaDto getMultimedia(@RequestParam(required=true) Long mediaCode) {
		return multimediaService.getMultimedia(mediaCode);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="POST", description="Creates a new multimedia file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createMultimedia(@RequestBody MultimediaDto multimediaDto) {
		multimediaService.createMultimedia(multimediaDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="PUT", description="Updates an existing multimedia file")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateMultimeida(@RequestBody MultimediaDto multimediaDto) {
		multimediaService.updateMultimedia(multimediaDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="DELETE", description="Deletes a list of multimedia files")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteMultimedia(@RequestParam(required=true) Long code) {
		multimediaService.deleteMultimedia(code);
	}
}
