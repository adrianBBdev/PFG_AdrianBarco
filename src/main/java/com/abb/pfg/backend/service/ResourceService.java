package com.abb.pfg.backend.service;

import java.util.List;

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
	public Page<Resource> listAllResourcesByJobOffer(Long id, Pageable pageable) {
		log.trace("Call service method listAllResourceByJobOffer()");
		var resourcePage = resourceRepository.findByJobOffer_Id(id, pageable);
		log.debug("List of resources found: {}", resourcePage.getNumberOfElements());
		return resourcePage;
	}
	
	/**
	 * Gets a specific Resource from its id
	 * 
	 * @param id - resource id
	 * @return ResourceDto - the requested Resource
	 */
	public ResourceDto getResource(Long id) {
		log.trace("Call service method getResource() with params: {}", id);
		var optionalResource = resourceRepository.findById(id);
		var resource = optionalResource.isPresent() ? optionalResource.get() : null;
		log.debug("Resource found: {}", resource.getId());
		return convertToDto(resource);
	}
	
	/**
	 * Creates a new resource file
	 * 
	 * @param resourceDto - the new resource file
	 */
	public void createResource(ResourceDto resourceDto) {
		log.trace("Call service method createResource() with params: {}", resourceDto.getId());
		if(!resourceRepository.existsById(resourceDto.getId())) {
			log.debug("New resource file: {}", resourceDto.getId());
			resourceRepository.save(convertToEntity(resourceDto));
		} else {
			log.debug("The resource file already exists");
		}
	}
	
	/**
	 * Updates an existing resource file
	 * 
	 * @param resourceDto - resource to update
	 */
	public void updateMultimedia(ResourceDto resourceDto) {
		log.trace("Call service method updateResource() with params: {}", resourceDto.getId());
		if(resourceRepository.existsById(resourceDto.getId())) {
			log.debug("Resource file updated: {}", resourceDto.getId());
			resourceRepository.save(convertToEntity(resourceDto));
		} else {
			log.debug("The resource file does not exist");
		}
	}
	
	/**
	 * Deletes a list of resource files
	 * 
	 * @param resources - list of resource files
	 */
	public void deleteMultimedias(List<Resource> resources) {
		log.trace("Call service method deleteResources() with params: {}", resources.size());
		resourceRepository.deleteAllInBatch(resources);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param resource - entity to convert
	 * @return ResourceDto - data transfer object converted
	 */
	private ResourceDto convertToDto(Resource resource) {
		var resourceDto = modelMapper.map(resource, ResourceDto.class);
		return resourceDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param resourceDto - data transfer object to convert
	 * @return Resource - entity converted
	 */
	private Resource convertToEntity(ResourceDto resourceDto) {
		var resource = modelMapper.map(resourceDto, Resource.class);
		return resource;
	}
}
