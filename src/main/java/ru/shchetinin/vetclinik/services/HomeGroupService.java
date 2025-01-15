package ru.shchetinin.vetclinik.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shchetinin.vetclinik.dto.GroupDto;
import ru.shchetinin.vetclinik.dto.GroupMemberDto;
import ru.shchetinin.vetclinik.dto.ListsGroupsDto;
import ru.shchetinin.vetclinik.entities.Group;
import ru.shchetinin.vetclinik.entities.JoinedUserGroup;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.exceptions.GroupAlreadyExistException;
import ru.shchetinin.vetclinik.exceptions.NotFoundGroupDeleteException;
import ru.shchetinin.vetclinik.repositories.GroupRepository;
import ru.shchetinin.vetclinik.repositories.JoinedUserGroupRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;

import java.security.Principal;
import java.util.*;

import static ru.shchetinin.vetclinik.util.Checker.isRealCreator;

@Service
@RequiredArgsConstructor
public class HomeGroupService {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;
    private final JoinedUserGroupRepository jugRepository;

    public ResponseEntity<ListsGroupsDto> getGroups(Principal principal){
        ListsGroupsDto listsGroupsDto = new ListsGroupsDto();
        User user = userRepo.findByUsername(principal.getName());
        List<Group> groups = new ArrayList<>();
        user.getJug().forEach(jug -> groups.add(jug.getGroup()));
        groups
                .forEach(group -> {
                    GroupDto groupDto = new GroupDto(group.getId(), group.getName(), group.getDescription());
                    if (group.getOwner().equals(user)){
                        listsGroupsDto.getAdminGroups().add(groupDto);
                    } else {
                        listsGroupsDto.getMemberGroups().add(
                                new GroupMemberDto(
                                    groupDto,
                                    jugRepository.findByUserAndGroup(user, group).getNumberClasses()
                                )
                        );
                    }
                });
        return ResponseEntity.ok(listsGroupsDto);
    }

    public void createNewGroup(GroupDto groupDto, Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        Optional<Group> groupInBase = groupRepository.findById(groupDto.getId());
        if (groupInBase.isPresent()){
            throw new GroupAlreadyExistException();
        }
        Group group = new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getDescription(),
                user);
        groupRepository.save(group);
        JoinedUserGroup jug = new JoinedUserGroup();
        jug.setGroup(group);
        jug.setUser(user);
        jugRepository.save(jug);
    }

    public void deleteGroup(UUID id, Principal principal){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()){
            isRealCreator(group.get(), principal);
            groupRepository.delete(group.get());
        } else {
            throw new NotFoundGroupDeleteException();
        }
    }

}
