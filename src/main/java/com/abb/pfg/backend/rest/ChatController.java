package com.abb.pfg.backend.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.ChatDto;
import com.abb.pfg.backend.entities.Chat;
import com.abb.pfg.backend.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which represents the chats' controller
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@RestController
@RequestMapping(value=Constants.Controllers.Chats.PATH)
@Tag(name="ChatController", description="Controller to manage the chats of the web app")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE')")
	@Operation(method="GET", description="Gets all chats or filter by parameters")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping
	public Page<Chat> getAllChatsByCompanyAndStudent(@RequestParam(required=false) Long companyId, 
			@RequestParam(required=false) Long studentId,
			@RequestParam(defaultValue="0") Integer page, 
			@RequestParam(defaultValue="3") Integer size){
		log.trace("Call controller method getAllChats");
		var pageable = PageRequest.of(page, size);
		var chatPage = chatService.listAllChatsByCompanyAndStudent(companyId, studentId, pageable);
		return chatPage;
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE')")
	@Operation(method="GET", description="Gets a specific chat from its id")
	@ApiResponses(value = 
		{@ApiResponse(responseCode="200", description="Success", content=@Content(mediaType=MediaType.APPLICATION_JSON_VALUE))})
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ChatDto getChat(@PathVariable("id") Long id) {
		log.trace("Call controller method getChat() with params: {}", id);
		return chatService.getChat(id);
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA')")
	@Operation(method="POST", description="Creates a new chat")
	@ApiResponses(value = {@ApiResponse(responseCode="202", description="Created")})
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public void createChat(@RequestBody ChatDto chatDto) {
		log.trace("Call controller method createChat() with params: {}", chatDto.getId());
		chatService.createChat(chatDto);
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE')")
	@Operation(method="PUT", description="Updates an existing chat")
	@ApiResponses(value = {@ApiResponse(responseCode="200", description="Success")})
	@PutMapping
	public void updateChat(@RequestBody ChatDto chatDto) {
		log.trace("Call controller method updateChat() with params: {}", chatDto.getId());
		chatService.updateChat(chatDto);
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPRESA','ROLE_ESTUDIANTE')")
	@Operation(method="DELETE", description="Deletes a list of chats")
	@ApiResponses(value = {@ApiResponse(responseCode="204", description="No content")})
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteChat(@RequestBody List<Chat> chats) {
		log.trace("Call controller method deleteChat() with params", chats.size());
		chatService.deleteChats(chats);
	}
}
