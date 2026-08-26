package com.equipment.event_equipment_booking.service;

import com.equipment.event_equipment_booking.entity.Equipment;
import com.equipment.event_equipment_booking.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment item not found with id: " + id));
    }

    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(Long id, Equipment updatedEquipment) {
        Equipment existing = getEquipmentById(id);
        existing.setName(updatedEquipment.getName());
        existing.setDescription(updatedEquipment.getDescription());
        existing.setType(updatedEquipment.getType());
        existing.setPrice(updatedEquipment.getPrice());
        existing.setAvailable(updatedEquipment.getAvailable());
        return equipmentRepository.save(existing);
    }

    public void deleteEquipment(Long id) {
        Equipment existing = getEquipmentById(id);
        equipmentRepository.delete(existing);
    }
}