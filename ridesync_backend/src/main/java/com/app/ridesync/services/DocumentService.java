package com.app.ridesync.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Document;
import com.app.ridesync.repositories.DocumentRepository;

@Service
public class DocumentService {
	@Autowired
	private DocumentRepository documentRepository;
	
	public long addDocument(Document document) {
		Document d = documentRepository.save(document);
		return d.getDocumentId();
	}
	
	public Optional<Document> getDocumentById(int Id) {
		return documentRepository.findByDocumentId(Id);
	}
}
