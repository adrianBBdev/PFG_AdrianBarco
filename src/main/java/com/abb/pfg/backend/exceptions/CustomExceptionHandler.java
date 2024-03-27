package com.abb.pfg.backend.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which represents
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNotFoundException(final NullPointerException exception, final HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(
	            String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
	            exception);
		var response = new CustomErrorResponse(
                guid,
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUnknownException(final Exception exception, final HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(
	            String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
	            exception);
		var response = new CustomErrorResponse(
                guid,
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
