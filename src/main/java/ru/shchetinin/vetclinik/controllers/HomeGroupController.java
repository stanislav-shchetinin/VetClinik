package ru.shchetinin.vetclinik.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.vetclinik.dto.GroupDto;
import ru.shchetinin.vetclinik.dto.ListsGroupsDto;
import ru.shchetinin.vetclinik.services.HomeGroupService;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class HomeGroupController {

    private final HomeGroupService homeGroupService;
    @GetMapping
    @Operation(
            summary = "Получить все группы пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно получены"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь на авторизован"
            )
    })
    public ResponseEntity<ListsGroupsDto> getGroups(Principal principal){
        return homeGroupService.getGroups(principal);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Получить все группы пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Группа создана"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Группа с таким id уже существует"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь на авторизован"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    public void createNewGroup(@RequestBody GroupDto groupDto, Principal principal){
        homeGroupService.createNewGroup(groupDto, principal);
    }
    @DeleteMapping("/{id}/delete")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Группа создана"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Такой группы нет"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь на авторизован"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Отказано в доступе"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable UUID id, Principal principal){
        homeGroupService.deleteGroup(id, principal);
    }
}
