package com.app.ridesync.integrationtest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.entities.ChatIdentifier;
import com.app.ridesync.entities.User;
import com.app.ridesync.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import jakarta.mail.MessagingException;

@SpringBootTest
@AutoConfigureMockMvc
public class MessagingTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthenticationService userService;

	private static User user;
	private static String BEARER_TOKEN;

	@BeforeEach
	private void setup() {
		setupUser();
	}

	@AfterEach()
	private void teardown() {
		clearUser();
	}

	public void setupUser() {
		createUser();
		verifyUser();
		authenticateUser();
	}

	@Test
	void testGetChatIdentifier() throws Exception {

		ChatIdentifier chat = new ChatIdentifier();
		chat.setSenderId(1);
		chat.setRecipientId(2);

		String requestBody = objectMapper.writeValueAsString(chat);

		MvcResult result = mvc
				.perform(get("/api/v1/message/chatIdentifier").contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + BEARER_TOKEN).content(requestBody))
				.andExpect(status().isOk()).andReturn();

		String chatIdentifier = JsonPath.read(result.getResponse().getContentAsString(), "$.responseObject");

		assertTrue(isValidUUID(chatIdentifier));
	}

	private boolean isValidUUID(String uuid) {
		try {
			UUID.fromString(uuid);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private void createUser() {
		RegisterRequest request = new RegisterRequest("admin", "nithinbharathik@gmail.com", "abc", new Date(), "123456",
				"99999 99999");
		try {
			user = userService.register(request).getUser();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void verifyUser() {
		userService.verifyEmail(user.getUserId(), user.getEmail());
	}

	private void authenticateUser() {
		AuthenticationRequest request = new AuthenticationRequest("nithinbharathik@gmail.com", "123456");
		BEARER_TOKEN = userService.authenticate(request).getToken();
	}

	private void clearUser() {
		userService.deleteByUserId(user.getUserId());
	}
}
