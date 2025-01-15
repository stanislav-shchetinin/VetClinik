package ru.shchetinin.groupmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberDto {
    private GroupDto group;
    private Integer numberClasses;
}
