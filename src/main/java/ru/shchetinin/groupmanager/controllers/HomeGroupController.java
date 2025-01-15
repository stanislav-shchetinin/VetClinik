package ru.shchetinin.groupmanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.dto.GroupDto;
import ru.shchetinin.groupmanager.dto.ListsGroupsDto;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.exceptions.GroupAlreadyExistException;
import ru.shchetinin.groupmanager.exceptions.NotFoundGroupDeleteException;
import ru.shchetinin.groupmanager.exceptions.NotRealCreatorException;
import ru.shchetinin.groupmanager.repositories.GroupRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;
import ru.shchetinin.groupmanager.services.HomeGroupService;

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
