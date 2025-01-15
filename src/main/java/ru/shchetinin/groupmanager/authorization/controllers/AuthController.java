package ru.shchetinin.groupmanager.authorization.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shchetinin.groupmanager.authorization.dto.JwtRequest;
import ru.shchetinin.groupmanager.authorization.dto.JwtResponse;
import ru.shchetinin.groupmanager.authorization.services.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Метод аутентификации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Токен успешно сгенерирован"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь с таким username не найден"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Email пользователя не активирован"
            )
    })
    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

}
