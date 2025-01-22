package ru.shchetinin.vetclinik.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto implements Serializable {
    private String phone;
    private String bread;
    private String size;
    private String weight;
    private String specialRequirements;
    private String street;
    private String house;
    private String flat;
    private Long clinicId;
}
