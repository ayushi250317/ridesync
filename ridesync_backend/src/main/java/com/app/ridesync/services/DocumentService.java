package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.dto.responses.DocumentResponse;
import com.app.ridesync.dto.responses.GetDocumentResponse;
import com.app.ridesync.entities.Document;
import com.app.ridesync.repositories.DocumentRepository;

/**
 * This service class manages operations related to user documents, including adding, retrieving,
 * updating, and deleting documents.
 */
@Service
public class DocumentService {
	@Autowired
	private DocumentRepository documentRepository;

	/**
	 * Adds a document for a user.
	 * Saves the document details in the database.
	 */
	public DocumentResponse addDocument(DocumentInput input) {
		DocumentResponse res = new DocumentResponse();

		try {
			Document document = new Document();
			document.setUserDocumentID(input.getUserDocumentID());
			document.setUserId(input.getUserId());
			document.setDocumentType(input.getDocumentType());
			document.setExpiryDate(input.getExpiryDate());
			Document response = documentRepository.save(document); // add the insurance number ID.

			res.setDocument(response);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(e.toString());
			return res;
		}
		res.setSuccess(true);
		res.setMessage("Document inserted Successfully");
		return res;
	}

	/**
	 * Retrieves all documents associated with a user.
	 * Retrieves documents from the database based on the user ID.
	 */
	public GetDocumentResponse getDocumentsByUserId(Integer userId) {
		GetDocumentResponse res = new GetDocumentResponse();

		try {
			res.setDocuments(documentRepository.findByUserId(userId));

		} catch (Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;
		}
		res.setMessage("Documents Fetched Successfully");
		res.setSuccess(true);
		return res;

	}

	/**
	 * Updates the details of a document.
	 * Retrieves the document by ID, updates its details, and saves the changes in
	 * the database.
	 */
	public DocumentResponse updateDocumentByDocId(DocumentInput input) {
		DocumentResponse res = new DocumentResponse();
		try {
			Document document = documentRepository.findByDocumentId(input.getDocumentId());

			document.setUserDocumentID(input.getUserDocumentID());
			document.setDocumentType(input.getDocumentType());
			document.setExpiryDate(input.getExpiryDate());
			res.setDocument(documentRepository.save(document));
		} catch (Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;
		}
		res.setMessage("Updated Selected Document Successfully");
		res.setSuccess(true);
		return res;
	}

	/**
	 * Deletes a document by its ID.
	 * Retrieves the document by ID, deletes it from the database.
	 */
	public DocumentResponse deleteDocument(Integer documentId) {
		DocumentResponse res = new DocumentResponse();
		try {
			Document document = documentRepository.findByDocumentId(documentId);

			res.setDocument(document);
			documentRepository.delete(document);
		} catch (Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;
		}
		res.setMessage("Deleted Selected Document Successfully");
		res.setSuccess(true);
		return res;
	}
}
