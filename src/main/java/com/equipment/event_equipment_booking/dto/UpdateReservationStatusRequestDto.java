package com.equipment.event_equipment_booking.dto;

import com.equipment.event_equipment_booking.entity.ReservationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateReservationStatusRequestDto {

    @NotNull(message = "Reservation status is required")
    private ReservationStatus status;

    public UpdateReservationStatusRequestDto() {
    }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}