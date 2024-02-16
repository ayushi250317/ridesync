package com.app.ridesync.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.dto.responses.DocumentResponse;
import com.app.ridesync.entities.Document;
import com.app.ridesync.repositories.DocumentRepository;

@Service
public class DocumentService {
	@Autowired
	private DocumentRepository documentRepository;
	
	public DocumentResponse addDocument(DocumentInput input) {
		DocumentResponse res = new DocumentResponse();
				
		try {
		Document document = new Document(input.getDocumentName(), input.getUserId(), input.getDocumentType(), input.getExpiryDate());
		Document response = documentRepository.save(document); //add the insurance number ID.
		
		
		res.setDocument(response);
		}catch(Exception e){
			res.setSuccess(false);
			res.setMessage(e.toString());
			return res;
		}
		res.setSuccess(true);
		res.setMessage("Document inserted Successfully");
		return res;
	}
	
	
	public List<Document> getDocumentsByUserId(String userId) {
		return documentRepository.findByUserId(userId);
		
	}
}
