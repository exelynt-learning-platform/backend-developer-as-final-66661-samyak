package com.equipment.event_equipment_booking.controller;

import com.equipment.event_equipment_booking.dto.CreateReservationRequestDto;
import com.equipment.event_equipment_booking.dto.ReservationResponseDto;
import com.equipment.event_equipment_booking.dto.UpdateReservationStatusRequestDto;
import com.equipment.event_equipment_booking.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody CreateReservationRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAll() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponseDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationResponseDto> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReservationStatusRequestDto requestDto) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, requestDto));
    }
}