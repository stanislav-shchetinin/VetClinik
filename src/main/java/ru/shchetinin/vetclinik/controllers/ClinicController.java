package ru.shchetinin.vetclinik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.services.ClinicService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/request/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping
    public ResponseEntity<List<Clinic>> getClinics(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10); // 10 клиник на страницу
        Page<Clinic> clinicsPage = clinicService.getClinics(pageable);
        List<Clinic> clinics = clinicsPage.getContent();
        return ResponseEntity.ok(clinics);
    }

    @GetMapping("/user")
    public ResponseEntity<Clinic> getClinic(Authentication authentication) {
        String username = authentication.getName();
        Optional<Clinic> clinic = clinicService.getClinicByUser(username);

        return clinic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
