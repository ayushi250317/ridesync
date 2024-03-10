package com.app.ridesync;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.app.ridesync.dto.requests.MessageRequest;
import com.app.ridesync.services.MessageService;

public class MessagingTest {
	private static MessageService messageService;
	
	@BeforeAll
	static void setup() {
		messageService = new MessageService();
	}
	
	@Test
	void testGetChatIdentifierLength() {
		MessageRequest chat = new MessageRequest(1,2,"");
		
		int expectedLength = 36; // length of UUID
		int actualLength = messageService.getChatIdentifier(chat).length();
		
		assertEquals(expectedLength,actualLength);
	}
	
	@Test
	void testGetChatIdenfier_Valid() {
		MessageRequest chat = new MessageRequest(1,2,"");
		
		String generatedUUID = messageService.getChatIdentifier(chat);
		
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
