package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ridesync.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByDocumentId(Integer documentId);
    
    Optional<Document> findByUserId(Integer userId);
}