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
import com.abb.pfg.backend.dtos.AreaDto;
import com.abb.pfg.backend.entities.Area;
import com.abb.pfg.backend.service.AreaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the area objects
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Areas.PATH)
@Tag(name="AreaController", description="Controller to manage the areas of the web app")
public class AreaController {
	
	@Autowired
	private AreaService areaService;
	
	@Operation(method="GET", description="Gets all aras")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<Area> getAllAreas(@RequestParam(defaultValue="0") Integer page, @RequestParam(defaultValue="3") Integer size) {
		log.trace("Call controller method getAllAreas()");
		var pageable = PageRequest.of(page, size);
		var pageArea = areaService.listAllAreas(pageable);
		log.debug("List of areas found: ´}", pageArea.getNumberOfElements());
		return pageArea;
	}
	
	@Operation(method="GET", description="Gets a specific area from its id")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public AreaDto getArea(@PathVariable("id") Long id) {
		log.trace("Call controller method getArea() with params: {}", id);
		return areaService.getArea(id);
	}
	
	@Operation(method="POST", description="Creates a new area")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createArea(@RequestBody AreaDto areaDto) {
		log.trace("Call controller method createArea() with params: {}", areaDto.getId());
		areaService.createArea(areaDto);
	}
	
	@Operation(method="PUT", description="Updates an existing area")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping()
	public void updateArea(@RequestBody AreaDto areaDto) {
		log.trace("Call controller mehtod updateArea() with params: {}", areaDto.getId());
		areaService.updateArea(areaDto);
	}
	
	@Operation(method="DELETE", description="Deletes a list of specified areas")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping()
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAreas(@RequestBody List<Area> areas) {
		log.trace("Call controller method deleteAreas() with params: {}", areas.size());
		areaService.deleteAreas(areas);
	}
}
