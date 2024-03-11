package com.abb.pfg.backend.service;

import java.util.List;

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
		log.trace("Call service method listAllAreas()");
		var areas = areaRepository.findAll(pageable);
		log.debug("List of areas found: {}", areas.getNumberOfElements());
		return areas;
	}
	
	/**
	 * Gets the area with the requested id
	 * 
	 * @param id - area id
	 * @return AreaDtp - the requested area
	 */
	public AreaDto getArea(Long id) {
		log.trace("Call service method getArea() with params: {}", id);
		var optionalArea = areaRepository.findById(id);
		var area = optionalArea.isPresent() ? optionalArea.get() : null;
		log.debug("Area found: {}", area.getId());
		return convertToDto(area);
	}
	
	/**
	 * Creates a new area
	 * 
	 * @param areaDto - the new area
	 */
	public void createArea(AreaDto areaDto) {
		log.trace("Call service method createArea() with params: {}", areaDto.getId());
		if(!areaRepository.existsById(areaDto.getId())) {
			log.debug("New area: {}", areaDto.getId());
			areaRepository.save(convertToEntity(areaDto));
		} else {
			log.debug("The area already exists");
		}
	}
	
	/**
	 * Updates an existing area
	 * 
	 * @param areaDto - the area that will be updated
	 */
	public void updateArea(AreaDto areaDto) {
		log.trace("Call service method updateArea() with params: {}", areaDto.getId());
		if(areaRepository.existsById(areaDto.getId())) {
			log.debug("Area updated: {}", areaDto.getId());
			areaRepository.save(convertToEntity(areaDto));
		} else {
			log.debug("The area does not exist");
		}
	}
	
	/**
	 * Deletes all provided areas
	 * 
	 * @param area - list of areas to delete
	 */
	public void deleteAreas(List<Area> areas) {
		log.trace("Call service method deleteAreas() with params: {}", areas.size());
		areaRepository.deleteAllInBatch(areas);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param area - entity to convert
	 * @return AreaDto - data transfer object converted
	 */
	private AreaDto convertToDto(Area area) {
		var areaDto = modelMapper.map(area, AreaDto.class);
		return areaDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param areaDto - data transfer object to convert
	 * @return Area - entity converted
	 */
	private Area convertToEntity(AreaDto areaDto) {
		var area = modelMapper.map(areaDto, Area.class);
		return area;
	}
}
