package ru.shchetinin.vetclinik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shchetinin.vetclinik.entities.Carrier;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.repositories.CarrierRepository;
import ru.shchetinin.vetclinik.repositories.ClinicRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;

@Service
public class CarrierService {

    private final CarrierRepository carrierRepository;
    private final UserRepository userRepository;

    public CarrierService(CarrierRepository carrierRepository, UserRepository userRepository) {
        this.carrierRepository = carrierRepository;
        this.userRepository = userRepository;
    }

    public Carrier saveCarrier(Carrier carrier, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        carrier.setUser(user);
        return carrierRepository.save(carrier);
    }
}