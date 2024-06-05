package com.abb.pfg.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abb.pfg.backend.entities.Area;

/**
 * Area JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface AreaRepository extends JpaRepository<Area, Long>{
	
	/**
	 * Finds the area from its name
	 * 
	 * @param name - area's name
	 * @return Area if it exists, null if not
	 */
	public Optional<Area> findByName(String name);
	
	/**
	 * Finds if an area exists or not
	 * 
	 * @param name - area's name
	 * @return true if it exists, false if not
	 */
	public boolean existsByName(String name);
	
	/**
	 * Deletes the area from its name
	 * 
	 * @param name - area's name
	 */
	public void deleteByName(String name);
}
