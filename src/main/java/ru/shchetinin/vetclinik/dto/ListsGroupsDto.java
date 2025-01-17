package ru.shchetinin.vetclinik.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListsGroupsDto {
    private List<RequestDto> adminGroups = new ArrayList<>();
    private List<GroupMemberDto> memberGroups = new ArrayList<>();
}
