package ru.shchetinin.vetclinik.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shchetinin.vetclinik.authorization.dto.ClinicDto;
import ru.shchetinin.vetclinik.authorization.services.UserService;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.repositories.ClinicRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private UserRepository userRepository;

    public Clinic saveClinic(Clinic clinic, String adminUsername) {
        User adminUser = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        clinic.setAdminUser(adminUser);
        return clinicRepository.save(clinic);
    }

    public Clinic getClinicByUser(String username) {
        return clinicRepository.findByAdminUsername(username);
    }

    public Page<Clinic> getAllClinics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clinicRepository.findAllClinics(pageable);
    }
}