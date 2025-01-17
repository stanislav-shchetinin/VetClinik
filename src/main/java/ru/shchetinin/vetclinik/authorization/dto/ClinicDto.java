package ru.shchetinin.vetclinik.authorization.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchetinin.vetclinik.entities.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClinicDto {
    private String name;
    private String street;
    private String house;
    private String openHours;
    private String clinicPhone;
}
