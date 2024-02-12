/**
 * 
 */
package com.abb.pfg.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abb.pfg.backend.entities.Message;
import com.abb.pfg.backend.entities.Multimedia;

/**
 * Class which represents the Message's JPA repository
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public interface MessageRepository extends JpaRepository<Message,Long>{
	
	/**
	 * Finds all messages from a specified chat
	 * 
	 * @param chat - chat's id
	 * @param pageable - message's pageable
	 * @return Page - list of messages
	 */
	@Query("SELECT ms FROM Message ms"
			+ " WHERE (:chatId IS NULL or ms.chat.id = :chatId)")
	public Page<Message> findByChat_Id(@Param("chatId") Long chatId, Pageable pageable);

}
