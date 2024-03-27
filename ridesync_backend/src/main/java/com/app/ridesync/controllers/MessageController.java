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
	
	@MessageMapping("/send/{channelIdentifier}")
	public void sendMessage(@RequestHeader("Authorization") String jwtToken, @PathVariable String channelIdentifier, Message message){
		message.setSenderId(message.getSenderId());
		
		messageService.persistAndSendMessageToBroker(channelIdentifier, message);
	}
	
	@GetMapping("/chatIdentifier/{recipientId}")
	public ResponseEntity<ApiResponse<String>> getChatIdentifier(@RequestHeader("Authorization") String jwtToken, @PathVariable Integer recipientId) {
		try {
			Integer senderId = jwtService.extractUserId(jwtToken.substring(7));
			String chatIdentifer = messageService.getChatIdentifier(senderId, recipientId);
			
			return ResponseEntity.status(HttpStatus.OK)
					 .body(new ApiResponse<>(chatIdentifer, true, "Chat Identifier was retrieved successfully"));
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body(new ApiResponse<>(null, false, "Chat Identifier retrieval failed with the following error: " + e.getMessage()));	
		}
	}
	
/*	@GetMapping("/messages/{recipientId}")
	public ResponseEntity<ApiResponse<List<MessageProjection>>> getChatMessagesByRecipient(@PathVariable Integer recipientId) {
		try {
			List<MessageProjection> messages = messageService.getChatMessagesByRecipientId(recipientId);
			
			return ResponseEntity.status(HttpStatus.OK)
					 .body(new ApiResponse<>(messages, true, "Chat Messages were retrieved successfully"));
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body(new ApiResponse<>(null, false, "Chat Message retrieval failed with the following error: " + e.getMessage()));	
		}
	} */
	
	@GetMapping("/messageHistory/{recipientId}")
	public ResponseEntity<ApiResponse<List<MessageProjection>>> getChatMessagesBySenderAndRecipient(@RequestHeader("Authorization") String jwtToken, @PathVariable Integer recipientId) {
		try {
			Integer senderId = jwtService.extractUserId(jwtToken.substring(7));
			List<MessageProjection> messages = messageService.getChatMessagesBySenderAndRecipientId(senderId, recipientId);
			
			return ResponseEntity.status(HttpStatus.OK)
					 .body(new ApiResponse<>(messages, true, "Chat Messages were retrieved successfully"));
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body(new ApiResponse<>(null, false, "Chat Message retrieval failed with the following error: " + e.getMessage()));	
		}
	} 
}
