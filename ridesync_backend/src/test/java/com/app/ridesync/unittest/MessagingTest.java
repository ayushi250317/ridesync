package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.repositories.ChatIdentifierRepository;
import com.app.ridesync.services.MessageService;

@ExtendWith(MockitoExtension.class)
public class MessagingTest {
	@Mock
	private ChatIdentifierRepository chatIdentifierRepository;

	@InjectMocks
	private MessageService messageService;



	@Test
	void testGetChatIdentifierLength() {
		ChatIdentifier chat = new ChatIdentifier();
		chat.setSenderId(1);
		chat.setRecipientId(2);

		when(chatIdentifierRepository.findBySenderAndRecipientId(chat.getSenderId(),chat.getRecipientId())).thenReturn(null);

		int expectedLength = 36; // length of UUID
		int actualLength = messageService.getChatIdentifier(chat).length();

		verify(chatIdentifierRepository).findBySenderAndRecipientId(chat.getSenderId(),chat.getRecipientId());

		assertEquals(expectedLength,actualLength);
	}

	@Test
	void testGetChatIdenfier_Valid() {
		ChatIdentifier chat = new ChatIdentifier();
		chat.setSenderId(1);
		chat.setRecipientId(2);

		when(chatIdentifierRepository.findBySenderAndRecipientId(chat.getSenderId(),chat.getRecipientId())).thenReturn(null);

		String generatedUUID = messageService.getChatIdentifier(chat);

		verify(chatIdentifierRepository).findBySenderAndRecipientId(chat.getSenderId(),chat.getRecipientId());

		assertTrue(isValidUUID(generatedUUID));
	}

	private boolean isValidUUID(String uuid) {
		try{
			UUID.fromString(uuid);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
