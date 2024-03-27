package com.app.ridesync.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.entities.Message;
import com.app.ridesync.entities.Notification;
import com.app.ridesync.projections.MessageProjection;
import com.app.ridesync.repositories.ChatIdentifierRepository;
import com.app.ridesync.repositories.MessageRepository;

@Service
public class MessageService {
	private final ChatIdentifierRepository chatIdentifierRepository;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final MessageRepository messageRepository;
	private NotificationService notificationService;

	private static final String DESTINATION = "/queue/messages/";

	@Autowired
	public MessageService(ChatIdentifierRepository chatIdentifierRepository, SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository,  NotificationService notificationService) {
		this.chatIdentifierRepository = chatIdentifierRepository;
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.messageRepository = messageRepository;
		this.notificationService = notificationService;

	}
	
	public void persistAndSendMessageToBroker(String channel, Message message) {		
		persistMessage(message);
		persistNotification(message);
		sendMessage(channel, message);
	}
	
	public String getChatIdentifier(Integer senderId, Integer recipientId) {
		String chatIdentifer = chatIdentifierRepository.findBySenderAndRecipientId(senderId,recipientId);
		
		if(chatIdentifer == null)
			chatIdentifer = createAndPersistChatIdentifier(senderId, recipientId);
		
		return chatIdentifer;
	}
	
	public List<MessageProjection> getChatMessagesByRecipientId(Integer recipientId){
		return messageRepository.findByRecipientId(recipientId);
	}
	
	public List<MessageProjection> getChatMessagesBySenderAndRecipientId(Integer senderId, Integer recipientId){
		return messageRepository.findBySenderAndRecipientId(senderId, recipientId);
	}
		
	private String createAndPersistChatIdentifier(Integer senderId, Integer recipientId) {
		String generatedChatIdentifier = UUID.randomUUID().toString();
		
		ChatIdentifier createdChatIdentifier = new ChatIdentifier();
		createdChatIdentifier.setSenderId(senderId);
		createdChatIdentifier.setRecipientId(recipientId);		
		createdChatIdentifier.setChatIdentifier(generatedChatIdentifier);
				
		chatIdentifierRepository.save(createdChatIdentifier);
				
		return generatedChatIdentifier;
	}
	
	private void persistNotification(Message message) {
		Notification createdNotification = notificationService.createNotificationFromMessage(message);
		notificationService.addNotification(createdNotification);
	}
	
	
	private void persistMessage(Message message) {
		messageRepository.save(message);
	}
	
	private void sendMessage(String channel, Message message) {
		simpMessagingTemplate.convertAndSend(DESTINATION+channel, message);
	}
}

