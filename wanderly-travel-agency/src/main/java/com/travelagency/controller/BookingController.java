package com.travelagency.controller;

import com.travelagency.dto.BookingRequest;
import com.travelagency.dto.BookingStatusUpdateRequest;
import com.travelagency.model.Booking;
import com.travelagency.model.TourPackage;
import com.travelagency.model.User;
import com.travelagency.repository.BookingRepository;
import com.travelagency.repository.PackageRepository;
import com.travelagency.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository bookingRepository, PackageRepository packageRepository,
                              UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
    }

    // Logged-in user creates a booking
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        TourPackage tourPackage = packageRepository.findById(request.getPackageId()).orElse(null);
        if (tourPackage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Package not found"));
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTourPackage(tourPackage);
        booking.setTravelDate(request.getTravelDate());
        booking.setNumberOfPeople(request.getNumberOfPeople());

        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Logged-in user views their own bookings
    @GetMapping("/my")
    public List<Booking> getMyBookings(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return bookingRepository.findByUser(user);
    }

    // Admin views all bookings
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @Valid @RequestBody BookingStatusUpdateRequest request) {
        return bookingRepository.findById(id).<ResponseEntity<?>>map(booking -> {
            booking.setStatus(request.getStatus());
            return ResponseEntity.ok(bookingRepository.save(booking));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Booking not found")));
    }
}
