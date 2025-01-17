package ru.shchetinin.vetclinik.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shchetinin.vetclinik.dto.UserDto;
import ru.shchetinin.vetclinik.dto.UserMinusDto;
import ru.shchetinin.vetclinik.dto.UserPlusDto;
import ru.shchetinin.vetclinik.entities.Request;
import ru.shchetinin.vetclinik.entities.JoinedUserRequest;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.repositories.GroupRepository;
import ru.shchetinin.vetclinik.repositories.JoinedUserGroupRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.shchetinin.vetclinik.util.Checker.*;

@Service
@RequiredArgsConstructor
public class HomeGroupMembersService {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;
    private final JoinedUserGroupRepository jugRepository;

    public ResponseEntity<List<UserDto>> getUsersFromGroup(UUID groupId,
                                                           Principal principal){
        Optional<Request> group = groupRepository.findById(groupId);

        isGroupExist(group);
        isRealCreator(group.get(), principal);

        List<User> members = new ArrayList<>();
        group.get().getJug().forEach(jug -> members.add(jug.getUser()));

        return ResponseEntity.ok(members
                .stream()
                .map(user -> new UserDto(
                        user.getUsername(),
                        jugRepository.findByUserAndGroup(user, group.get()).getNumberClasses()))
                .collect(Collectors.toList()));

    }

    public void addUserInGroup(UUID groupId,
                               String userId,
                               Principal principal){
        Optional<Request> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userId);

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        JoinedUserRequest jug = new JoinedUserRequest();
        jug.setUser(user);
        jug.setNumberClasses(0);
        jugRepository.save(jug);
    }

    public void deleteUserFromGroup(UUID groupId,
                                    String userId,
                                    Principal principal){
        Optional<Request> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userId);

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        jugRepository.delete(
                jugRepository.findByUserAndGroup(user, group.get())
        );

    }

    public void plusNumberOfCLasses(UUID groupId,
                                    UserPlusDto userPlusDto,
                                    Principal principal){
        Optional<Request> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userPlusDto.getUsername());

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        JoinedUserRequest jug = jugRepository.findByUserAndGroup(user, group.get());
        Integer curNumberClasses = jug.getNumberClasses();
        jug.setNumberClasses(curNumberClasses + userPlusDto.getPlus());
        jugRepository.save(jug);
    }

    public void minusNumberOfCLasses(UUID groupId,
                                     UserMinusDto userMinusDto,
                                     Principal principal){

        Optional<Request> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userMinusDto.getUsername());

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        JoinedUserRequest jug = jugRepository.findByUserAndGroup(user, group.get());
        Integer curNumberClasses = jug.getNumberClasses();
        jug.setNumberClasses(curNumberClasses - userMinusDto.getMinus());
        jugRepository.save(jug);
    }

}
