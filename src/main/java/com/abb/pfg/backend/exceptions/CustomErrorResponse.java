package com.abb.pfg.backend.exceptions;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Class which represents the custom exception format
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
public class CustomErrorResponse {
	
	private final String guid;
	private final String message;
	private final Integer statusCode;
	private final String statusName;
	private final String path;
	private final String method;
	private final LocalDateTime timestamp;
	
}
