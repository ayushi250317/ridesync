package com.app.ridesync.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.repositories.ChatIdentifierRepository;

@Service
public class MessageService {
	private final ChatIdentifierRepository chatIdentifierRepository;
	
	@Autowired
	public MessageService(ChatIdentifierRepository chatIdentifierRepository) {
		this.chatIdentifierRepository = chatIdentifierRepository;
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
				
		return createdChatIdentifier;
	}
}
