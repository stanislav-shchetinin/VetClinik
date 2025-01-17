package ru.shchetinin.vetclinik.authorization.dto;

import lombok.*;
import ru.shchetinin.vetclinik.authorization.roles.RoleAdd;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private RoleAdd role;
}
