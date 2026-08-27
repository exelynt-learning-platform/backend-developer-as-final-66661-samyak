package com.equipment.event_equipment_booking.dto;

import jakarta.validation.constraints.*;

public class UpdateEquipmentRequestDto {

    @NotBlank(message = "Equipment name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotNull(message = "Availability status is required")
    private Boolean available;

    public UpdateEquipmentRequestDto() {
    }

    public UpdateEquipmentRequestDto(String name, String description, String price, Double priceVal, Boolean available) {
        this.name = name;
        this.description = description;
        this.price = priceVal;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}