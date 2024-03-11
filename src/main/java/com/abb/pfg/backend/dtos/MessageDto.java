package com.abb.pfg.backend.dtos;

import java.util.Date;

import com.abb.pfg.backend.commons.SenderType;
import com.abb.pfg.backend.entities.Chat;

import lombok.Data;

/**
 * Data transfer object for message entity
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
public class MessageDto {
	
	private Long id;
	private String content;
	private int order;
	private SenderType senderType;
	private Date timeStamp;
	private Chat chat;
}
