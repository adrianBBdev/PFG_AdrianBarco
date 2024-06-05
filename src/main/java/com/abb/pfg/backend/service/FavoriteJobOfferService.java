package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.FavoriteJobOfferDto;
import com.abb.pfg.backend.entities.FavoriteJobOffer;
import com.abb.pfg.backend.repositories.FavoriteJobOfferRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the favorite job offers
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class FavoriteJobOfferService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private FavoriteJobOfferRepository favoriteJobOfferRepository;
	
	/**
	 * Gets all favorite job offers by student
	 *
	 * @param jobOfferId - job offer id
	 * @param studentId - student id
	 * @param requestState - state of the request
	 * @param pageable - requests pageable
	 * @return Page - list of requests
	 */
	public Page<FavoriteJobOffer> listAllFavoriteJobOffersByStudent(String username, Pageable pageable) {
		var favoritesPage = favoriteJobOfferRepository.findByStudent_Username(username, pageable);
		return favoritesPage;
	}
	
	/**
	 * Gets a specific favorite job offer from the username and offer code
	 * 
	 * @param username - user's username
	 * @param offerCode - offer code
	 * @return FavoriteJobOfferDto - specified favorite job offers
	 */
	public FavoriteJobOfferDto getFavoriteJobOffer(String username, Long id) {
		var favoriteJobOffer = favoriteJobOfferRepository.findByStudent_UsernameAndJobOfferId(username, id);
		if(favoriteJobOffer.isPresent()) {
			return convertToDto(favoriteJobOffer.get());
		}
		return null;
	}
	
	/**
	 * Creates a new favorite job offer
	 *
	 * @param favoriteJobOfferDto - the new jobOfferDto
	 */
	public void createFavoriteJobOffer(FavoriteJobOfferDto favoriteJobOfferDto) {
		favoriteJobOfferRepository.save(convertToEntity(favoriteJobOfferDto));
	}
	
	 /**
	 * Deletes all provided favorite job offers
	 *
	 * @param requests - list of requests to delete
	 */
	public void deleteFavoriteJobOffer(Long id) {
		favoriteJobOfferRepository.deleteById(id);
		log.debug("The request does not exist");
	}
	
	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param favoriteJobOffer - entity to convert
	 * @return favoriteJobOfferDto - data transfer object converted
	 */
	private FavoriteJobOfferDto convertToDto(FavoriteJobOffer favoriteJobOffer) {
		try {
			return modelMapper.map(favoriteJobOffer, FavoriteJobOfferDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param favoriteJobOfferDto - data transfer object to convert
	 * @return favoriteJobOffer - entity converted
	 */
	private FavoriteJobOffer convertToEntity(FavoriteJobOfferDto favoriteJobOfferDto) {
		try {
			return modelMapper.map(favoriteJobOfferDto, FavoriteJobOffer.class);
		} catch(Exception e) {
			return null;
		}
	}
}
