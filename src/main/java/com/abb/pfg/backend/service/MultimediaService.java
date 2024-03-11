package com.abb.pfg.backend.service;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.MultimediaDto;
import com.abb.pfg.backend.entities.Multimedia;
import com.abb.pfg.backend.repositories.MultimediaRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the multimedia objects
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class MultimediaService{

	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private MultimediaRepository multimediaRepository;
	
	/**
	 * Finds all multimedia content or filter by owner
	 * 
	 * @param id - user id
	 * @param pageable - multimedia objects pageable
	 * @return Page - list of multimedia
	 */
	public Page<Multimedia> listAllMultimediaByUser(Long id, Pageable pageable) {
		log.trace("Call service method listAllMultimediaByUser()");
		var multimediaPage = multimediaRepository.findByUser_Id(id, pageable);
		log.debug("List of Multimedia found: {}", multimediaPage.getNumberOfElements());
		return multimediaPage;
	}
	
	/**
	 * Gets a specific Multimedia from its id
	 * 
	 * @param id - multimedia's id
	 * @return MultimediaDto - the requested multimedia
	 */
	public MultimediaDto getMultimedia(Long id) {
		log.trace("Call service method getMultimedia() with params: {}", id);
		var optionalMultimedia = multimediaRepository.findById(id);
		var multimedia = optionalMultimedia.isPresent() ? optionalMultimedia.get() : null;
		log.debug("Multimedia found: {}", multimedia.getId());
		return convertToDto(multimedia);
	}
	
	/**
	 * Creates a new multimedia file
	 * 
	 * @param multimediaDto - the new multimedia file
	 */
	public void createMultimedia(MultimediaDto multimediaDto) {
		log.trace("Call service method createMultimedia() with params: {}", multimediaDto.getId());
		if(!multimediaRepository.existsById(multimediaDto.getId())) {
			log.debug("New multimedia file: {}", multimediaDto.getId());
			multimediaRepository.save(convertToEntity(multimediaDto));
		} else {
			log.debug("The multimedia file already exists");
		}
	}
	
	/**
	 * Updates an existing multimedia file
	 * 
	 * @param multimediaDto - multimedia to update
	 */
	public void updateMultimedia(MultimediaDto multimediaDto) {
		log.trace("Call service method updateMultimedia() with params: {}", multimediaDto.getId());
		if(multimediaRepository.existsById(multimediaDto.getId())) {
			log.debug("Updated multimedia file: {}", multimediaDto.getId());
			multimediaRepository.save(convertToEntity(multimediaDto));
		} else {
			log.debug("The multimedia file already exists");
		}
	}
	
	/**
	 * Deletes a list of multimedia files
	 * 
	 * @param multimedias - list of multimedia files
	 */
	public void deleteMultimedias(List<Multimedia> multimedias) {
		log.trace("Call service method deleteMultimedias() with params: {}", multimedias.size());
		multimediaRepository.deleteAllInBatch(multimedias);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param multimedia - entity to convert
	 * @return MultimediaDto - data transfer object converted
	 */
	private MultimediaDto convertToDto(Multimedia multimedia) {
		var multimediaDto = modelMapper.map(multimedia, MultimediaDto.class);
		return multimediaDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param multimediaDto - data transfer object to convert
	 * @return Multimedia - entity converted
	 */
	private Multimedia convertToEntity(MultimediaDto multimediaDto) {
		var multimedia = modelMapper.map(multimediaDto, Multimedia.class);
		return multimedia;
	}
}
