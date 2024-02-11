package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByDocumentId(Integer documentId);
    
    Optional<Document> findByUserId(String userId);
}