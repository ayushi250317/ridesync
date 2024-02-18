package com.app.ridesync.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.dto.responses.DocumentResponse;
import com.app.ridesync.dto.responses.GetDocumentResponse;
import com.app.ridesync.services.DocumentService;
import com.app.ridesync.services.JwtService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/document")
@RestController
@RequiredArgsConstructor
public class DocumentController {
	@Autowired
	private DocumentService documentService;
	@Autowired
	private JwtService jwtService;
	
//	@PostMapping("/addDocument")
//	public DocumentResponse addDocument(@RequestHeader("Authentication") String jwtToken, @RequestBody DocumentInput input) {
//
//		String userId = jwtService.extractUserEmail(jwtToken);
//		input.setUserId(userId);
//		DocumentResponse res =documentService.addDocument(input); // add(Ride details)
//		return res;
//	}
	
//	@GetMapping("/getDocumentsByUserId/{id}")
//	public GetDocumentResponse getDocumentsById(@PathVariable String id, @RequestHeader("Authentication") String jwtToken){
//		String userId = jwtService.extractUserEmail(jwtToken);
//
//		return documentService.getDocumentsByUserId(userId);
//	}
}
