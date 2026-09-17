package com.travelagency.repository;

import com.travelagency.model.Booking;
import com.travelagency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}
