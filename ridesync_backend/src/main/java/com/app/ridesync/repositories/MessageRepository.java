package com.app.ridesync.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Message;
import com.app.ridesync.projections.MessageProjection;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{
	
	
	@Query("SELECT "
			+ "NEW com.app.ridesync.projections.MessageProjection(" 
			+ "sender.fullName as senderName, recipient.fullName as recipientName, message.message, message.sentTime) "
			+ "FROM Message message "
			+ "JOIN User sender ON message.senderId = sender.Id "
			+ "JOIN User recipient ON message.recipientId = recipient.Id "
			+ "WHERE (senderId = :senderId AND recipientId = :recipientId) "
			+ "OR (senderId = :recipientId AND recipientId = :senderId) "
			+ "ORDER BY sentTime" )
	List<MessageProjection> findBySenderAndRecipientId(@Param("senderId")Integer senderId, @Param("recipientId")Integer recipientId);
	
	@Query("SELECT "
			+ "NEW com.app.ridesync.projections.MessageProjection(" 
			+ "sender.fullName as senderName, recipient.fullName as recipientName, message.message, message.sentTime) "
			+ "FROM Message message "
			+ "JOIN User sender ON message.senderId = sender.Id "
			+ "JOIN User recipient ON message.recipientId = recipient.Id "
			+ "WHERE recipientId = :recipientId "
			+ "ORDER BY sentTime DESC")
	List<MessageProjection> findByRecipientId(@Param("recipientId")Integer recipientId);
	
    
}
