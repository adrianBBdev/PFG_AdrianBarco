package com.abb.pfg.backend.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Custom response against an authentication request.
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@AllArgsConstructor
@Data
public class LoginResponse {

	private String token;
	private String role;
}
