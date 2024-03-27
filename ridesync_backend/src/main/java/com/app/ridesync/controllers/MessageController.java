package com.app.ridesync.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.entities.Message;
import com.app.ridesync.projections.MessageProjection;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.MessageService;

/**
 * Controller class handling message-related endpoints.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/message")
public class MessageController {
	private MessageService messageService;
	private JwtService jwtService;

	@Autowired
	public MessageController(MessageService messageService, JwtService jwtService) {
		this.messageService = messageService;
		this.jwtService = jwtService;
	}

	/**
	 * Endpoint for sending a message to a specified channel.
	 */
	@MessageMapping("/send/{channelIdentifier}")
	public void sendMessage(@RequestHeader("Authorization") String jwtToken, @PathVariable String channelIdentifier,
			Message message) {
		message.setSenderId(jwtService.extractUserId(jwtToken.substring(7)));

		messageService.persistAndSendMessageToBroker(channelIdentifier, message);
	}

	/**
	 * Endpoint for retrieving the chat identifier for a specified recipient.
	 */
	@GetMapping("/chatIdentifier/{recipientId}")
	public ResponseEntity<ApiResponse<String>> getChatIdentifier(@RequestHeader("Authorization") String jwtToken,
			@PathVariable Integer recipientId) {
		try {
			Integer senderId = jwtService.extractUserId(jwtToken.substring(7));
			String chatIdentifer = messageService.getChatIdentifier(senderId, recipientId);
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

	/*
	 * @GetMapping("/messages/{recipientId}")
	 * public ResponseEntity<ApiResponse<List<MessageProjection>>>
	 * getChatMessagesByRecipient(@PathVariable Integer recipientId) {
	 * try {
	 * List<MessageProjection> messages =
	 * messageService.getChatMessagesByRecipientId(recipientId);
	 * ApiResponse<List<MessageProjection>> response = new ApiResponse<>(messages,
	 * true,
	 * "Chat Messages were retrieved successfully");
	 * return ResponseEntity.status(HttpStatus.OK)
	 * .body(response);
	 * 
	 * } catch (Exception e) {
	 * ApiResponse<List<MessageProjection>> response = new ApiResponse<>(null,
	 * false,
	 * "Chat Message retrieval failed with the following error: " + e.getMessage());
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(response);
	 * }
	 * }
	 */

	/**
	 * Endpoint for retrieving chat message history for a specified sender and
	 * recipient.
	 */
	@GetMapping("/messageHistory/{recipientId}")
	public ResponseEntity<ApiResponse<List<MessageProjection>>> getChatMessagesBySenderAndRecipient(
			@RequestHeader("Authorization") String jwtToken, @PathVariable Integer recipientId) {
		try {
			Integer senderId = jwtService.extractUserId(jwtToken.substring(7));
			List<MessageProjection> messages = messageService.getChatMessagesBySenderAndRecipientId(senderId,
					recipientId);
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
