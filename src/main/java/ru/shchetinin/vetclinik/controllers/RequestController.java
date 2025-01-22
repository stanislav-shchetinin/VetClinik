package ru.shchetinin.vetclinik.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.dto.RequestDto;
import ru.shchetinin.vetclinik.dto.RequestResponse;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.repositories.ClinicRepository;
import ru.shchetinin.vetclinik.services.RequestService;
import ru.shchetinin.vetclinik.entities.Request;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final ClinicRepository clinicRepository;

    @PostMapping
    @Operation(summary = "Создать заявку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Создана"),
            @ApiResponse(responseCode = "400", description = "Клиника не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<?> createRequest(@RequestBody RequestDto request, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "UNAUTHORIZED"));
        try {
            var savedRequest = requestService.saveRequest(request, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Error saving request"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getRequestsByUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "UNAUTHORIZED"));
        try {
            Page<RequestResponse> requests = requestService.getRequestsByUser(principal.getName(), page, size);
            return ResponseEntity.ok(requests.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Error retrieving requests"));
        }
    }
    @GetMapping("/clinic/list")
    public ResponseEntity<?> getRequestsByClinic(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "UNAUTHORIZED"));
        Clinic clinic = clinicRepository.findByAdminUsername(principal.getName());
        try {
            Page<RequestResponse> requests = requestService.getRequestsByClinic(clinic.getId(), page, size);
            return ResponseEntity.ok(requests.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Error retrieving requests"));
        }
    }
}
