package com.equipment.event_equipment_booking.config;

import com.equipment.event_equipment_booking.entity.*;
import com.equipment.event_equipment_booking.repository.EquipmentRepository;
import com.equipment.event_equipment_booking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    public DataInitializer(UserRepository userRepository, EquipmentRepository equipmentRepository) {
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed Seed Users (Password plain text for now; we will BCrypt encode them in Step 3)
        if (userRepository.count() == 0) {
            User admin = new User(null, "admin@example.com", "admin123", Role.ROLE_ADMIN, LocalDateTime.now());
            User user = new User(null, "user@example.com", "user123", Role.ROLE_USER, LocalDateTime.now());

            userRepository.save(admin);
            userRepository.save(user);
            System.out.println(">>> Seeded default ADMIN and USER accounts.");
        }

        // Seed Sample Equipment
        if (equipmentRepository.count() == 0) {
            Equipment mic = new Equipment();
            mic.setName("Wireless Stage Microphone");
            mic.setDescription("High-grade condenser mic for live events");
            mic.setType("AUDIO");
            mic.setPrice(25.0);
            mic.setAvailable(true);
            mic.setIsDeleted(false);
            mic.setCreatedAt(LocalDateTime.now());
            mic.setUpdatedAt(LocalDateTime.now());

            Equipment projector = new Equipment();
            projector.setName("4K Laser Projector");
            projector.setDescription("5000 Lumens Ultra HD projector");
            projector.setType("VIDEO");
            projector.setPrice(80.0);
            projector.setAvailable(true);
            projector.setIsDeleted(false);
            projector.setCreatedAt(LocalDateTime.now());
            projector.setUpdatedAt(LocalDateTime.now());

            equipmentRepository.save(mic);
            equipmentRepository.save(projector);
            System.out.println(">>> Seeded default Equipment items.");
        }
    }
}