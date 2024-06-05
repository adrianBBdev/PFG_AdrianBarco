package com.abb.pfg.backend.service;


import java.io.File;

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
	public Page<Multimedia> listAllMultimediaByUser(String name, String username, Pageable pageable) {
		var resourcePage = multimediaRepository.findByUserAndName(name, username, pageable);
		return resourcePage;
	}

	/**
	 * Gets a specific Multimedia from its id
	 *
	 * @param id - multimedia's id
	 * @return MultimediaDto - the requested multimedia
	 */
	public MultimediaDto getMultimedia(Long id) {
		var optionalMedia = multimediaRepository.findById(id);
		var multimedia = optionalMedia.isPresent() ? optionalMedia.get() : null;
		return convertToDto(multimedia);
	}

	/**
	 * Creates a new multimedia file
	 *
	 * @param multimediaDto - the new multimedia file
	 */
	public void createMultimedia(MultimediaDto multimediaDto) {
		multimediaRepository.save(convertToEntity(multimediaDto));
	}

	/**
	 * Updates an existing multimedia file
	 *
	 * @param multimediaDto - multimedia to update
	 */
	public void updateMultimedia(MultimediaDto multimediaDto) {
		if(multimediaRepository.existsById(multimediaDto.getId())) {
			multimediaRepository.save(convertToEntity(multimediaDto));
			return;
		}
		log.debug("The multimedia file does not exist");
	}

	/**
	 * Deletes a list of multimedia files
	 *
	 * @param multimedias - list of multimedia files
	 */
	public void deleteMultimedia(Long id) {
		var mediaName = this.getMultimedia(id).getName();
		multimediaRepository.deleteById(id);
		deleteFileFromSystem(mediaName);
	}
	
	/**
	 * Deletes the media resource from system
	 * 
	 * @param fileName - file name to delte
	 * @return boolean - true if it has been deleted, false if not
	 */
	private boolean deleteFileFromSystem(String fileName) {
		var storedPath = "C:\\Users\\Usuario\\Documents\\DocumentosAdrian\\Proyecto_Fin_Grado\\PFG_AdrianBarco\\app_data\\media";
		try {
			var file = new File(storedPath + "\\" + fileName);
			return file.delete();
		} catch (SecurityException e) {
			System.err.println("Error: no se ha podido eliminar el archivo del sistema");
			return false;
		}
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param multimedia - entity to convert
	 * @return MultimediaDto - data transfer object converted
	 */
	private MultimediaDto convertToDto(Multimedia multimedia) {
		try {
			 return modelMapper.map(multimedia, MultimediaDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param multimediaDto - data transfer object to convert
	 * @return Multimedia - entity converted
	 */
	private Multimedia convertToEntity(MultimediaDto multimediaDto) {
		try {
			return modelMapper.map(multimediaDto, Multimedia.class);
		} catch(Exception e) {
			return null;
		}
	}
}
