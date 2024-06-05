/**
 * 
 */
package com.abb.pfg.backend.config;

import com.abb.pfg.backend.commons.RequestStatus;

import lombok.Data;

/**
 * Data transfer Object used to create new requests
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
public class RequestCreationDto {
	private String content;
	private RequestStatus requestStatus;
	private String student;
	private Long jobOffer;
}
