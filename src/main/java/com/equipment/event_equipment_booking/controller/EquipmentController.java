package com.equipment.event_equipment_booking.controller;

import com.equipment.event_equipment_booking.dto.CreateEquipmentRequestDto;
import com.equipment.event_equipment_booking.dto.EquipmentResponseDto;
import com.equipment.event_equipment_booking.dto.UpdateEquipmentRequestDto;
import com.equipment.event_equipment_booking.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    @PostMapping
    public ResponseEntity<EquipmentResponseDto> create(
            @Valid @RequestBody CreateEquipmentRequestDto requestDto) {
        EquipmentResponseDto response = equipmentService.createEquipment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEquipmentRequestDto requestDto) {
        EquipmentResponseDto response = equipmentService.updateEquipment(id, requestDto);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDto> getById(@PathVariable Long id) {
        EquipmentResponseDto response = equipmentService.getEquipmentById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDto>> getAll() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.softDeleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}