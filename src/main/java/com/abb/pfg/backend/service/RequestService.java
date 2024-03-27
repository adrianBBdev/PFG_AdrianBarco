package com.abb.pfg.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.commons.RequestState;
import com.abb.pfg.backend.dtos.RequestDto;
import com.abb.pfg.backend.entities.Request;
import com.abb.pfg.backend.repositories.RequestRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the requests
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Slf4j
@Service
public class RequestService {

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private RequestRepository requestRepository;

	/**
	 * Gets all requests
	 *
	 * @param jobOfferId - job offer id
	 * @param studentId - student id
	 * @param requestState - state of the request
	 * @param pageable - requests pageable
	 * @return Page - list of requests
	 */
	public Page<Request> listAllRequestsByJobOfferAndStudentAndRequestState(Long jobOfferId, Long studentId,
			RequestState requestState, Pageable pageable) {
		log.trace("Call service method listAllRequests()");
		var requestPage = requestRepository.findByJobOffer_IdAndStudent_IdAndRequestState(jobOfferId,
				studentId, requestState, pageable);
		log.debug("List of chats found {}", requestPage.getNumberOfElements());
		return requestPage;
	}

	/**
	 * Gets the request with the requested id
	 *
	 * @param id - request id
	 * @return RequestDto - the requested request
	 */
	public RequestDto getRequest(Long id) {
		log.trace("Call service method getRequest() with params: {}", id);
		var optionalRequest = requestRepository.findById(id);
		var request = optionalRequest.isPresent() ? optionalRequest.get() : null;
		log.debug("Request found: {}", id);
		return convertToDto(request);
	}

	/**
	 * Creates a new request
	 *
	 * @param requestDto - the new request
	 */
	public void createRequest(RequestDto requestDto) {
		log.trace("Call service method createRequest() with params: {}", requestDto.getId());
		if(!requestRepository.existsById(requestDto.getId())) {
			log.debug("New request: {}", requestDto.getId());
			requestRepository.save(convertToEntity(requestDto));
		} else {
			log.debug("The request already exists");
		}
	}

	/**
	 * Updates an existing request
	 *
	 * @param requestDto - request to update
	 */
	public void updateRequest(RequestDto requestDto) {
		log.trace("Call service method updateRequest() with params: {}", requestDto.getId());
		if(requestRepository.existsById(requestDto.getId())) {
			log.debug("New request: {}", requestDto.getId());
			requestRepository.save(convertToEntity(requestDto));
		} else {
			log.debug("The request does not exist");
		}
	}

	/**
	 * Deletes all provided requests
	 *
	 * @param requests - list of requests to delete
	 */
	public void deleteRequests(List<Request> requests) {
		log.trace("Call service method deleteRequests() with params: {}", requests.size());
		requestRepository.deleteAllInBatch(requests);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param request - entity to convert
	 * @return RequestDto - data transfer object converted
	 */
	private RequestDto convertToDto(Request request) {
		var requestDto = modelMapper.map(request, RequestDto.class);
		return requestDto;
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param requestDto - data transfer object to convert
	 * @return Request - entity converted
	 */
	private Request convertToEntity(RequestDto requestDto) {
		var request = modelMapper.map(requestDto, Request.class);
		return request;
	}
}
