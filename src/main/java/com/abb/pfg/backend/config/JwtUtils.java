package com.abb.pfg.backend.config;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;


/**
 * Service that manipulates tokens for authentication
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Component
public class JwtUtils {

	@Value(value="${authentication.secret-key}")
	private String SECRET_KEY;
	
	/**
	 * Creates a new token for an app user.
	 * 
	 * @param authentication - contains the user's authentication information.
	 * @return String - Token associated with the authenticated user.
	 */
	public String generateToken(Authentication authentication) {
		var bytesOfMessage = Base64.getEncoder().encode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
		var key = new SecretKeySpec(bytesOfMessage, "HmacSHA256");
		log.info("Token generated...");
		return Jwts.builder()
				.issuedAt(Date.from(Instant.now()))
				.subject(authentication.getName())
				.expiration(Date.from(Instant.now().plus(1L, ChronoUnit.HOURS)))
				.signWith(key)
				.compact();
	}
	
	/**
	 * Validates and analyzes the token provided as a paramter, in order to authorize the user associated.
	 * 
	 * @param token - Token to validate.
	 * @return Jws<Claims> - User data extracted when validating the token.
	 */
	public Jws<Claims> validateAndParseToken(String token) {
		try {
			var secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
			log.info("Key: {}", String.valueOf(secretKey.getEncoded()));
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
		} catch (JwtException ex) {
            // Invalid signature/claims
        	log.error("ERROR: Failed to parse token", ex);
        	return null;
		}
	}
}
