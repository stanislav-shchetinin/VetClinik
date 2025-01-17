package ru.shchetinin.vetclinik.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shchetinin.vetclinik.dto.RequestDto;
import ru.shchetinin.vetclinik.dto.GroupMemberDto;
import ru.shchetinin.vetclinik.dto.ListsGroupsDto;
import ru.shchetinin.vetclinik.entities.Request;
import ru.shchetinin.vetclinik.entities.JoinedUserRequest;
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
        List<Request> groups = new ArrayList<>();
        groups
                .forEach(group -> {
//                    RequestDto requestDto = new RequestDto(group.getId(), group.getName(), group.getDescription());
                    if (group.getOwner().equals(user)){
                        listsGroupsDto.getAdminGroups().add(requestDto);
                    } else {
                        listsGroupsDto.getMemberGroups().add(
                                new GroupMemberDto(
                                        requestDto,
                                    jugRepository.findByUserAndGroup(user, group).getNumberClasses()
                                )
                        );
                    }
                });
        return ResponseEntity.ok(listsGroupsDto);
    }

    public void createNewGroup(RequestDto requestDto, Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        Optional<Request> groupInBase = groupRepository.findById(requestDto.getId());
        if (groupInBase.isPresent()){
            throw new GroupAlreadyExistException();
        }
        Request group = new Request(
                requestDto.getId(),
                requestDto.getName(),
                requestDto.getDescription(),
                user);
        groupRepository.save(group);
        JoinedUserRequest jug = new JoinedUserRequest();
        jug.setGroup(group);
        jug.setUser(user);
        jugRepository.save(jug);
    }

    public void deleteGroup(UUID id, Principal principal){
        Optional<Request> group = groupRepository.findById(id);
        if (group.isPresent()){
            isRealCreator(group.get(), principal);
            groupRepository.delete(group.get());
        } else {
            throw new NotFoundGroupDeleteException();
        }
    }

}
