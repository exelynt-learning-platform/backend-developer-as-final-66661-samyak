package com.equipment.event_equipment_booking.service;

import com.equipment.event_equipment_booking.dto.CreateReservationRequestDto;
import com.equipment.event_equipment_booking.dto.ReservationResponseDto;
import com.equipment.event_equipment_booking.dto.UpdateReservationStatusRequestDto;
import com.equipment.event_equipment_booking.entity.*;
import com.equipment.event_equipment_booking.exception.ResourceNotFoundException;
import com.equipment.event_equipment_booking.repository.EquipmentRepository;
import com.equipment.event_equipment_booking.repository.ReservationRepository;
import com.equipment.event_equipment_booking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              EquipmentRepository equipmentRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public ReservationResponseDto createReservation(CreateReservationRequestDto requestDto) {
        if (requestDto.getEndTime().isBefore(requestDto.getStartTime()) ||
                requestDto.getEndTime().isEqual(requestDto.getStartTime())) {
            throw new IllegalArgumentException("End time must be strictly after start time");
        }

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getUserId()));

        Equipment equipment = equipmentRepository.findById(requestDto.getEquipmentId())
                .filter(e -> !e.getIsDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with ID: " + requestDto.getEquipmentId()));

        if (!equipment.getAvailable()) {
            throw new IllegalStateException("Selected equipment is currently not available for booking");
        }

        // Calculate price based on duration in hours
        long hours = Math.max(1, Duration.between(requestDto.getStartTime(), requestDto.getEndTime()).toHours());
        BigDecimal calculatedPrice = BigDecimal.valueOf(equipment.getPrice()).multiply(BigDecimal.valueOf(hours));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEquipment(equipment);
        reservation.setStartTime(requestDto.getStartTime());
        reservation.setEndTime(requestDto.getEndTime());
        reservation.setTotalPrice(calculatedPrice);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());

        Reservation saved = reservationRepository.save(reservation);
        return mapToDto(saved, "Reservation created successfully");
    }

    public ReservationResponseDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        return mapToDto(reservation, "Reservation fetched successfully");
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(res -> mapToDto(res, "Reservation fetched successfully"))
                .toList();
    }

    public List<ReservationResponseDto> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(res -> mapToDto(res, "User reservations fetched successfully"))
                .toList();
    }

    public ReservationResponseDto updateReservationStatus(Long id, UpdateReservationStatusRequestDto requestDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));

        reservation.setStatus(requestDto.getStatus());
        reservation.setUpdatedAt(LocalDateTime.now());

        Reservation updated = reservationRepository.save(reservation);
        return mapToDto(updated, "Reservation status updated to " + requestDto.getStatus());
    }

    private ReservationResponseDto mapToDto(Reservation reservation, String message) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUser().getId());
        dto.setUserEmail(reservation.getUser().getEmail());
        dto.setEquipmentId(reservation.getEquipment().getId());
        dto.setEquipmentName(reservation.getEquipment().getName());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setTotalPrice(reservation.getTotalPrice());
        dto.setStatus(reservation.getStatus());
        dto.setMessage(message);
        return dto;
    }
}