package com.abb.pfg.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abb.pfg.backend.entities.Administrator;
import com.abb.pfg.backend.entities.Role;
import com.abb.pfg.backend.entities.User;
import com.abb.pfg.backend.repositories.AdminRepository;
import com.abb.pfg.backend.repositories.RoleRepository;
import com.abb.pfg.backend.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

/**
 * Verifies if the database contais default data for the correct performance of the web app
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Configuration
public class DataInitializer {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final AdminRepository adminRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, AdminRepository adminRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.adminRepository = adminRepository;
	}
	
	@PostConstruct
	public void initData() {
		if(roleRepository.count() == 0) {
			roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("STUDENT"));
			roleRepository.save(new Role("COMPANY"));
			roleRepository.save(new Role("GUEST"));
		}
		if(!userRepository.existsByUsername("admin@jobs4students.com")) {
			var adminRole = roleRepository.findByName("ADMIN");
			var adminUser = new User("admin@jobs4students.com", passwordEncoder.encode("password1234"), adminRole);
			userRepository.save(adminUser);
			var admin = userRepository.findByUsername("admin@jobs4students.com");
			adminRepository.save(new Administrator(admin));
		}
	}
}
