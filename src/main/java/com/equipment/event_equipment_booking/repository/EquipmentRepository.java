package com.equipment.event_equipment_booking.repository;

import com.equipment.event_equipment_booking.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    // Custom query method to fetch only non-deleted records
    List<Equipment> findByIsDeletedFalse();


}