package com.equipment.event_equipment_booking.service;

import com.equipment.event_equipment_booking.entity.Equipment;
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
    public Equipment createEquipment(Equipment equipment) {
        equipment.setIsDeleted(false);
        equipment.setCreatedAt(LocalDateTime.now());
        equipment.setUpdatedAt(LocalDateTime.now());
        return equipmentRepository.save(equipment);
    }

    // READ ONE
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    // READ ALL (Only non-deleted)
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findByIsDeletedFalse();
    }

    // UPDATE
    public Equipment updateEquipment(Long id, Equipment updatedData) {
        Equipment existing = equipmentRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setType(updatedData.getType());
        existing.setPrice(updatedData.getPrice());
        existing.setAvailable(updatedData.getAvailable());
        existing.setUpdatedAt(LocalDateTime.now());

        return equipmentRepository.save(existing);
    }

    // SOFT DELETE
    public void softDeleteEquipment(Long id) {
        Equipment existing = equipmentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setIsDeleted(true);
            existing.setUpdatedAt(LocalDateTime.now());
            equipmentRepository.save(existing);
        }
    }
}