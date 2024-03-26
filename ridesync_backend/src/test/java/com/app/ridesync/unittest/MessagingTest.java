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
	private ChatIdentifierRepository chatIdentifierIdentifierRepository;

	@InjectMocks
	private MessageService messageService;



	@Test
	void testGetChatIdentifierLength() {
		ChatIdentifier chatIdentifier = new ChatIdentifier();
		chatIdentifier.setSenderId(1);
		chatIdentifier.setRecipientId(2);

		when(chatIdentifierIdentifierRepository.findBySenderAndRecipientId(chatIdentifier.getSenderId(),chatIdentifier.getRecipientId())).thenReturn(null);

		int expectedLength = 36; // length of UUID
		int actualLength = messageService.getChatIdentifier(chatIdentifier.getSenderId(),chatIdentifier.getRecipientId()).length();

		verify(chatIdentifierIdentifierRepository).findBySenderAndRecipientId(chatIdentifier.getSenderId(),chatIdentifier.getRecipientId());

		assertEquals(expectedLength,actualLength);
	}

	@Test
	void testGetChatIdenfier_Valid() {
		ChatIdentifier chatIdentifier= new ChatIdentifier();
		chatIdentifier.setSenderId(1);
		chatIdentifier.setRecipientId(2);

		when(chatIdentifierIdentifierRepository.findBySenderAndRecipientId(chatIdentifier.getSenderId(),chatIdentifier.getRecipientId())).thenReturn(null);

		String generatedUUID = messageService.getChatIdentifier(chatIdentifier.getSenderId(),chatIdentifier.getRecipientId());

		verify(chatIdentifierIdentifierRepository).findBySenderAndRecipientId(chatIdentifier.getSenderId(),chatIdentifier.getRecipientId());

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
