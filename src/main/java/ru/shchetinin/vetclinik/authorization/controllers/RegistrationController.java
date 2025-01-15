package ru.shchetinin.vetclinik.authorization.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.responses.Response;
import ru.shchetinin.vetclinik.authorization.services.RegistrationService;

@RestController
@RequiredArgsConstructor
@Tag(name="RegistrationController",
        description="Контроллер для регистрации пользователей")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(
            summary = "Метод регистрации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно добавлен в базу"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с таким username уже существует"
            )
    })
    @PostMapping("/registration")
    public Response registration(@RequestBody User user){
        return registrationService.addNewUser(user);
    }

    @GetMapping("/activation/{activationCode}")
    @ResponseStatus(HttpStatus.OK)
    public void activation(@PathVariable String activationCode){
        registrationService.activation(activationCode);
    }

}