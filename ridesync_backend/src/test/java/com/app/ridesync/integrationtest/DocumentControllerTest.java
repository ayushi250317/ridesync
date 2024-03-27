package com.app.ridesync.integrationtest;

import com.app.ridesync.controllers.DocumentController;
import com.app.ridesync.dto.requests.DocumentInput;
import com.app.ridesync.repositories.DocumentRepository;
import com.app.ridesync.services.DocumentService;
import com.app.ridesync.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DocumentController.class, DocumentService.class, JwtService.class})
@ExtendWith(SpringExtension.class)
class DocumentControllerTest {
    @Autowired
    private DocumentController documentController;

    @MockBean
    private DocumentRepository documentRepository;


    @Test
    void addDocumentTest() throws Exception {

        // Arrange
        DocumentInput documentInput = new DocumentInput();
        documentInput.setDocumentId(1);
        documentInput.setDocumentType("Document Type");
        documentInput.setUserDocumentID("User Document ID");
        documentInput.setUserId(1);
        String content = (new ObjectMapper()).writeValueAsString(documentInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/document/addDocument")
                .header("Authorization", "qwerty eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.uXy8l0MeVGqx2pUKqDb3ZWGqcJyO-o2BMLk4x6zRhkY")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(documentController).build().perform(requestBuilder);
    }

    @Test
    void deleteDocumentTest() throws Exception {

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/document/deleteDocument/{documentId}", 1);

        // Act
        MockMvcBuilders.standaloneSetup(documentController).build().perform(requestBuilder);
    }



    @Test
    void updateDocumentTest() throws Exception {

        // Arrange
        DocumentInput documentInput = new DocumentInput();
        documentInput.setDocumentId(1);
        documentInput.setDocumentType("Document Type");
        documentInput.setUserDocumentID("User Document ID");
        documentInput.setUserId(1);
        String content = (new ObjectMapper()).writeValueAsString(documentInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/document/updateDocument")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(documentController).build().perform(requestBuilder);
    }
}
