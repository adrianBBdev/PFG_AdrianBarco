package com.abb.pfg.backend.entities;

import java.time.Instant;
import java.util.Date;

import com.abb.pfg.backend.commons.SenderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity associated with the messages sent on the chats.
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@Entity
@Table(name="Message")
@Data
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false)
	private Long id;

	@Column(length=255,nullable=false)
	private String content;

	@Column(length=20,nullable=false)
	private int orderNumber;

	@Enumerated(EnumType.STRING)
	private SenderType senderType;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	@ManyToOne
	private Chat chat;

	/**
	 * Default class constructor
	 *
	 * @param content - message content
	 * @param order - message order within the chat
	 * @param senderType - type of sender who sends the message
	 * @param chat - chat to which the message belongs
	 */
	public Message (String content, int order, SenderType senderType, Chat chat) {
		this.content = content;
		this.orderNumber = order;
		this.senderType = senderType;
		this.timeStamp = Date.from(Instant.now());


	}

}
