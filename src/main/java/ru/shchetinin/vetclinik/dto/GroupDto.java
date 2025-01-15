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
public class GroupDto implements Serializable {
    private UUID id;
    private String name;
    private String description;
}
