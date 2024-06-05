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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.FavoriteJobOfferDto;
import com.abb.pfg.backend.entities.FavoriteJobOffer;
import com.abb.pfg.backend.service.FavoriteJobOfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

/**
 * Controller associated with the favorite job offer objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@RestController
@RequestMapping(value=Constants.Controllers.Favorites.PATH)
@Tag(name="RequestController", description="Controller to manage the favorite job offers of the web app")
public class FavoriteJobOfferController {
	
	@Autowired
	private FavoriteJobOfferService favoriteJobOfferService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets all favorite job offers by student")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<FavoriteJobOffer> getAllFavoriteJobOffersByStudent(@RequestParam(required=false) String username,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="5") Integer size) {
		var pageable = PageRequest.of(page, size);
		var favoriteJobOfferPage = favoriteJobOfferService.listAllFavoriteJobOffersByStudent(username, pageable);
		return favoriteJobOfferPage;
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="GET", description="Gets a favorite job offer from its offer code")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@GetMapping(value="/favorite")
	@ResponseStatus(HttpStatus.OK)
	public FavoriteJobOfferDto getFavorite(@RequestParam(required=true) String username, @RequestParam(required=true) Long offerCode) {
		return favoriteJobOfferService.getFavoriteJobOffer(username, offerCode);
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="POST", description="Creates a new favorite job offer")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createFvorite(@RequestBody FavoriteJobOfferDto favoriteJobOfferDto) {
		favoriteJobOfferService.createFavoriteJobOffer(favoriteJobOfferDto);
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
	@Operation(method="DELETE", description="Deletes a favorite job offer item")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteFavoriteJobOffer(@RequestParam(required=true) Long favoriteCode) {
		favoriteJobOfferService.deleteFavoriteJobOffer(favoriteCode);
	}

}
