package com.abb.pfg.backend.service;

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
	 * @param name - company's or student's name
	 * @param pageable - chat pageable
	 * @return Page - list of chats
	 */
	public Page<Chat> listAllChatsByCompanyAndStudent(String companyId, String studentId, 
			String name, String studentName, String companyName,Pageable pageable){
		var chatPage = chatRepository.findByCompany_IdAndStudent_IdAndCompanyNameAndStudentName(companyId, 
				studentId, name, studentName, companyName, pageable);
		return chatPage;
	}

	/**
	 * Gets the chat with the requested id
	 *
	 * @param id - chat's id
	 * @return ChatDto - the requested chat
	 */
	public ChatDto getChatById(Long id){
		var optionalChat = chatRepository.findById(id);
		var chat = optionalChat.isPresent() ? optionalChat.get() : null;
		return convertToDto(chat);
	}

	/**
	 * Creates a new chat
	 *
	 * @param chatDto - the new chat
	 */
	public void createChat(ChatDto chatDto) {
		chatRepository.save(convertToEntity(chatDto));
	}

	/**
	 * Updates an existing chat
	 *
	 * @param chatDto - the chat that will be updated
	 */
	public void updateChat(ChatDto chatDto) {
		if(chatRepository.existsById(chatDto.getId())) {
			chatRepository.save(convertToEntity(chatDto));
			return;
		}
		log.debug("The chat does not exist");
	}

	/**
	 * Deletes the provided chat
	 *
	 * @param chat - chat to delete
	 */
	public void deleteChat(Long id) {
		chatRepository.deleteById(id);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param chat - entity to convert
	 * @return ChatDto - data transfer object converted
	 */
	private ChatDto convertToDto(Chat chat) {
		try {
			return modelMapper.map(chat, ChatDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param chatDto - data transfer object to convert
	 * @return Chat - entity converted
	 */
	private Chat convertToEntity(ChatDto chatDto) {
		try {
			return modelMapper.map(chatDto, Chat.class);
		} catch(Exception e) {
			return null;
		}
	}
}
