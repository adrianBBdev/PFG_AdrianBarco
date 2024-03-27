package com.abb.pfg.backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.MessageDto;
import com.abb.pfg.backend.entities.Message;
import com.abb.pfg.backend.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller associated with the message objects
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@EnableMethodSecurity
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Messages.PATH)
@Tag(name="MessageController", description="Controller to manage the messages of the web app")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets all messages or filter by parameters")
	@ApiResponses(value =
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Message> getAllMessagesByChat(@RequestParam(required=false) Long id,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="0")Integer size) {
		log.trace("Call controller method getAllMessagesByUser()");
		var pageable = PageRequest.of(page, size);
		var messagePage = messageService.listAllMessagesByChat(id, pageable);
		return messagePage;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="GET", description="Gets an existing message")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@GetMapping(value="/{id}")
	public MessageDto getMessage(@PathVariable Long id) {
		log.trace("Call controller method getMessage() with params: {}", id);
		return messageService.getMessage(id);
	}

	@PreAuthorize("hasAnyAuthority('STUDENT','COMPANY')")
	@Operation(method="POST", description="Creates a new message")
	@ApiResponses(value={@ApiResponse(responseCode="201", description="Created")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createMessage(MessageDto messageDto) {
		log.trace("Call controller method createMessage() with params: {}", messageDto.getId());
		messageService.createMessage(messageDto);
	}

	@PreAuthorize("hasAnyAuthority('STUDENT','COMPANY')")
	@Operation(method="PUT", description="Updates an existing message")
	@ApiResponses(value={@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateMessage(MessageDto messageDto) {
		log.trace("Call controller method updateMessage() with params: {}", messageDto.getId());
		messageService.updateMessage(messageDto);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','COMPANY')")
	@Operation(method="DELETE", description="Deletes a list of messages")
	@ApiResponses(value={@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMessages(List<Message> messages) {
		log.trace("Call controller method deleteMessages() with params: {}", messages.size());
		messageService.deleteMessages(messages);
	}
}
