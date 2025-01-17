package ru.shchetinin.vetclinik.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.authorization.dto.ClinicDto;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.services.ClinicService;
import ru.shchetinin.vetclinik.services.HomeGroupService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/info/clinic")
@RequiredArgsConstructor
@Slf4j
public class AddInfoController {
    @Autowired
    private ClinicService clinicService;

    @PostMapping
    public ResponseEntity<Clinic> saveClinic(@RequestBody Clinic clinic, Authentication authentication) {
        log.info("prrr name: " + authentication.getName());
        String username = authentication.getName();
        Clinic savedClinic = clinicService.saveClinic(clinic, username);
        return ResponseEntity.ok(savedClinic);
    }

    @GetMapping
    public ResponseEntity<Clinic> getClinic(Principal principal) {
        String username = principal.getName();
        Optional<Clinic> clinic = clinicService.getClinicByUser(username);

        return clinic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
