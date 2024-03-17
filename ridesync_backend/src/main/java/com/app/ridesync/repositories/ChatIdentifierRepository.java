package com.app.ridesync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.ridesync.entities.ChatIdentifier;

public interface ChatIdentifierRepository extends JpaRepository<ChatIdentifier,Integer>{

	@Query("SELECT chatIdentifier FROM ChatIdentifier "
			+ "WHERE (senderId = :senderId AND recipientId = :recipientId) "
			+ "OR (senderId = :recipientId AND recipientId = :senderId)")
	String findBySenderAndRecipientId(@Param("senderId")Integer senderId, @Param("recipientId")Integer recipientId);
}
