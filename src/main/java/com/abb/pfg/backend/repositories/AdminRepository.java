package com.abb.pfg.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abb.pfg.backend.entities.Administrator;

/**
 * Administrator JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface AdminRepository extends JpaRepository<Administrator, Long> {}