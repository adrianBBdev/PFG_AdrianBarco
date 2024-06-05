package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Message;

/**
 * Messages JPA repository
 *
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface MessageRepository extends JpaRepository<Message,Long>{

	/**
	 * Finds all messages from a specified chat
	 *
	 * @param chat - chat id
	 * @param pageable - messages pageable
	 * @return Page - list of messages
	 */
	@Query("SELECT ms FROM Message ms"
			+ " WHERE (:chatId IS NULL or ms.chat.id = :chatId)")
	public Page<Message> findMessagesByChatCode(@Param("chatId") Long chatId, Pageable pageable);
	
	@Query("SELECT COUNT(ms) FROM Message ms"
			+ " WHERE ms.chat.id = :chatId")
	public int countMessagesByChatId(@Param("chatId") Long chatId);
}
