package com.travelagency.model;

import jakarta.persistence.*;

@Entity
@Table(name = "packages")
public class TourPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String location;

    private String category; // e.g. Adventure, Beach, Wildlife, Honeymoon

    private Double price;

    private Integer durationDays;

    private String imageUrl;

    public TourPackage() {
    }

    public TourPackage(String title, String description, String location, String category,
                        Double price, Integer durationDays, String imageUrl) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.category = category;
        this.price = price;
        this.durationDays = durationDays;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
