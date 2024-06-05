package com.abb.pfg.backend.service;

import java.io.File;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.ResourceDto;
import com.abb.pfg.backend.entities.Resource;
import com.abb.pfg.backend.repositories.ResourceRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the resource objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class ResourceService {

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private ResourceRepository resourceRepository;

	/**
	 * Finds all resources content or filter by owner
	 *
	 * @param id - user id
	 * @param pageable - resources pageable
	 * @return Page - list of resources
	 */
	public Page<Resource> listAllResourcesByJobOfferIdAndName(Long jobOfferId, String name, Pageable pageable) {
		var resourcePage = resourceRepository.findByJobOfferIdAndName(jobOfferId, name, pageable);
		return resourcePage;
	}

	/**
	 * Gets a specific Resource from its id
	 *
	 * @param id - resource id
	 * @return ResourceDto - the requested Resource
	 */
	public ResourceDto getResource(Long id) {
		var optionalResource = resourceRepository.findById(id);
		var resource = optionalResource.isPresent() ? optionalResource.get() : null;
		return convertToDto(resource);
	}

	/**
	 * Creates a new resource file
	 *
	 * @param resourceDto - the new resource file
	 */
	public void createResource(ResourceDto resourceDto) {
		resourceRepository.save(convertToEntity(resourceDto));
	}

	/**
	 * Updates an existing resource file
	 *
	 * @param resourceDto - resource to update
	 */
	public void updateResource(ResourceDto resourceDto) {
		if(resourceRepository.existsById(resourceDto.getId())) {
			resourceRepository.save(convertToEntity(resourceDto));
			return;
		}
		log.debug("The resource file does not exist");
	}

	/**
	 * Deletes a list of resource files
	 *
	 * @param resources - list of resource files
	 */
	public void deleteResource(Long id) {
		var resourceName = this.getResource(id).getName();
		resourceRepository.deleteById(id);
		deleteFileFromSystem(resourceName);
	}
	
	/**
	 * Deletes a resource from system
	 * 
	 * @param fileName - file name to delete
	 * @return boolean - true if it has been deleted, false if not
	 */
	private boolean deleteFileFromSystem(String fileName) {
		var storedPath = "C:\\Users\\Usuario\\Documents\\DocumentosAdrian\\Proyecto_Fin_Grado\\PFG_AdrianBarco\\app_data\\resources";
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
	 * @param resource - entity to convert
	 * @return ResourceDto - data transfer object converted
	 */
	private ResourceDto convertToDto(Resource resource) {
		try {
			return modelMapper.map(resource, ResourceDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param resourceDto - data transfer object to convert
	 * @return Resource - entity converted
	 */
	private Resource convertToEntity(ResourceDto resourceDto) {
		try {
			return modelMapper.map(resourceDto, Resource.class);
		} catch(Exception e) {
			return null;
		}
	}
}
