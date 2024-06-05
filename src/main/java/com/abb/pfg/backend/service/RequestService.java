package com.abb.pfg.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.commons.RequestStatus;
import com.abb.pfg.backend.config.RequestCreationDto;
import com.abb.pfg.backend.dtos.RequestDto;
import com.abb.pfg.backend.entities.Request;
import com.abb.pfg.backend.repositories.RequestRepository;

import jakarta.transaction.Transactional;
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
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private JobOfferService jobOfferService;

	/**
	 * Gets all requests
	 *
	 * @param jobOfferId - job offer id
	 * @param userId - user's username
	 * @param requestStatus - status of the request
	 * @param pageable - requests pageable
	 * @return Page - list of requests
	 */
	public Page<Request> listAllRequestsByJobOfferAndStudentAndRequestStatus(Long jobOfferId, String userId,
			String name, RequestStatus requestStatus, Pageable pageable) {
		var requestPage = requestRepository.findByJobOffer_IdAndStudent_IdAndRequestStatus(jobOfferId,
				userId, name, requestStatus, pageable);
		return requestPage;
	}

	/**
	 * Gets the request with the requested id
	 *
	 * @param id - request id
	 * @return RequestDto - the requested request
	 */
	public RequestDto getRequest(Long id) {
		var optionalRequest = requestRepository.findById(id);
		var request = optionalRequest.isPresent() ? optionalRequest.get() : null;
		return convertToDto(request);
	}

	/**
	 * Creates a new request
	 *
	 * @param requestDto - the new request
	 */
	@Transactional
	public void createRequest(RequestCreationDto requestCreationDto) {
		var studentDto = studentService.getStudentByUsernameAndDni(requestCreationDto.getStudent(), null);
		var jobOfferDto = jobOfferService.getJobOfferById(requestCreationDto.getJobOffer());
		var requestDto = new RequestDto();
		requestDto.setContent(requestCreationDto.getContent());
		requestDto.setRequestStatus(requestCreationDto.getRequestStatus());
		requestDto.setStudent(studentService.convertToEntity(studentDto));
		requestDto.setJobOffer(jobOfferService.convertToEntity(jobOfferDto));
		requestRepository.save(convertToEntity(requestDto));
	}

	/**
	 * Updates an existing request
	 *
	 * @param requestDto - request to update
	 */
	public void updateRequest(RequestDto requestDto) {
		if(requestRepository.existsById(requestDto.getId())) {
			requestRepository.save(convertToEntity(requestDto));
		}
		log.debug("The request does not exist");
	}
	
	/**
	 * Deletes the request which request code has been provided as parameter
	 * 
	 * @param requestCode - request code to delete
	 */
	public void deleteRequest(Long id) {
		requestRepository.deleteById(id);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param request - entity to convert
	 * @return RequestDto - data transfer object converted
	 */
	private RequestDto convertToDto(Request request) {
		try {
			return modelMapper.map(request, RequestDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param requestDto - data transfer object to convert
	 * @return Request - entity converted
	 */
	private Request convertToEntity(RequestDto requestDto) {
		try {
			return modelMapper.map(requestDto, Request.class);
		} catch(Exception e) {
			return null;
		}
	}
}
