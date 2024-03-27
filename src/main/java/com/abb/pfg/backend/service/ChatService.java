package com.abb.pfg.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.ChatDto;
import com.abb.pfg.backend.entities.Chat;
import com.abb.pfg.backend.repositories.ChatRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service associated with the chats
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */

@Slf4j
@Service
public class ChatService{

	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private ChatRepository chatRepository;

	/**
	 * Gets all chats or filter by user
	 *
	 * @param companyId - company id
	 * @param studentId - student id
	 * @param pageable - chat pageable
	 * @return Page - list of chats
	 */
	public Page<Chat> listAllChatsByCompanyAndStudent(Long companyId, Long studentId,Pageable pageable){
		log.trace("Call service method listAllChatsByCompanyAndStudent()");
		var chatPage = chatRepository.findByCompany_IdAndStudent_Id(companyId, studentId, pageable);
		log.debug("List of chats found {}", chatPage.getNumberOfElements());
		return chatPage;
	}

	/**
	 * Gets the chat with the requested id
	 *
	 * @param id - chat id
	 * @return ChatDto - the requested chat
	 */
	public ChatDto getChat(Long id){
		log.trace("Call service method getChat() with params: {}", id);
		var optionalChat = chatRepository.findById(id);
		var chat = optionalChat.isPresent() ? optionalChat.get() : null;
		log.debug("Chat found: {}", id);
		return convertToDto(chat);
	}

	/**
	 * Creates a new chat
	 *
	 * @param chatDto - the new chat
	 */
	public void createChat(ChatDto chatDto) {
		log.trace("Call service method createChat() with params: {}", chatDto.getId());
		if(!chatRepository.existsById(chatDto.getId())) {
			log.debug("New chat: {}", chatDto.getId());
			chatRepository.save(convertToEntity(chatDto));
		} else {
			log.debug("The chat already exists");
		}
	}

	/**
	 * Updates an existing chat
	 *
	 * @param chatDto - the chat that will be updated
	 */
	public void updateChat(ChatDto chatDto) {
		log.trace("Call service method updateChat() with params: {}", chatDto.getId());
		if(chatRepository.existsById(chatDto.getId())) {
			log.debug("Chat updated: {}", chatDto.getId());
			chatRepository.save(convertToEntity(chatDto));
		} else {
			log.debug("The chat does not exist");
		}
	}

	/**
	 * Deletes all provided chats
	 *
	 * @param chats - list of chats to delete
	 */
	public void deleteChats(List<Chat> chats) {
		log.trace("Call service method deleteChats() with {} chats", chats.size());
		chatRepository.deleteAllInBatch(chats);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param chat - entity to convert
	 * @return ChatDto - data transfer object converted
	 */
	private ChatDto convertToDto(Chat chat) {
		var chatDto = modelMapper.map(chat, ChatDto.class);
		return chatDto;
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param chatDto - data transfer object to convert
	 * @return Chat - entity converted
	 */
	private Chat convertToEntity(ChatDto chatDto) {
		var chat = modelMapper.map(chatDto, Chat.class);
		return chat;
	}
}
