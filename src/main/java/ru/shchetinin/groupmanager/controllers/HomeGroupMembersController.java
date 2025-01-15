package ru.shchetinin.groupmanager.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.dto.UserDto;
import ru.shchetinin.groupmanager.dto.UserMinusDto;
import ru.shchetinin.groupmanager.dto.UserPlusDto;
import ru.shchetinin.groupmanager.services.HomeGroupMembersService;

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

    @DeleteMapping("/delete")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь удален"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Такого пользователя нет"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Отказано в доступе"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Группа не найдена"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserFromGroup(@PathVariable UUID groupId,
                                    @RequestParam String userId,
                               Principal principal){
        homeGroupMembersService.deleteUserFromGroup(groupId, userId, principal);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успех"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Отказано в доступе"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Группа не найдена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден"
            )
    })
    @PostMapping("/numberOfClasses/plus")
    @ResponseStatus(HttpStatus.OK)
    public void plusNumberOfCLasses(@PathVariable UUID groupId,
                                    @RequestBody UserPlusDto userPlusDto,
                                    Principal principal){
        homeGroupMembersService.plusNumberOfCLasses(groupId, userPlusDto, principal);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успех"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Отказано в доступе"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Группа не найдена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден"
            )
    })
    @PostMapping("/numberOfClasses/minus")
    @ResponseStatus(HttpStatus.OK)
    public void minusNumberOfCLasses(@PathVariable UUID groupId,
                                    @RequestBody UserMinusDto userMinusDto,
                                    Principal principal){

        homeGroupMembersService.minusNumberOfCLasses(groupId, userMinusDto, principal);
    }

}
