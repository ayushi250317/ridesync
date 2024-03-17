package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.dto.responses.DocumentResponse;
import com.app.ridesync.dto.responses.GetDocumentResponse;
import com.app.ridesync.services.DocumentService;
import com.app.ridesync.services.JwtService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/document")
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class DocumentController {
	@Autowired
	private DocumentService documentService;
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/addDocument")
	public DocumentResponse addDocument(@RequestHeader("Authorization") String jwtToken, @RequestBody DocumentInput input) {
		
		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		input.setUserId(userId);
		DocumentResponse res =documentService.addDocument(input); 
		return res;
	}
	
	@GetMapping("/getDocumentsByUserId/{id}")
	public GetDocumentResponse getDocumentsById(@PathVariable Integer id, @RequestHeader("Authorization") String jwtToken){
		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		return documentService.getDocumentsByUserId(userId);
	}
	
	@PostMapping("/updateDocument")
	public DocumentResponse updateDocument(@RequestBody DocumentInput input) {	
		return documentService.updateDocumentByDocId(input);
	}
	
	@DeleteMapping("/deleteDocument/{documentId}")
    public DocumentResponse deleteDocument(@PathVariable Integer documentId) {
        return documentService.deleteDocument(documentId);
    }
}
