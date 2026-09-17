package com.travelagency.repository;

import com.travelagency.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {
}
