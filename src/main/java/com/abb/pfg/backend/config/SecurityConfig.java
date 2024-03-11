package com.abb.pfg.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abb.pfg.backend.service.CustomUserDetailsService;

/**
 * Custom Spring security configuration.
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 * 
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	AuthenticationTokenFilter authenticationTokenFilter;
	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		 httpSecurity.httpBasic(Customizer.withDefaults());
		 httpSecurity.csrf().disable();
		 httpSecurity.headers().frameOptions().disable();
		 httpSecurity
         	.authorizeHttpRequests()
         	.requestMatchers("/auth/*").permitAll()
            .anyRequest().authenticated();
		 httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		 return httpSecurity.build();
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManager(CustomUserDetailsService customUserDetailsService,
			 PasswordEncoder passwordEncoder) {
		 DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		 authenticationProvider.setUserDetailsService(customUserDetailsService);
		 authenticationProvider.setPasswordEncoder(passwordEncoder);
		 
		 return new ProviderManager(authenticationProvider);
	 }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
}
