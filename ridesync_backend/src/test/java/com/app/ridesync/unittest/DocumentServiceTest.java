package com.app.ridesync.unittest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.dto.responses.DocumentResponse;
import com.app.ridesync.dto.responses.GetDocumentResponse;
import com.app.ridesync.entities.Document;
import com.app.ridesync.repositories.DocumentRepository;

import java.time.LocalDate;
import java.util.ArrayList;

import com.app.ridesync.services.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DocumentService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DocumentServiceTest {
    @MockBean
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;


    @Test
    void addDocumentSuccessTest() {
        Document document = new Document();
        document.setDocumentId(1);
        document.setDocumentType("Document Type");
        document.setExpiryDate(LocalDate.of(1970, 1, 1));
        document.setUserDocumentID("User Document ID");
        document.setUserId(1);
        when(documentRepository.save(Mockito.<Document>any())).thenReturn(document);

        DocumentResponse expectedDocumentResponse=DocumentResponse.builder()
                                    .message("Document inserted Successfully")
                                    .success(true)
                                    .document(document)
                                    .build();
        DocumentResponse actualAddDocumentResult = documentService.addDocument(new DocumentInput());

        verify(documentRepository).save(Mockito.<Document>any());
        assertEquals(expectedDocumentResponse, actualAddDocumentResult);
    }


    @Test
    void addDocumentFailedTest() {
    DocumentResponse actualAddDocumentResult = documentService.addDocument(null);  
    assertEquals(
               "java.lang.NullPointerException: Cannot invoke \"com.app.ridesync.dto.requests.DocumentInput.getUserDocumentID" + "()\" because \"input\" is null",
                actualAddDocumentResult.getMessage());

    }


    @Test
    void getDocumentsByUserIdTest() {
        when(documentRepository.findByUserId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        GetDocumentResponse expectedGetDocumentResponse=GetDocumentResponse.builder()
                            .message("Documents Fetched Successfully")
                            .success(true)
                            .documents(new ArrayList<>())
                            .build();
        GetDocumentResponse actualDocumentsByUserId = documentService.getDocumentsByUserId(1);

        verify(documentRepository).findByUserId(Mockito.<Integer>any());
        assertEquals(expectedGetDocumentResponse, actualDocumentsByUserId);
    }


    @Test
    void updateDocumentByDocIdPassedTest() {
        Document document = new Document();
        document.setDocumentId(1);
        document.setDocumentType("Document Type");
        document.setExpiryDate(LocalDate.of(1970, 1, 1));
        document.setUserDocumentID("User Document ID");
        document.setUserId(1);

        when(documentRepository.save(Mockito.<Document>any())).thenReturn(document);
        when(documentRepository.findByDocumentId(Mockito.<Integer>any())).thenReturn(document);
        DocumentResponse expectedDocumentResponse=DocumentResponse.builder()
                                                .message("Updated Selected Document Successfully")
                                                .document(document)
                                                .success(true)
                                                .build();
        DocumentResponse actualUpdateDocumentByDocIdResult = documentService.updateDocumentByDocId(new DocumentInput());

        verify(documentRepository).findByDocumentId(Mockito.<Integer>any());
        verify(documentRepository).save(Mockito.<Document>any());
        assertEquals(expectedDocumentResponse, actualUpdateDocumentByDocIdResult);
    }


    @Test
    void updateDocumentByDocIdFailedTest() {
        DocumentResponse actualUpdateDocumentByDocIdResult = documentService.updateDocumentByDocId(null);

        assertEquals(
                "java.lang.NullPointerException: Cannot invoke \"com.app.ridesync.dto.requests.DocumentInput.getDocumentId()\""
                        + " because \"input\" is null",
                actualUpdateDocumentByDocIdResult.getMessage());
    }


    @Test
    void deleteDocumentTest() {
        Document document = new Document();
        document.setDocumentId(1);
        document.setDocumentType("Document Type");
        document.setExpiryDate(LocalDate.of(1970, 1, 1));
        document.setUserDocumentID("User Document ID");
        document.setUserId(1);
        when(documentRepository.findByDocumentId(Mockito.<Integer>any())).thenReturn(document);
        doNothing().when(documentRepository).delete(Mockito.<Document>any());
        DocumentResponse expecDocumentResponse=DocumentResponse.builder()
                                                .message("Deleted Selected Document Successfully")
                                                .success(true)
                                                .document(document)
                                                .build();
        DocumentResponse actualDeleteDocumentResult = documentService.deleteDocument(1);

        verify(documentRepository).findByDocumentId(Mockito.<Integer>any());
        verify(documentRepository).delete(Mockito.<Document>any());
       assertEquals(expecDocumentResponse, actualDeleteDocumentResult);
    }
}
