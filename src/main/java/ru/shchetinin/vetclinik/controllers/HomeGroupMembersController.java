package ru.shchetinin.vetclinik.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.dto.UserDto;
import ru.shchetinin.vetclinik.dto.UserMinusDto;
import ru.shchetinin.vetclinik.dto.UserPlusDto;
import ru.shchetinin.vetclinik.services.HomeGroupMembersService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups/{groupId}/members")
@RequiredArgsConstructor
public class HomeGroupMembersController {

    private final HomeGroupMembersService homeGroupMembersService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Юзеры получены"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Отказано в доступе"
            )
    })
    public ResponseEntity<List<UserDto>> getUsersFromGroup(@PathVariable UUID groupId,
                                                           Principal principal){
        return homeGroupMembersService.getUsersFromGroup(groupId, principal);
    }
    /**
     * Админ добавляет юзера
     * */
    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь добавлен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Такой группы нет"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Отказано в доступе"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    public void addUserInGroup(@PathVariable UUID groupId,
                               @RequestParam String userId,
                               Principal principal){
        homeGroupMembersService.addUserInGroup(groupId, userId, principal);
    }

}
