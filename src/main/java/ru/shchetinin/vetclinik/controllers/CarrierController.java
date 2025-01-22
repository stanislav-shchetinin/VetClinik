package ru.shchetinin.vetclinik.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.entities.Carrier;
import ru.shchetinin.vetclinik.services.CarrierService;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/api/carrier")
public class CarrierController {

    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @PostMapping
    public ResponseEntity<?> createCarrier(@RequestBody Carrier carrier, Principal principal) {
        var username = principal.getName();
        try {
            Carrier savedCarrier = carrierService.saveCarrier(carrier, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCarrier);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Error saving carrier"));
        }
    }
}
