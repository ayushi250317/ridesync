package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.dto.responses.DocumentResponse;
import com.app.ridesync.dto.responses.GetDocumentResponse;
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
	
	
	public GetDocumentResponse getDocumentsByUserId(Integer userId) {
		GetDocumentResponse res = new GetDocumentResponse();
		
		try {
		res.setTemp(documentRepository.findByUserId(userId));
		
		}catch(Exception e){
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;
		}
		res.setMessage("Documents Fetched Successfully");
		res.setSuccess(true);
		return res;
		
	}
}
