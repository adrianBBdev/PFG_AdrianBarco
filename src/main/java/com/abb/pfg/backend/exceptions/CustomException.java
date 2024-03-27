package com.abb.pfg.backend.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class which represents the custom exception of the application
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = -4260991469865498946L;
	private final String errorCode;
	private final String message;
	private final HttpStatus httpStatus;
}
