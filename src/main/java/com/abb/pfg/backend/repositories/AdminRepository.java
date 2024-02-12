package com.abb.pfg.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abb.pfg.backend.entities.Administrator;

/**
 * Class which represents the administrator's JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface AdminRepository extends JpaRepository<Administrator, Long> {}