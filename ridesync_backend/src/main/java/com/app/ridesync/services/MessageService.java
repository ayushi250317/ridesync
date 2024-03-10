package com.app.ridesync.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.entities.Message;
import com.app.ridesync.repositories.ChatIdentifierRepository;

@Service
public class MessageService {
	private final ChatIdentifierRepository chatIdentifierRepository;
	private SimpMessagingTemplate simpMessagingTemplate;
	private static final String DESTINATION = "/queue/messages/";

	@Autowired
	public MessageService(ChatIdentifierRepository chatIdentifierRepository, SimpMessagingTemplate simpMessagingTemplate) {
		this.chatIdentifierRepository = chatIdentifierRepository;
		this.simpMessagingTemplate = simpMessagingTemplate;

	}
	
	public void persistAndSendMessageToBroker(String channel, Message message) {		
		sendMessage(channel, message);
	}
	
	public String getChatIdentifier(ChatIdentifier chat) {
		String chatIdentifer = chatIdentifierRepository.findBySenderAndRecipientId(chat.getSenderId(),chat.getRecipientId());
		
		if(chatIdentifer == null)
			chatIdentifer = createAndPersistChatIdentifier(chat);
		
		return chatIdentifer;
	}
	
	private String createAndPersistChatIdentifier(ChatIdentifier chat) {
		String createdChatIdentifier = UUID.randomUUID().toString();
		chat.setChatIdentifier(createdChatIdentifier);
		
		chatIdentifierRepository.save(chat);
				
		return createdChatIdentifier;
	}
	
	private void sendMessage(String channel, Message message) {
		simpMessagingTemplate.convertAndSend(DESTINATION+channel, message);
	}
}

