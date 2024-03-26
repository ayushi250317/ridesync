package com.app.ridesync.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.MessageHistoryRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.entities.Message;
import com.app.ridesync.projections.MessageProjection;
import com.app.ridesync.services.MessageService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/message")
public class MessageController {
	private MessageService messageService;

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@MessageMapping("/send/{channelIdentifier}")
	public void sendMessage(@PathVariable String channelIdentifier, Message message) {
		messageService.persistAndSendMessageToBroker(channelIdentifier, message);
	}

	@GetMapping("/chatIdentifier")
	public ResponseEntity<ApiResponse<String>> getChatIdentifier(@RequestBody ChatIdentifier chat) {
		try {
			String chatIdentifer = messageService.getChatIdentifier(chat);
			ApiResponse<String> response = new ApiResponse<>(chatIdentifer, true,
					"Chat Identifier was retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<>(null, false,
					"Chat Identifier retrieval failed with the following error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	@GetMapping("/messages/{recipientId}")
	public ResponseEntity<ApiResponse<List<MessageProjection>>> getChatMessagesByRecipient(
			@PathVariable int recipientId) {
		try {
			List<MessageProjection> messages = messageService.getChatMessagesByRecipientId(recipientId);
			ApiResponse<List<MessageProjection>> response = new ApiResponse<>(messages, true,
					"Chat Messages were retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<List<MessageProjection>> response = new ApiResponse<>(null, false,
					"Chat Message retrieval failed with the following error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	@GetMapping("/messageHistory")
	public ResponseEntity<ApiResponse<List<MessageProjection>>> getChatMessagesBySenderAndRecipient(
			@RequestBody MessageHistoryRequest messageHistoryRequest) {
		try {
			Integer senderId=messageHistoryRequest.senderId();
			Integer recepientId=messageHistoryRequest.recipientId();
			List<MessageProjection> messages = messageService.getChatMessagesBySenderAndRecipientId(senderId,recepientId);
			ApiResponse<List<MessageProjection>> response = new ApiResponse<>(messages, true,
					"Chat Messages were retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<List<MessageProjection>> response = new ApiResponse<>(null, false,
					"Chat Message retrieval failed with the following error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}
}
