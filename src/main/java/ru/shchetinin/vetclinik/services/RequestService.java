package ru.shchetinin.vetclinik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shchetinin.vetclinik.dto.RequestDto;
import ru.shchetinin.vetclinik.dto.RequestResponse;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.entities.Request;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.repositories.ClinicRepository;
import ru.shchetinin.vetclinik.repositories.RequestRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, ClinicRepository clinicRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.clinicRepository = clinicRepository;
        this.userRepository = userRepository;
    }

    public RequestResponse saveRequest(RequestDto dto, String username) {
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
        User user = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var request = new Request();
        request.setPhone(dto.getPhone());
        request.setBreed(dto.getBread()); // "bread" -> "breed" (исправлено)
        request.setSize(dto.getSize());
        request.setWeight(dto.getWeight());
        request.setSpecialRequirements(dto.getSpecialRequirements());
        request.setStreet(dto.getStreet());
        request.setHouse(dto.getHouse());
        request.setFlat(dto.getFlat());
        request.setClinic(clinic);
        request.setUser(user);
        requestRepository.save(request);
        return mapToResponse(request);
    }

    private RequestResponse mapToResponse(Request request) {
        RequestResponse response = new RequestResponse();
        response.setId(request.getId());
        response.setStatus("PENDING"); // Устанавливаем статус
        response.setPhone(request.getPhone());
        response.setBreed(request.getBreed());
        response.setSize(request.getSize());
        response.setWeight(request.getWeight());
        response.setSpecialRequirements(request.getSpecialRequirements());
        response.setStreet(request.getStreet());
        response.setHouse(request.getHouse());
        response.setFlat(request.getFlat());

        // Заполняем информацию о клинике
        Clinic clinic = request.getClinic();
        if (clinic != null) {
            response.setClinicPhone(clinic.getClinicPhone());
            response.setClinicName(clinic.getName());
            response.setOpenHours(clinic.getOpenHours());
            response.setClinicStreet(clinic.getStreet());
            response.setClinicHouse(clinic.getHouse());
            response.setClinicFlat(clinic.getFlat());
        }

        return response;
    }

    public Page<RequestResponse> getRequestsByUser(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var requests = requestRepository.findRequestsByUserId(username, pageable);
        return requests.map(this::mapToResponse);
    }

    public Page<RequestResponse> getRequestsByClinic(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var requests = requestRepository.findRequestsByClinicId(id, pageable);
        return requests.map(this::mapToResponse);
    }
}
