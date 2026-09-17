package com.travelagency.controller;

import com.travelagency.dto.ContactRequest;
import com.travelagency.model.ContactMessage;
import com.travelagency.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Public: anyone can send a contact message
    @PostMapping
    public ResponseEntity<?> submitMessage(@Valid @RequestBody ContactRequest request) {
        ContactMessage message = new ContactMessage();
        message.setName(request.getName());
        message.setEmail(request.getEmail());
        message.setSubject(request.getSubject());
        message.setMessage(request.getMessage());

        contactRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Thank you! We'll get back to you soon."));
    }

    // Admin only: view all messages
    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return contactRepository.findAll();
    }
}
