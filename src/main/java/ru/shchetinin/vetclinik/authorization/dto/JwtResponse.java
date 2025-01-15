package ru.shchetinin.vetclinik.authorization.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
}
