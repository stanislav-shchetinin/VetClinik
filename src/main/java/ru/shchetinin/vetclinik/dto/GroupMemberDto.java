package ru.shchetinin.vetclinik.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberDto {
    private RequestDto group;
    private Integer numberClasses;
}
