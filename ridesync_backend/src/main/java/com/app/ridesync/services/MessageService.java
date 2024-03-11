package com.app.ridesync.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.entities.Message;
import com.app.ridesync.projections.MessageProjection;
import com.app.ridesync.repositories.ChatIdentifierRepository;
import com.app.ridesync.repositories.MessageRepository;

@Service
public class MessageService {
	private final ChatIdentifierRepository chatIdentifierRepository;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final MessageRepository messageRepository;
	
	private static final String DESTINATION = "/queue/messages/";

	@Autowired
	public MessageService(ChatIdentifierRepository chatIdentifierRepository, SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository) {
		this.chatIdentifierRepository = chatIdentifierRepository;
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.messageRepository = messageRepository;

	}
	
	public void persistAndSendMessageToBroker(String channel, Message message) {	
		persistMessage(message);
		sendMessage(channel, message);
	}
	
	public String getChatIdentifier(ChatIdentifier chat) {
		String chatIdentifer = chatIdentifierRepository.findBySenderAndRecipientId(chat.getSenderId(),chat.getRecipientId());
		
		if(chatIdentifer == null)
			chatIdentifer = createAndPersistChatIdentifier(chat);
		
		return chatIdentifer;
	}
	
	public List<MessageProjection> getChatMessagesByRecipientId(Integer recipientId){
		return messageRepository.findByRecipientId(recipientId);
	}
	
	public List<MessageProjection> getChatMessagesBySenderAndRecipientId(Integer senderId, Integer recipientId){
		return messageRepository.findBySenderAndRecipientId(senderId, recipientId);
	}
		
	private String createAndPersistChatIdentifier(ChatIdentifier chat) {
		String createdChatIdentifier = UUID.randomUUID().toString();
		chat.setChatIdentifier(createdChatIdentifier);
		
		chatIdentifierRepository.save(chat);
				
		return createdChatIdentifier;
	}
	
	private void persistMessage(Message message) {
		messageRepository.save(message);
	}
	
	private void sendMessage(String channel, Message message) {
		simpMessagingTemplate.convertAndSend(DESTINATION+channel, message);
	}
}

