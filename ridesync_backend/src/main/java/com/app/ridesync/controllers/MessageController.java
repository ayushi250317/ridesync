package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.entities.ChatIdentifier;
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
	
	@GetMapping("/chatIdentifier")
	public ResponseEntity<ApiResponse<String>> getChatIdentifier(@RequestBody ChatIdentifier chat) {
		try {
			String chatIdentifer = messageService.getChatIdentifier(chat);
			
			return ResponseEntity.status(HttpStatus.OK)
					 .body(new ApiResponse<>(chatIdentifer, true, "Chat Identifier was retrieved successfully"));
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body(new ApiResponse<>(null, false, "Chat Identifier retrieval failed with the following error: " + e.getMessage()));	
		}
	}
}
