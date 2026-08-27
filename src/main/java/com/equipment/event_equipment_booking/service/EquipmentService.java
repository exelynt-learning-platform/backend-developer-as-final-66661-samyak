package com.equipment.event_equipment_booking.service;

import com.equipment.event_equipment_booking.dto.CreateEquipmentRequestDto;
import com.equipment.event_equipment_booking.dto.EquipmentResponseDto;
import com.equipment.event_equipment_booking.dto.UpdateEquipmentRequestDto;
import com.equipment.event_equipment_booking.entity.Equipment;
import com.equipment.event_equipment_booking.exception.ResourceNotFoundException;
import com.equipment.event_equipment_booking.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    // CREATE
    public EquipmentResponseDto createEquipment(CreateEquipmentRequestDto requestDto) {
        Equipment equipment = mapToEntity(requestDto);
        Equipment savedEquipment = equipmentRepository.save(equipment);
        return mapToDto(savedEquipment, "Equipment created successfully");
    }

    // READ ONE
    public EquipmentResponseDto getEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .filter(e -> !e.getIsDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with ID: " + id));

        return mapToDto(equipment, "Equipment fetched successfully");
    }

    // READ ALL
    public List<EquipmentResponseDto> getAllEquipment() {
        return equipmentRepository.findByIsDeletedFalse()
                .stream()
                .map(equipment -> mapToDto(equipment, "Equipment fetched successfully"))
                .toList();
    }

    // UPDATE
    public EquipmentResponseDto updateEquipment(Long id, UpdateEquipmentRequestDto requestDto) {
        Equipment existing = equipmentRepository.findById(id)
                .filter(e -> !e.getIsDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Equipment not found with ID: " + id));

        existing.setName(requestDto.getName());
        existing.setDescription(requestDto.getDescription());
        existing.setPrice(requestDto.getPrice());
        existing.setAvailable(requestDto.getAvailable());
        existing.setUpdatedAt(LocalDateTime.now());

        Equipment updatedEquipment = equipmentRepository.save(existing);
        return mapToDto(updatedEquipment, "Equipment updated successfully");
    }

    // SOFT DELETE
    public void softDeleteEquipment(Long id) {
        Equipment existing = equipmentRepository.findById(id)
                .filter(e -> !e.getIsDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Equipment not found with ID: " + id));

        existing.setIsDeleted(true);
        existing.setUpdatedAt(LocalDateTime.now());
        equipmentRepository.save(existing);
    }

    private Equipment mapToEntity(CreateEquipmentRequestDto dto) {
        Equipment equipment = new Equipment();
        equipment.setName(dto.getName());
        equipment.setDescription(dto.getDescription());
        equipment.setType(dto.getType());
        equipment.setPrice(dto.getPrice());
        equipment.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        equipment.setIsDeleted(false);
        equipment.setCreatedAt(LocalDateTime.now());
        equipment.setUpdatedAt(LocalDateTime.now());
        return equipment;
    }

    private EquipmentResponseDto mapToDto(Equipment equipment, String message) {
        EquipmentResponseDto dto = new EquipmentResponseDto();
        dto.setId(equipment.getId());
        dto.setName(equipment.getName());
        dto.setDescription(equipment.getDescription());
        dto.setType(equipment.getType());
        dto.setPrice(equipment.getPrice());
        dto.setAvailable(equipment.getAvailable());
        dto.setMessage(message);
        return dto;
    }
}