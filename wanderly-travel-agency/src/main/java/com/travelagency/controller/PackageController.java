package com.travelagency.controller;

import com.travelagency.dto.PackageRequest;
import com.travelagency.model.TourPackage;
import com.travelagency.repository.PackageRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageRepository packageRepository;

    public PackageController(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    // Public: anyone can view packages
    @GetMapping
    public List<TourPackage> getAllPackages() {
        return packageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageById(@PathVariable Long id) {
        return packageRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Package not found")));
    }

    // Admin only: create
    @PostMapping
    public ResponseEntity<TourPackage> createPackage(@Valid @RequestBody PackageRequest request) {
        TourPackage tourPackage = new TourPackage(
                request.getTitle(), request.getDescription(), request.getLocation(),
                request.getCategory(), request.getPrice(), request.getDurationDays(), request.getImageUrl()
        );
        TourPackage saved = packageRepository.save(tourPackage);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Admin only: update
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Long id, @Valid @RequestBody PackageRequest request) {
        return packageRepository.findById(id).<ResponseEntity<?>>map(existing -> {
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setLocation(request.getLocation());
            existing.setCategory(request.getCategory());
            existing.setPrice(request.getPrice());
            existing.setDurationDays(request.getDurationDays());
            existing.setImageUrl(request.getImageUrl());
            return ResponseEntity.ok(packageRepository.save(existing));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Package not found")));
    }

    // Admin only: delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        if (!packageRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Package not found"));
        }
        packageRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Package deleted"));
    }
}
