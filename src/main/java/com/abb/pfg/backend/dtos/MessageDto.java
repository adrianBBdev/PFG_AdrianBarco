package com.abb.pfg.backend.dtos;

import java.time.Instant;
import java.util.Date;

import com.abb.pfg.backend.commons.SenderType;
import com.abb.pfg.backend.entities.Chat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for message entity
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
public class MessageDto {
	private Long id;
	private String content;
	private int orderNumber;
	private SenderType senderType;
	private Date timeStamp = Date.from(Instant.now());;
	private Chat chat;
}
