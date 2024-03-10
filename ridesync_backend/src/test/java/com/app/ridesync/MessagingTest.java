package com.app.ridesync;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		messageService = new MessageService();

		MessageRequest chat = new MessageRequest(1,2,"");
		
		int expectedLength = 36; // length of UUID
		int actualLength = messageService.getChatIdentifier(chat).length();
		
		assertEquals(expectedLength,actualLength);
	}
}
