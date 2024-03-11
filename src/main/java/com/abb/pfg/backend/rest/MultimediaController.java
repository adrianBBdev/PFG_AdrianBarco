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
import com.abb.pfg.backend.dtos.MultimediaDto;
import com.abb.pfg.backend.entities.Multimedia;
import com.abb.pfg.backend.service.MultimediaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the multimedia objects
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Multimedia.PATH)
@Tag(name="MultimediaController", description="Controller to manage the multimedia files of the web app")
public class MultimediaController {
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Operation(method="GET", description="Gets all multimedia files or filter by parameters")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Multimedia> getAllMultimediaByUser(@RequestParam(required=false) Long id, 
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="0")Integer size) {
		log.trace("Call controller method getAllMultimediasByUser()");
		var pageable = PageRequest.of(page, size);
		var multimediaPage = multimediaService.listAllMultimediaByUser(id, pageable);
		return multimediaPage;
	}
	
	@Operation(method="POST", description="Gets an existing multimedia file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@GetMapping(value="/{id}")
	public MultimediaDto getMultimedia(@PathVariable Long id) {
		log.trace("Call controller method getMultimedia() with params: {}", id);
		return multimediaService.getMultimedia(id);
	}
	
	@Operation(method="POST", description="Creates a new multimedia file")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createMultimedia(MultimediaDto multimediaDto) {
		log.trace("Call controller method createMultimedia() with params: {}", multimediaDto.getId());
		multimediaService.createMultimedia(multimediaDto);
	}
	
	@Operation(method="PUT", description="Updates an existing multimedia file")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateMultimeida(MultimediaDto multimediaDto) {
		log.trace("Call controller method updateMultimedia() with params: {}", multimediaDto.getId());
		multimediaService.updateMultimedia(multimediaDto);
	}
	
	@Operation(method="DELETE", description="Deletes a list of multimedia files")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMultimedias(List<Multimedia> multimedias) {
		log.trace("Call controller method deleteMultimedias() with params: {}", multimedias.size());
		multimediaService.deleteMultimedias(multimedias);
	}
}
