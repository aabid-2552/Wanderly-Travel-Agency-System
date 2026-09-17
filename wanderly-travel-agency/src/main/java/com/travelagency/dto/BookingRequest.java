package com.travelagency.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BookingRequest {

    @NotNull(message = "Package id is required")
    private Long packageId;

    @NotNull(message = "Travel date is required")
    private LocalDate travelDate;

    @NotNull(message = "Number of people is required")
    @Min(value = 1, message = "At least 1 person is required")
    private Integer numberOfPeople;

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
