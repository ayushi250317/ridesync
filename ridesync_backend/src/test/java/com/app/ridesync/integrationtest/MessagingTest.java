package com.app.ridesync.integrationtest;

import com.app.ridesync.entities.ChatIdentifier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class MessagingTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	// @Test
	// void testGetChatIdentifier() throws Exception {
		
	// 	ChatIdentifier chat = new ChatIdentifier();
	// 	chat.setSenderId(1);
	// 	chat.setRecipientId(2);
		
	// 	String requestBody = objectMapper.writeValueAsString(chat);

	// 	MvcResult result = mvc.perform(
	// 						   get("/api/v1/message/chatIdentifier")
	// 						   .contentType(MediaType.APPLICATION_JSON)
	// 					       .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNTIifQ.7KjwphRNd7qYUwpHonAaJ_QLmrOLax_t7r_ha2FIork")
	// 					       .content(requestBody))
	// 						   .andExpect(status().isOk())
	// 						   .andReturn();
		
	// 	String chatIdentifier = JsonPath.read(result.getResponse().getContentAsString(), "$.responseObject");

	// 	assertTrue(isValidUUID(chatIdentifier));
	// }
	
	private boolean isValidUUID(String uuid) {
		try{
			UUID.fromString(uuid);
			return true;
			
		}catch(Exception e) {
			return false;
		}
	}

}
