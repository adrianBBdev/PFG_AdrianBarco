/**
 * 
 */
package com.abb.pfg.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abb.pfg.backend.dtos.MessageDto;
import com.abb.pfg.backend.entities.Message;
import com.abb.pfg.backend.repositories.MessageRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Class which represents the message's service
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Slf4j
@Service
public class MessageService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private MessageRepository messageRepository;
	
	/**
	 * Finds all message content or filter by owner
	 * 
	 * @param id - chat's id
	 * @param pageable - message's pageable
	 * @return Page - list of messages
	 */
	public Page<Message> listAllMessagesByChat(Long id, Pageable pageable) {
		log.trace("Call service method listAllMessagesByChat()");
		var multimediaPage = messageRepository.findByChat_Id(id, pageable);
		log.debug("List of Message found: {}", multimediaPage.getNumberOfElements());
		return multimediaPage;
	}
	
	/**
	 * Gets a specific Multimedia from its id
	 * 
	 * @param id - multimedia's id
	 * @return MultimediaDto - the requested multimedia
	 */
	public MessageDto getMessage(Long id) {
		log.trace("Call service method getMessage() with params: {}", id);
		var optionalMessage = messageRepository.findById(id);
		var message = optionalMessage.isPresent() ? optionalMessage.get() : null;
		log.debug("Message found: {}", message.getId());
		return convertToDto(message);
	}
	
	/**
	 * Creates a new message file
	 * 
	 * @param messageDto - the new message file
	 */
	public void createMessage(MessageDto messageDto) {
		log.trace("Call service method createMessage() with params: {}", messageDto.getId());
		if(!messageRepository.existsById(messageDto.getId())) {
			log.debug("New message file: {}", messageDto.getId());
			messageRepository.save(convertToEntity(messageDto));
		} else {
			log.debug("The message file already exists");
		}
	}
	
	/**
	 * Updates an existing messages
	 *  
	 * @param messageDto - message to update
	 */
	public void updateMessage(MessageDto messageDto) {
		log.trace("Call service method updateMessage() with params: {}", messageDto.getId());
		if(messageRepository.existsById(messageDto.getId())) {
			log.debug("Updated message file: {}", messageDto.getId());
			messageRepository.save(convertToEntity(messageDto));
		} else {
			log.debug("The multimedia file already exists");
		}
	}
	
	/**
	 * Deletes a list of messages
	 * 
	 * @param messages - list of messages
	 */
	public void deleteMessages(List<Message> messages) {
		log.trace("Call service method deleteMessages() with params: {}", messages.size());
		messageRepository.deleteAllInBatch(messages);
	}
	
	/**
	 * Converts an entity into a data transfer object
	 * 
	 * @param message - entity to convert
	 * @return messageDto - data transfer object converted
	 */
	private MessageDto convertToDto(Message message) {
		var messageDto = modelMapper.map(message, MessageDto.class);
		return messageDto;
	}
	
	/**
	 * Converts a data transfer object into an entity
	 * 
	 * @param messageDto - data transfer object to convert
	 * @return message - entity converted
	 */
	private Message convertToEntity(MessageDto messageDto) {
		var message = modelMapper.map(messageDto, Message.class);
		return message;
	}

}
