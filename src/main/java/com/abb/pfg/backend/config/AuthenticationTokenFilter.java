package com.abb.pfg.backend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abb.pfg.backend.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Security filter to validate the path and authentication token.
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		//Validate the path. If /login or /signUp, then it is no authentication
		if(request.getRequestURI().contains("/auth/")) {
			filterChain.doFilter(request, response);
			return;
		}
		//Gets the token
		var token = getTokenFromRequestHeader(request);
		
		//if validated token, then set authentication
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
        }
		
		var jwt = jwtUtils.validateAndParseToken(token).getPayload();
		
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());
		} catch(UsernameNotFoundException ex) {
			log.error("ERROR: No se ha encontrado el username - {}", userDetails, ex.getMessage());
			filterChain.doFilter(request, response);
			return;
		}
		var auth = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), 
				userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}
	
	/**
	 * Parses the received http request and extract the bearer token from it.
	 * 
	 * @param request - Http request to analyze.
	 * @return String - Token extracted from request.
	 */
	private String getTokenFromRequestHeader(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && !authHeader.isEmpty()) {
			return authHeader.replace("Bearer ", "");
        }
        return null;
	}
}
