package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.AreaDto;
import com.abb.pfg.backend.entities.Area;
import com.abb.pfg.backend.repositories.AreaRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the area objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class AreaService {

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private AreaRepository areaRepository;

	/**
	 * Gets all areas
	 *
	 * @param pageable - pageable of areas
	 * @return Page - list with the requested areas
	 */
	public Page<Area> listAllAreas(Pageable pageable){
		var areas = areaRepository.findAll(pageable);
		return areas;
	}

	/**
	 * Gets the area with the requested id
	 *
	 * @param name - area name
	 * @return AreaDto - the requested area
	 */
	public AreaDto getArea(String name) {
		var optionalArea = areaRepository.findByName(name);
		var area = optionalArea.isPresent() ? optionalArea.get() : null;
		return convertToDto(area);
	}

	/**
	 * Creates a new area
	 *
	 * @param areaDto - the new area
	 */
	public void createArea(AreaDto areaDto) {
		if(!areaRepository.existsByName(areaDto.getName())) {
			areaRepository.save(convertToEntity(areaDto));
			return;
		} 
		log.debug("The area already exists");
	}

	/**
	 * Updates an existing area
	 *
	 * @param areaDto - the area that will be updated
	 */
	public void updateArea(AreaDto areaDto) {
		if(areaRepository.existsByName(areaDto.getName())) {
			areaRepository.save(convertToEntity(areaDto));
			return;
		} 
		log.debug("The area does not exist");
	}

	/**
	 * Deletes all provided areas
	 *
	 * @param area - area's name
	 */
	public void deleteArea(String areaName) {
		areaRepository.deleteByName(areaName);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param area - entity to convert
	 * @return AreaDto - data transfer object converted
	 */
	private AreaDto convertToDto(Area area) {
		try {
			return modelMapper.map(area, AreaDto.class);
		} catch (Exception e) {
			return null;
		}
		
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param areaDto - data transfer object to convert
	 * @return Area - entity converted
	 */
	public Area convertToEntity(AreaDto areaDto) {
		try {
			return modelMapper.map(areaDto, Area.class);
		} catch (Exception e) {
			return null;
		}
	}
}
