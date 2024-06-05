package com.abb.pfg.backend.service;

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
 * Service associated with the messages
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
	 * @param id - chat id
	 * @param pageable - messages pageable
	 * @return Page - list of messages
	 */
	public Page<Message> listAllMessagesByChat(Long chatId, Pageable pageable) {
		var multimediaPage = messageRepository.findMessagesByChatCode(chatId, pageable);
		return multimediaPage;
	}

	/**
	 * Gets a specific Multimedia from its id
	 *
	 * @param id - multimedia id
	 * @return MultimediaDto - the requested multimedia
	 */
	public MessageDto getMessageByCode(Long id) {
		var optionalMessage = messageRepository.findById(id);
		var message = optionalMessage.isPresent() ? optionalMessage.get() : null;
		return convertToDto(message);
	}

	/**
	 * Creates a new message file
	 *
	 * @param messageDto - the new message file
	 */
	public void createMessage(MessageDto messageDto) {
		var order = messageRepository.countMessagesByChatId(messageDto.getChat().getId());
		messageDto.setOrderNumber(order);
		messageRepository.save(convertToEntity(messageDto));
	}

	/**
	 * Updates an existing messages
	 *
	 * @param messageDto - message to update
	 */
	public void updateMessage(MessageDto messageDto) {
		if(messageRepository.existsById(messageDto.getId())) {
			messageRepository.save(convertToEntity(messageDto));
			return;
		} 
		log.debug("The multimedia file already exists");
	}

	/**
	 * Deletes a list of messages
	 *
	 * @param messages - list of messages
	 */
	public void deleteMessage(Long id) {
		messageRepository.deleteById(id);
	}

	/**
	 * Converts an entity into a data transfer object
	 *
	 * @param message - entity to convert
	 * @return messageDto - data transfer object converted
	 */
	private MessageDto convertToDto(Message message) {
		try {
			return modelMapper.map(message, MessageDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Converts a data transfer object into an entity
	 *
	 * @param messageDto - data transfer object to convert
	 * @return message - entity converted
	 */
	private Message convertToEntity(MessageDto messageDto) {
		try {
			return modelMapper.map(messageDto, Message.class);
		} catch(Exception e) {
			return null;
		}
	}
}
