package ru.shchetinin.vetclinik.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private UserRepository userRepo;

    public Clinic saveClinic(Clinic clinic, String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        log.info("ugu: " + user.getUsername());
        log.info("cgu: " + clinic.getUser().getUsername());
        if (!user.getUsername().equals(clinic.getUser().getUsername())) {
            throw new SecurityException("User does not match the clinic");
        }

        return clinicRepository.save(clinic);
    }

    public Optional<Clinic> getClinicByUser(String username) {
        return clinicRepository.findByUserUsername(username);
    }

    public Page<Clinic> getClinics(Pageable pageable) {
        return clinicRepository.findAll(pageable);
    }
}