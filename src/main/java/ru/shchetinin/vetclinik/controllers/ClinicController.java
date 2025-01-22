package ru.shchetinin.vetclinik.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.services.ClinicService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/request/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/all")
    public ResponseEntity<?> getClinics(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        try {
            Page<Clinic> clinics = clinicService.getAllClinics(page, size);
            return ResponseEntity.ok(clinics.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "incorrect info"));
        }
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Создана"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<?> saveClinic(@RequestBody Clinic clinic, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "UNAUTHORIZED"));
        var adminUsername = principal.getName();
        try {
            Clinic savedClinic = clinicService.saveClinic(clinic, adminUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClinic);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Error saving clinic"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getClinic(Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "UNAUTHORIZED"));
        String username = principal.getName();
        Clinic clinic = clinicService.getClinicByUser(username);

        return ResponseEntity.status(HttpStatus.OK).body(clinic);
    }

}
