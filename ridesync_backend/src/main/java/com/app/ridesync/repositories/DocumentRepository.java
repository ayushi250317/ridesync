package com.app.ridesync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    
	Document findByDocumentId(Integer documentId);
    
    List<Document> findByUserId(Integer userId);
}