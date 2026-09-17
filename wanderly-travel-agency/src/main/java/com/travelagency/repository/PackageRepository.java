package com.travelagency.repository;

import com.travelagency.model.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<TourPackage, Long> {
}
